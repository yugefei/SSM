package cn.edu.njue.blackStone.backend.mem;

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
        String accessKey = "acckey";
        String secretKey = "seckey";
        auth = Auth.create(accessKey, secretKey);
    }

    public String getToken(){
        return auth.uploadToken("blackstone");
    }
}
