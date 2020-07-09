package cn.bupt.sse.nmp.service.impl;

import cn.bupt.sse.nmp.dao.RoleMapper;
import cn.bupt.sse.nmp.dao.RolePermissionMapper;
import cn.bupt.sse.nmp.entity.Role;
import cn.bupt.sse.nmp.entity.RolePermission;
import cn.bupt.sse.nmp.service.RoleService;
import com.mysql.cj.jdbc.integration.jboss.ExtendedMysqlExceptionSorter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public void assignPerms(Map<String, Object> map){
        rolePermissionMapper.delete((Integer)map.get("roleId"));
        rolePermissionMapper.insert(map);
    }

    @Override
    public Role selectByRoleId(Integer roleId) {
        Role role = roleMapper.selectByRoleId(roleId);
        return role;
    }

    @Override
    public void addRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateRole(role);
    }

    @Override
    public void delRoleById(Integer roleId) {
        roleMapper.delRoleById(roleId);
    }

    @Override
    public Role selectByUserPhone(String phone) {
        return roleMapper.selectByUserPhone(phone);
    }

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }
}
