package indi.repo.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ChenHQ
 */
@Data
@Builder
@TableName("system_log")
@AllArgsConstructor
@NoArgsConstructor
public class SystemLog {

    /**
     * 操作日志主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 操作人员账号
     */
    private String userId;

    /**
     * 操作人员姓名
     */
    private String username;

    /**
     * 操作类型
     */
    private String optType;

    /**
     * 模块类型
     */
    private String moduleType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 操作详情记录
     */
    private String detailContent;

    /**
     * 展开返回的集合json入库数据
     */
    private String contentJson;

}
