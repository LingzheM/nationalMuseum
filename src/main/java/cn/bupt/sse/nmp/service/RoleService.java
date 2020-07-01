package cn.bupt.sse.nmp.service;

import cn.bupt.sse.nmp.entity.Role;

import java.util.List;

public interface RoleService {
    public void assignPerms(Integer roleId,List<Integer> permIds);

    public Role findById(Integer roleId);
}
