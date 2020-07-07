package cn.bupt.sse.nmp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户角色模型")
public class UserRole {
    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("角色Id")
    private Integer roleId;

    public UserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRole() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}