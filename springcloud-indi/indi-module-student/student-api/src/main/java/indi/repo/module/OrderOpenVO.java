package indi.repo.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChenHQ
 * 订单信息
 * @date 2021/12/17 17:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderOpenVO {

    /**
     * 购买人
     */
    private String name;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 下单时间
     */
    private String orderTime;

    /**
     * 订单状态
     */
    private String orderStatue;

    /**
     * 商品货号
     */
    private String productCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品单价
     */
    private String productPrice;

    /**
     * 购买数量
     */
    private String productNum;

    /**
     * 购买总价
     */
    private String orderPrice;

    /**
     * 关联第三方链sku
     */
    private String productSku;

    /**
     * 链类型id
     */
    private String chainType;


}
