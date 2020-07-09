package cn.bupt.sse.nmp.service;

import cn.bupt.sse.nmp.entity.LoginUser;
import cn.bupt.sse.nmp.entity.User;
import cn.bupt.sse.nmp.result.Result;

import java.util.List;
import java.util.Map;

public interface UserService {

    Result<LoginUser> login(String userPhone, String password);


    /**
     * 根据手机号获取用户
     * @param userPhone
     * @return
     */
    Result<User> getUserByPhone(String userPhone);

    Result<Integer> UserRegister(User user);

    void updateUser(User user);

    void deleteUserById(Integer userId);

    List<Map> selectUser(String userPhone);

}
