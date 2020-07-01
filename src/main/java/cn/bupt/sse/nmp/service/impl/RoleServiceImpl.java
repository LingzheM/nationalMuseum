package cn.bupt.sse.nmp.service.impl;

import cn.bupt.sse.nmp.dao.RoleMapper;
import cn.bupt.sse.nmp.dao.RolePermissionMapper;
import cn.bupt.sse.nmp.entity.Role;
import cn.bupt.sse.nmp.entity.RolePermission;
import cn.bupt.sse.nmp.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: nationalMuseum
 * @description:
 * @author: Ljx
 * @create: 2020-07-01 14:11
 **/
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public void assignPerms(Integer roleId, List<Integer> permIds) {
        for (Integer permId : permIds) {
            RolePermission rolePermission = new RolePermission(roleId, permId);
            rolePermissionMapper.insert(rolePermission);
        }
    }

    @Override
    public Role findById(Integer roleId) {
        Role role = roleMapper.findById(roleId);
        return role;
    }
}
