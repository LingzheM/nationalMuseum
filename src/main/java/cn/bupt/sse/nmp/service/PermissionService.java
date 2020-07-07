package cn.bupt.sse.nmp.service;

import cn.bupt.sse.nmp.entity.Permission;

import java.util.List;

public interface PermissionService {
    void addPermission(Permission permission);
    void updatePermission(Permission permission);
    void deletePermission(Integer permissionId);
    List<Permission> selectAll();
}
