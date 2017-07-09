package cn.edu.njue.blackStone.backend.controller.form;

import cn.edu.njue.blackStone.backend.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import java.util.Date;

/**
 * Created by Edward on 2017/2/7 007.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterForm {
    private String pwd;
    private String verifyCode;
    private String mobile;
    private String name;
    private String gender;
    private String studentId;
    private String mail;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
