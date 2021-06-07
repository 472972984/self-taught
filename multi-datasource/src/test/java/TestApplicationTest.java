import indi.repo.multisource.TestApplication;
import indi.repo.multisource.config.DBTypeEnum;
import indi.repo.multisource.config.DbContextHolder;
import indi.repo.multisource.entity.Student;
import indi.repo.multisource.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/7
 * @desc:
 */
@SpringBootTest(classes = {TestApplication.class})
@RunWith(SpringRunner.class)
public class TestApplicationTest {

    @Autowired
    StudentService studentService;

    @Test
    public void test() {
        Student s1 = studentService.getById(1L);
        System.out.println("s1 = " + s1);
    }

}
