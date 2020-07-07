package cn.bupt.sse.nmp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("角色权限模型")
public class RolePermission {
    @ApiModelProperty("角色Id")
    private Integer roleId;

    @ApiModelProperty("权限Id")
    private Integer permissionId;

    public RolePermission(Integer roleId, Integer permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public RolePermission() {
        super();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}