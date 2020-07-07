package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.Role;

import java.util.List;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);

    Role selectByRoleId(Integer roleId);

    Role selectByUserPhone(String phone);

    void updateRole(Role role);

    void delRoleById(Integer roleId);

    List<Role> selectAll();
}