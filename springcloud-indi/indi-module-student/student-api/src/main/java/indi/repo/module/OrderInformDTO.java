package indi.repo.module;

import lombok.Data;

import java.util.List;

/**
 * @author ChenHQ
 * @date 2022/4/1 15:23
 */
@Data
public class OrderInformDTO {

    private List<String> orderCode;

    private String merchantId;


}
