package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.Permission;

import java.util.List;

public interface PermissionMapper {
    int insert(Permission permission);
    void update(Permission permission);
    void delete(Integer permissionId);
    List<Permission> selectAll();

    int insertSelective(Permission record);
}