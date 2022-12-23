package indi.repo.springboot.module.dto;

import indi.repo.common.annotation.HttpAnnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

/**
 * @author ChenHQ
 * @date 2022/8/31 13:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDTO {

    private String url;


    @HttpAnnotation(method = HttpMethod.GET)
    public void testA(String chq) {
        System.out.println("chq = " + chq);
    }

}
