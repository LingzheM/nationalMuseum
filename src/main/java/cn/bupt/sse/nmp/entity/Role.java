package cn.bupt.sse.nmp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("角色模型")
public class Role {
    @ApiModelProperty("角色Id")
    private Integer roleId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("角色描述")
    private String description;
    @ApiModelProperty("角色类型")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Role(Integer roleId, String roleName, String description, String type) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.description = description;
        this.type = type;

    }

    public Role() {
        super();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}