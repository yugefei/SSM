package com.tencent.seventeenShow.backend.mem;

import com.qiniu.util.Auth;

/**
 * Created by Edward on 2017/3/31 031.
 */
public class UploadTokenManager {
    private static UploadTokenManager ourInstance = new UploadTokenManager();

    public static UploadTokenManager getInstance() {
        return ourInstance;
    }

    private Auth auth;

    private UploadTokenManager() {
        String accessKey = "RYYpakA_Uok-E7gB_3QPG2LyWXJPwWBApJ9I9r5P";
        String secretKey = "7p8g1SLav-nGIF-WPN8-zYjsqX3UAfMA58on2DF7";
        auth = Auth.create(accessKey, secretKey);
    }

    public String getToken(){
        return auth.uploadToken("17show");
    }
}
