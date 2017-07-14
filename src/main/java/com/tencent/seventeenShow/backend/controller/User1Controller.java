package com.tencent.seventeenShow.backend.controller;

import com.tencent.seventeenShow.backend.conf.ResultCode;
import com.tencent.seventeenShow.backend.controller.form.LoginForm;
import com.tencent.seventeenShow.backend.controller.vo.LoginVo;
import com.tencent.seventeenShow.backend.mem.TokenManager;
import com.tencent.seventeenShow.backend.mem.TokenModel;
import com.tencent.seventeenShow.backend.model.Response;
import com.tencent.seventeenShow.backend.model.User;
import com.tencent.seventeenShow.backend.model.User1;
import com.tencent.seventeenShow.backend.service.User1Service;
import com.tencent.seventeenShow.backend.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.tencent.seventeenShow.backend.conf.Character.user;
import static sun.nio.ch.sctp.SctpMultiChannelImpl.message;

/**
 * Created by gefeiyu on 2017/7/10.
 */
@Controller("user1COntroller")
@RequestMapping("/user")
public class User1Controller extends BaseController {
    protected Logger logger = Logger.getLogger(User1Controller.class);
    @Autowired
    private User1Service user1Service;
    private UserService userService;

    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    public Response<LoginVo>login(@RequestBody LoginForm form)
    {
        User1 user1 = user1Service.login(form.getUsername(),form.getPwd());
        User user = userService.login(form.getUsername(),form.getPwd());
        if(user1!=null)
        {
            if(user1.getAvatar() != null){
                user1.setAvatar("http://img.blackstone.ebirdnote.cn/" + user1.getAvatar());
            }
            TokenManager manager = TokenManager.getInstance();
            TokenModel model = manager.tokenize(user);
            LoginVo vo = new LoginVo();
            vo.setUser(user);
            vo.setToken(model.getToken());
            vo.setExpireAt(model.getExpireTime().getTime());
            return new Response<LoginVo>(vo);
        }
        else
        {
            return new Response(ResultCode.ERROR_OPERATION_FAILED,"service error");
        }
    }
}

