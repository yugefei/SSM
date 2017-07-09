package cn.edu.njue.blackStone.backend.mem.SmsCode;

/**
 * Created by Edward on 2017/2/8 008.
 */
public class ApiResponse{
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiResponse(){}
}