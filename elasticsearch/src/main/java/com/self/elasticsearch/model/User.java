package com.self.elasticsearch.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author ChenHQ
 * 系统名称: elasticsearch
 * 模块名称:
 * 类 名 称: User
 * 软件版权: 杭州数美科技有限公司
 * 功能说明：
 * 系统版本：
 * 开发时间: create in 2020/8/24
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期 修改人员 修改说明
 */
@Document(indexName = "ecut")
public class User {

    private String id;

    private String name;

    private String sex;

    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
