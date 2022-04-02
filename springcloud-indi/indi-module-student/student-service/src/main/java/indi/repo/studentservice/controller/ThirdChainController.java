package indi.repo.studentservice.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import indi.repo.common.Result;
import indi.repo.module.ChainResponse;
import indi.repo.module.OrderInformDTO;
import indi.repo.module.OrderOpenVO;
import indi.repo.studentservice.common.OtherChainClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ChenHQ
 * @date 2022/4/1 15:21
 */
@Slf4j
@RestController
public class ThirdChainController {

    @Value("${hde.url:https://10.50.2.70/hde/open/api/entrance}")
    private String hdeUrl;

    @Value("${hde.appkey:1474c0b6ca00299602f64}")
    private String appKey;

    @Value("${hde.secret:TEBLPplIBm_PNR7uPLnR}")
    private String secret;

    @Value("${hde.chainCode:123456789}")
    private String chainCode;

    @Resource
    private OtherChainClient otherChainClient;

    private String addressAccount = "account-address";

    /**
     * 订单推送
     */
    @PostMapping("/order/push")
    public Result hdeOrderInformPost(@RequestBody OrderInformDTO orderInformDTO) throws Exception {
        Result result = new Result();
        log.info("收到hde订单信息推送：订单号为：{}, 商户号为：{}", orderInformDTO.getOrderCode(), orderInformDTO.getMerchantId());
        //调用hde服务获取订单详情
        String method = "hde.order.detail.get";
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("orderCode", orderInformDTO.getOrderCode());
        jsonObject.set("merchantId", orderInformDTO.getMerchantId());
        ChainResponse<Object> response = otherChainClient.postHdeRequest(hdeUrl, method, jsonObject, appKey, secret);
        log.info("hde查询详情服务接口返回：{}", JSON.toJSONString(response));

        if (response.getCode() != HttpStatus.HTTP_OK) {
            log.error("调用hde服务有误-{}....", response.getMessage());
            result.setCode(500);
            result.setMessage("失败");
            result.setSuccess(false);
            return result;
        }

        //----------------------------------------------------------------------------------------------------------------
        //账号推送
        OrderOpenVO orderInfo = JSON.parseObject(response.getData().toString(), OrderOpenVO.class);
        String accountAddress = UUID.fastUUID().toString().replace("-", "") + addressAccount;
        //账号推送
        String methodAccount = "hde.user.chain.account";
        JSONObject account = new JSONObject();
        account.set("phone", orderInfo.getPhone());
        account.set("chainCode", chainCode);
        account.set("account", accountAddress);
        ChainResponse<Object> resp = otherChainClient.postHdeRequest(hdeUrl, methodAccount, account, appKey, secret);
        log.info("hde账号推送服务接口返回：{}", JSON.toJSONString(resp));

        result.setCode(0);
        result.setSuccess(true);
        result.setMessage("成功");
        return result;
    }


}
