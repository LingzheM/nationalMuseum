package cn.bupt.sse.nmp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("用户模型")
public class User {
    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户手机")
    private String phone;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户状态")
    private Byte status;

    @ApiModelProperty("用户年龄")
    private Byte age;

    @ApiModelProperty("用户性别")
    private String sex;

    @ApiModelProperty("用户创建时间")
    private Date createTime;

    public User(Integer userId, String userName, String phone, String password, Byte status, Byte age, String sex, Date createTime) {
        this.userId = userId;
        this.userName = userName;
        this.phone = phone;
        this.password = password;
        this.status = status;
        this.age = age;
        this.sex = sex;
        this.createTime = createTime;
    }

    public User() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}