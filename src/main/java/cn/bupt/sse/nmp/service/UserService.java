package cn.bupt.sse.nmp.service;

import cn.bupt.sse.nmp.entity.LoginUser;
import cn.bupt.sse.nmp.entity.User;
import cn.bupt.sse.nmp.result.Result;

public interface UserService {

    Result<LoginUser> login(String userPhone, String password);


    /**
     * 根据手机号获取用户
     * @param userPhone
     * @return
     */
    Result<User> getUserByPhone(String userPhone);

    Result<User> UserRegister(User user);

}
