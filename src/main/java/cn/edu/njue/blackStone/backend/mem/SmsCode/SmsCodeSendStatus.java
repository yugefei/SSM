package cn.edu.njue.blackStone.backend.mem.SmsCode;

/**
 * Created by Edward on 2017/2/8 008.
 */
public enum SmsCodeSendStatus {
    fakePhone//手机号不正确
    ,frequencyLimit //流量控制
    ,success //成功
    ,unknown //未知错误
}
