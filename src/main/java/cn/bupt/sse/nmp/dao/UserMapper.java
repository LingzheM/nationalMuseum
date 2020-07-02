package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User selectByUserPhone(String userPhone);
}