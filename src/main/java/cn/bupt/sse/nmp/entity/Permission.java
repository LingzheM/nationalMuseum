package cn.bupt.sse.nmp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("权限模型")
public class Permission {

    @ApiModelProperty("权限Id")
    private Integer permissionId;

    @ApiModelProperty("权限名称")
    private String permissionName;

    @ApiModelProperty("权限type")
    private Byte type;

    @ApiModelProperty("权限描述")
    private String description;

    public Permission(Integer permissionId, String permissionName, Byte type, String description) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.type = type;
        this.description = description;
    }

    public Permission() {
        super();
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}