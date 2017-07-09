package cn.edu.njue.blackStone.backend.model;

import cn.edu.njue.blackStone.backend.conf.ResultCode;

/**
 * Created by Edward on 2017/2/7 007.
 */
public class Response <T> {
    private Long code = ResultCode.OK_CODE;
    private String message = "ok";
    private T data;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public void setCode(int code) {
        this.code = (long) code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Response(){

    }

    public Response(Long code,String message){
        this.setCode(code);
        this.setMessage(message);
    }

    public Response(boolean isSuccess,Long code,String message){
        if(!isSuccess){
            this.code = code;
            this.message = message;
        }
    }

    public Response(T data){
        this.setData(data);
    }
}