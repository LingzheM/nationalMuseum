package cn.bupt.sse.nmp.service;

import cn.bupt.sse.nmp.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {
    public void assignPerms(Map<String, Object> map);

    public Role selectByRoleId(Integer roleId);
    void addRole(Role role);

    void updateRole(Role role);

    void delRoleById(Integer roleId);

    public Role selectByUserPhone(String phone);

    List<Role> selectAll();
}
