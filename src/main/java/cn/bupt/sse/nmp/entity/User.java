package cn.bupt.sse.nmp.entity;

import java.util.Date;

public class User {
    private Integer userId;

    private String userName;

    private Integer phone;

    private String password;

    private Byte status;

    private Byte age;

    private String sex;

    private Date createTime;

    public User(Integer userId, String userName, Integer phone, String password, Byte status, Byte age, String sex, Date createTime) {
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

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
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