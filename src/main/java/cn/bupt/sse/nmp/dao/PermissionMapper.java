package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.Permission;

public interface PermissionMapper {
    int insert(Permission record);

    int insertSelective(Permission record);
}