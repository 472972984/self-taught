package com.self.elasticsearch.operator;

import com.self.elasticsearch.model.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenHQ
 * 系统名称: elasticsearch
 * 模块名称:
 * 类 名 称: UserController
 * 软件版权: 杭州数美科技有限公司
 * 功能说明：
 * 系统版本：
 * 开发时间: create in 2020/8/24
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期 修改人员 修改说明
 */
@RestController
@RequestMapping("/user")
public class UserController {



    @RequestMapping("/importData")
    public Result testImportData(){




        return Result.ok();
    }



}
