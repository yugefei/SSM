package com.tencent.seventeenShow.backend.controller;

import com.tencent.seventeenShow.backend.controller.vo.UploadTokenVo;
import com.tencent.seventeenShow.backend.mem.UploadTokenManager;
import com.tencent.seventeenShow.backend.model.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by Edward on 2017/3/31 031.
 */
@Controller("uploadController")
@RequestMapping("/upload")
public class UploadController extends BaseController{

    @ResponseBody
    @RequestMapping(value = "/token",method = RequestMethod.GET)
    public Response<UploadTokenVo> getUploadToken(){
        UploadTokenVo vo = new UploadTokenVo();
        vo.setToken(UploadTokenManager.getInstance().getToken());
        vo.setExpireAt(new Date(System.currentTimeMillis() + 3600 * 1000));
        return new Response<UploadTokenVo>(vo);
    }


}
