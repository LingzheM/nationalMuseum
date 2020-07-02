package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.RolePermission;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

public interface RolePermissionMapper {
    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    List<Integer> selectPermIdsByRoleId(Integer roleId);
}