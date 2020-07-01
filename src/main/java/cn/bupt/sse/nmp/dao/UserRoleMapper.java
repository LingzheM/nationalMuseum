package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.UserRole;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}