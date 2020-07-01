package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.Role;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);

    Role selectByRoleId(Integer roleId);
}