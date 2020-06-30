package cn.bupt.sse.nmp.service.impl;

import cn.bupt.sse.nmp.dao.UserMapper;
import cn.bupt.sse.nmp.entity.LoginUser;
import cn.bupt.sse.nmp.entity.User;
import cn.bupt.sse.nmp.result.CodeMsg;
import cn.bupt.sse.nmp.result.Result;
import cn.bupt.sse.nmp.service.UserService;
import cn.bupt.sse.nmp.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<LoginUser> login(Integer userPhone, String password) {

        User user = userMapper.selectByUserPhone(userPhone);

        if (user!=null&&user.getPassword().equals(password)) {
            LoginUser loginUser = new LoginUser();
            loginUser.setUser(user);
            String token = JWTUtil.sign(userPhone, password);
            loginUser.setToken(token);
            return Result.success(loginUser);
        }else if (user!=null) {
            return Result.error(CodeMsg.PASSWORD_INCORRECT);
        }else {
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }

    }
}
