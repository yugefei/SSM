package cn.edu.njue.blackStone.backend.mem;

import java.util.Date;

/**
 * Created by Edward on 2016/8/6.
 */
public class TokenModel <T>{
    private String token;
    private Date expireTime;
    private T data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
