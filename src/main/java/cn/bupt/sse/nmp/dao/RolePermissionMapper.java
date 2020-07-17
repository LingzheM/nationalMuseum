package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.RolePermission;


import java.util.List;
import java.util.Map;

public interface RolePermissionMapper {
    int insert(Map<String, Object> map);

    void delete(Integer roleId);

    int insertSelective(RolePermission record);

    List<Integer> selectPermIdsByRoleId(Integer roleId);
}