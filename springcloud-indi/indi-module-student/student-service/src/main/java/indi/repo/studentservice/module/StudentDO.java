package indi.repo.studentservice.module;

import com.github.dadiyang.equator.FieldBaseEquator;
import com.github.dadiyang.equator.FieldInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private FriendDO friend;


    public static void main(String[] args) {

        StudentDO s1 = StudentDO.builder().id(1L).sex("M").username("chq").friend(new FriendDO("杭州", "135")).build();

        StudentDO s2 = StudentDO.builder().id(2L).sex("F").username("chq2").friend(new FriendDO("杭州2", "135")).build();


        MyFieldBaseEquator fieldBaseEquator = new MyFieldBaseEquator();
        List<FieldInfo> diffFields = fieldBaseEquator.getDiffFields(s1, s2);

        for (FieldInfo diffField : diffFields) {

            System.out.println(diffField);

        }

    }


}
