package cn.bupt.sse.nmp;

import cn.bupt.sse.nmp.dao.RoleMapper;
import cn.bupt.sse.nmp.dao.UserMapper;
import cn.bupt.sse.nmp.entity.Role;
import cn.bupt.sse.nmp.entity.User;
import cn.bupt.sse.nmp.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NationalMuseumSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RoleTest {

    @Test
    public void testForUserSelect() {

        System.out.println(1);
    }
}
