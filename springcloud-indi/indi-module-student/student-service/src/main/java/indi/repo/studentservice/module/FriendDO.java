package indi.repo.studentservice.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChenHQ
 * @date 2022/3/31 15:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendDO {

    private String address;

    private String phone;


}
