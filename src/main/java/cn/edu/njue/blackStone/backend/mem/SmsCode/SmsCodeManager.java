package cn.edu.njue.blackStone.backend.mem.SmsCode;

import cn.edu.njue.blackStone.backend.mem.TokenModel;
import cn.edu.njue.blackStone.backend.utils.HttpUtils;
import cn.edu.njue.blackStone.backend.utils.RestClient;
import cn.edu.njue.blackStone.backend.utils.Utils;
import com.alibaba.druid.sql.ast.expr.SQLNullExpr;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Edward on 2017/2/8 008.
 */
public class SmsCodeManager {
    class VerifyCodeInfo{
        private String phoneNumber;
        private String code;
        private SmsCodeType type;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public SmsCodeType getType() {
            return type;
        }

        public void setType(SmsCodeType type) {
            this.type = type;
        }
    }


    private Logger logger = Logger.getLogger(SmsCodeManager.class);

    private Random random;
    private Long smsCodeLifeLong = (long)(10 * 60 * 1000);
    private Map<String,TokenModel<VerifyCodeInfo>> map;
    private static SmsCodeManager ourInstance = new SmsCodeManager();

    private Map<String,String> header;

    public static SmsCodeManager getInstance() {
        return ourInstance;
    }
    private SmsCodeManager(){
        header = new HashMap<String, String>();
        header.put("Authorization","APPCODE appcode"); //阿里云调用值
        map = new HashMap<String, TokenModel<VerifyCodeInfo>>();
        random = new Random(new Date().getTime());
        //开启定时任务,一天清理一次失效验证码避免内存占用过度
        final long timeInterval = 24 * 60 * 60 * 1000; //一天

    }

    public synchronized  SmsCodeSendStatus sendSms(String number,SmsCodeType type, String stuCode) {
        String templateString = null;
        switch (type){
            case register:
                templateString = "SMS_45425016";
                break;
            case forgetPwd:
                templateString = "SMS_56170277";
                break;
            case changeMobile:
                templateString = "SMS_68705070";
                break;

            default:
                break;
        }
        if(map.containsKey(number))
            map.remove(number);

        VerifyCodeInfo info = new VerifyCodeInfo();
        info.setPhoneNumber(number);
        info.setType(type);
        String code = String.format("%06d", random.nextInt(1000000));
        info.setCode(code);

        String host = "http://sms.market.alicloudapi.com";
        String path = "/singleSendSms";
        Map<String, String> params = new HashMap<String, String>();
        params.put("TemplateCode", templateString);
        params.put("RecNum", number);

        if(type != SmsCodeType.changeMobile)
            params.put("ParamString", "{\"code\":\"" + code + "\",\"number\":\"" + number.substring(number.length() - 4,number.length()) + "\"}");
        else
            params.put("ParamString", String.format( "{\"code\":\"%s\",\"stuCode\":\"%s\"}",code,stuCode.substring(stuCode.length() - 4,stuCode.length())));

        params.put("SignName","黑石顶");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, "GET", header, params);
            String responseString = EntityUtils.toString(response.getEntity());
            logger.info(responseString);
            ApiResponse res = new ObjectMapper().readValue(responseString, ApiResponse.class);
            if (res.isSuccess()) {
                TokenModel<VerifyCodeInfo> model = new TokenModel<VerifyCodeInfo>();
                model.setData(info);
                model.setToken(number);
                model.setExpireTime(new Date(System.currentTimeMillis() + this.smsCodeLifeLong));
                map.put(number, model);
                return SmsCodeSendStatus.success;
            } else {
                logger.error(String.format("Send SMSCodeFailed: Number:%s,message:%s", number, res.getMessage()));
                if(res.getMessage().equals("Frequency limit reaches.")){
                    return SmsCodeSendStatus.frequencyLimit;
                }
                return SmsCodeSendStatus.unknown;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return SmsCodeSendStatus.unknown;
        }
    }


    public synchronized boolean check(String token,SmsCodeType type,String verifyCode){
        if(!this.map.containsKey(token))
            return false;

        if(this.map.get(token).getExpireTime().compareTo(new Date()) == -1 ) { //expire time 比现在时间小 说明过期
            this.map.remove(token);
            return false;
        }

        if(type != map.get(token).getData().type || !verifyCode.equals(map.get(token).getData().getCode()))
            return false;

        return true;
    }
    public synchronized  void remove(String token){
        this.map.remove(token);
    }
    private synchronized void mapCleaning(){
        for(TokenModel<VerifyCodeInfo> model : map.values()){
            if(model.getExpireTime().compareTo(new Date()) == -1 ) { //expire time 比现在时间小 说明过期
                this.map.remove(model.getToken());
            }
        }

        logger.info("SmsCode map cleanedUp");
    }
}
