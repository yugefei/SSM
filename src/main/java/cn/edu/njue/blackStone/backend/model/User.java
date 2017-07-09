package cn.edu.njue.blackStone.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by Edward on 2017/2/7 007.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User {
    private Long id;
    private String mobile;
    private String studentId;
    private String name;
    private String gender;
    private String avatar;
    private String mail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
