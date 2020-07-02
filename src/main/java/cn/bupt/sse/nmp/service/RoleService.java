package cn.bupt.sse.nmp.service;

import cn.bupt.sse.nmp.entity.Role;

import java.util.List;

public interface RoleService {
    public void assignPerms(Integer roleId,List<Integer> permIds);

    public Role selectByRoleId(Integer roleId);
    void addRole(Role role);

    void updateRole(Role role);

    void delRoleById(Integer roleId);


    List<Role> selectAll();
}
