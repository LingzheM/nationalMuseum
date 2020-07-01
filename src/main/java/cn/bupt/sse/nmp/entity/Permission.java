package cn.bupt.sse.nmp.entity;

public class Permission {
    private Integer permissionId;

    private String permissionName;

    private Byte type;

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