package indi.repo.springboot.test.engine;

import lombok.Data;

import java.util.List;

/**
 * @author ChenHQ
 * @date 2025/4/8 21:08
 */
@Data
public class PluginDTO {

    String domain;

    String path;

    List<PluginDetailDTO> detailDTOList;

    @Data
    public static class PluginDetailDTO {
        HandlerType handlerType;
        String code;
    }

}
