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
@SpringBootTest(classes = NationalMuseumSystemApplication.class)
public class RoleTest {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;


    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    public void testForSelect() {
        Role role = roleMapper.selectByRoleId(1);

        System.out.println(role.getDescription());
    }

    @Test
    public void testForUserSelect() {
        User user = userMapper.selectByUserPhone(176);

        System.out.println(user);
    }
}
