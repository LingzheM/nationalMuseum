package cn.bupt.sse.nmp.service.impl;

import cn.bupt.sse.nmp.dao.PermissionMapper;
import cn.bupt.sse.nmp.entity.Permission;
import cn.bupt.sse.nmp.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void addPermission(Permission permission) {
        permissionMapper.insert(permission);
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionMapper.update(permission);
    }

    @Override
    public void deletePermission(Integer permissionId) {
        permissionMapper.delete(permissionId);
    }

    @Override
    public List<Permission> selectAll() {
        return permissionMapper.selectAll();
    }
}
