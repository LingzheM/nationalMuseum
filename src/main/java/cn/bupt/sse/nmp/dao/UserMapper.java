package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User selectByUserPhone(String userPhone);

    void updateUser(User user);

    void deleteUserById(Integer userId);

    List<Map> selectUser(String userPhone);
}