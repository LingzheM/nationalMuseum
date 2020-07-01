package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.RolePermission;

public interface RolePermissionMapper {
    int insert(RolePermission record);

    int insertSelective(RolePermission record);
}