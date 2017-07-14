package com.tencent.seventeenShow.backend.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;

/**
 * Created by Edward on 2016/9/18.
 */
public class Utils {
    public static String getSig(String openId) throws IOException{
        return Utils.exeCmd("/root/signature /root/private_key 1400035413 " + openId);
    }
    private static String exeCmd(String commandStr) throws IOException {
        BufferedReader br = null;
        Process p = Runtime.getRuntime().exec(commandStr);
        br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();

    }

    public static void copyProperties(Object result,Object object){
        try{
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            BeanUtils.copyProperties(result,object);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    private static String byteToHexString(byte[] tmp) {
        String s;
        // 用字节表示就是 16 个字节
        char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
            // 转换成 16 进制字符的转换
            byte byte0 = tmp[i]; // 取第 i 个字节
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
            // >>> 为逻辑右移，将符号位一起右移
            str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }
        s = new String(str); // 换后的结果转换为字符串
        return s;
    }
    public static String getHash(String data) {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(data.getBytes());
            String tokenString = byteToHexString(b);
            return tokenString;
        }catch (Exception e){
            return null;
        }

    }
    public static String MD5(String data) {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(data.getBytes());
            String tokenString = byteToHexString(b);
            return tokenString;
        }catch (Exception e){
            return null;
        }

    }
}
