package cn.bupt.sse.nmp.entity;

public class Role {
    private Integer roleId;

    private String roleName;

    private String description;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Role(Integer roleId, String roleName, String description, Integer type) {
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