package indi.repo.studentservice.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChenHQ
 * @date: create in 2021/12/26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDO {

    private Long id;

    private String username;

    private String sex;

}
