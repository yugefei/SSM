package com.tencent.seventeenShow.backend.controller;

import com.tencent.seventeenShow.backend.conf.ResultCode;
import com.tencent.seventeenShow.backend.controller.form.*;
import com.tencent.seventeenShow.backend.controller.vo.DiamondVo;
import com.tencent.seventeenShow.backend.controller.vo.FriendsVo;
import com.tencent.seventeenShow.backend.controller.vo.LoginVo;
import com.tencent.seventeenShow.backend.dao.UserMapper;
import com.tencent.seventeenShow.backend.mem.SmsCode.SmsCodeManager;
import com.tencent.seventeenShow.backend.mem.SmsCode.SmsCodeSendStatus;
import com.tencent.seventeenShow.backend.mem.SmsCode.SmsCodeType;
import com.tencent.seventeenShow.backend.mem.TokenManager;
import com.tencent.seventeenShow.backend.model.Privilege;
import com.tencent.seventeenShow.backend.model.Response;
import com.tencent.seventeenShow.backend.model.User;
import com.tencent.seventeenShow.backend.service.UserService;
import com.tencent.seventeenShow.backend.utils.Utils;
import com.tencent.seventeenShow.backend.utils.exception.MobileOccupiedException;
import com.tencent.seventeenShow.backend.utils.exception.StudentIdOccupiedException;
import com.tencent.seventeenShow.backend.utils.exception.StudentNameException;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Edward on 2017/2/7 007.
 */
@Controller("userController")
@RequestMapping("/user")
public class UserController extends BaseController {

    protected Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    private UserMapper userMapper;

//    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    @ResponseBody
//    public Response<LoginVo> login(@RequestBody LoginForm form){
////        User user = userService.login(form.getUsername(),form.getPwd());
////        if(user !=  null){
////            if(user.getAvatar() != null){
////                user.setAvatar("http://img.blackstone.ebirdnote.cn/" + user.getAvatar());
////            }
////            TokenManager manager = TokenManager.getInstance();
////            TokenModel model = manager.tokenize(user);
////            LoginVo vo = new LoginVo();
////            vo.setUser(user);
////            vo.setToken(model.getToken());
////            vo.setExpireAt(model.getExpireTime().getTime());
////            return new Response<LoginVo>(vo);
////        }else{
////            return new Response<LoginVo>(ResultCode.ERROR_PARAMETER_WRONG,"用户名/密码不匹配");
////        }
//
//    }



    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Response registerPhone(@RequestBody RegisterForm form){
        if(!SmsCodeManager.getInstance().check(form.getMobile(), SmsCodeType.register,form.getVerifyCode())){
            return new Response(ResultCode.ERROR_PARAMETER_WRONG,"验证码错误");
        }

        User user = new User();
        Utils.copyProperties(user,form);
        try{
            userService.register(user,form.getPwd());
        }catch(MobileOccupiedException e){
            return new Response(ResultCode.ERROR_USER_MOBILE_OCCUPIED,"手机号已注册");
        }catch (StudentIdOccupiedException e){
            return new Response(ResultCode.ERROR_USER_STUDENT_REGISTERED,"该学号已注册,请登录");
        }catch (StudentNameException e){
            return new Response(ResultCode.ERROR_USER_ID_NAME_NOTMATCHED,"学号与姓名不匹配");
        }

        SmsCodeManager.getInstance().remove(form.getMobile()); //移除token
        return new Response();
    }



    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Response<LoginVo> firstLogin(LoginForm form){
        if(userService.findTokenByOpenId(form.getOpenId())!=null)
        {
            String token = userService.findTokenByOpenId(form.getOpenId()).getToken();

            return new Response<LoginVo>(new LoginVo(token, true));
        }

        String tokenAndId = form.getAccessToken() +form.getOpenId() + System.currentTimeMillis();

        String token = Utils.getHash(tokenAndId);
        long timeInterval = 24 * 60 * 60 * 1000 ;
        long expire = System.currentTimeMillis() + timeInterval;
        userService.firstLogin(form.getAccessToken(), form.getOpenId(), token, 0);
        return new Response<LoginVo>(new LoginVo(token, false));

    }



    @RequestMapping(value = "/refreshToken",method = RequestMethod.POST)
    @ResponseBody
    public Response<String> refreshToken(@RequestBody OAuthForm form) {
        long timeInterval = 24 * 60 * 60 * 1000;
        if (userService.findTokenByOpenId(form.getOpenId())!= null) {
            String tokenByFind = userService.findTokenByOpenId(form.getOpenId()).getToken();

            //重放攻击
            if (Math.abs(System.currentTimeMillis() - form.getTimestamp()) > 60)
                return new Response<String>(ResultCode.REPALY_ATTACK, "校验失败");

            //数据被篡改
            String accessToken = userService.findTokenByOpenId(form.getOpenId()).getAccessToken();
            String md = accessToken.substring(0, 17) + form.getOpenId().substring(17) + form.getTimestamp();
            if (!form.getCheckSum().equals(Utils.MD5(md))) {
                return new Response<String>(ResultCode.FALSIFY_DATA, "数据被篡改");
            }

            //toekn 过期
            if (System.currentTimeMillis() > userService.findTokenByOpenId(form.getOpenId()).getExpire()) {
                String tmp = form.getOpenId() + userService.findTokenByOpenId(form.getOpenId()).getAccessToken() + System.currentTimeMillis();
                String newToken = Utils.getHash(tmp);
                long expire = System.currentTimeMillis() + timeInterval;
                userService.updateToken(form.getOpenId(), newToken, expire);

                return new Response<String>(ResultCode.OK_CODE, "新的token", newToken);
            }

            long expire = userService.findTokenByOpenId(form.getOpenId()).getExpire() + timeInterval;
            userService.updateExpire(form.getOpenId(),expire);

            return new Response<String>(ResultCode.OK_CODE, "success", userService.findTokenByOpenId(form.getOpenId()).getToken());
        }
        return new Response<String>(ResultCode.ERROR_PARAMETER_WRONG, "无效的Token");
    }



   @RequestMapping(value = "/balances",method = RequestMethod.GET)
   @ResponseBody
   public Response<Privilege> getBalance(@RequestHeader("token")String token){
        Long userId = TokenManager.getInstance().getUser(token).getId();
       Privilege privilege = userService.getPrivilegeByName(userId);
       return new Response<Privilege>(privilege);
   }


    @RequestMapping(value = "/peer/pair/like",method = RequestMethod.GET)
    @ResponseBody
    public Response clickLike(@RequestHeader("token")String token){
        Long userId = TokenManager.getInstance().getUser(token).getId();
        if(userService.clickLike(userId))
          return new Response(ResultCode.OK_CODE,"点击喜欢成功");
        return new Response(ResultCode.CLICK_LIKE_FAIL,"点击喜欢失败");

    }


    @RequestMapping(value = "/peer/pair/dislike",method = RequestMethod.GET)
    @ResponseBody
    public Response clickDislike(@RequestHeader("token")String token){
        Long userId = TokenManager.getInstance().getUser(token).getId();
       if( userService.clickDislike(userId))
        return new Response(ResultCode.OK_CODE,"点击不喜欢成功");
       return new Response(ResultCode.Click_DISLIKE_FAIL,"点击不喜欢失败");
    }


   @RequestMapping(value = "/peer/extendTime",method = RequestMethod.GET)
   @ResponseBody
   public Response<DiamondVo> extendTime(@RequestHeader("token")String token){
            Long userId = TokenManager.getInstance().getUser(token).getId();
            int diamond = userService.extendTime(userId);
            DiamondVo diamondVo = new DiamondVo();
            diamondVo.setDiamond(diamond);
            return new Response<DiamondVo>();
   }

    @RequestMapping(value = "/custom/buyfilter",method = RequestMethod.POST)
    @ResponseBody
    public Response<DiamondVo> buyFilter(@RequestHeader("token")String token){
        Long userId = TokenManager.getInstance().getUser(token).getId();
        int diamond = userService.buyFilter(userId);
        DiamondVo diamondVo = new DiamondVo();
        diamondVo.setDiamond(diamond);
        return new Response<DiamondVo>();
    }

    @RequestMapping(value = "/getresume",method = RequestMethod.GET)
    @ResponseBody
    public Response<User> getResume(@RequestHeader("token")String token){
        Long userId = TokenManager.getInstance().getUser(token).getId();
        User user = new User();
        user = userService.getResume(userId);
        return new Response<User>(user);
    }


    @RequestMapping(value = "/getfriends",method = RequestMethod.GET)
    @ResponseBody
    public Response<FriendsVo> getFreinds(@RequestHeader("token")String token){
        Long userId = TokenManager.getInstance().getUser(token).getId();
        FriendsVo friendsVo = new FriendsVo();
        ArrayList<User> list = new ArrayList<User>();
        list = userService.getFriends(userId);
        friendsVo.setFriends(list);
        return new Response<FriendsVo>(friendsVo);
    }

    @RequestMapping(value = "/peer/start",method = RequestMethod.GET)
    @ResponseBody
    public Response startMatch(@RequestHeader("token")String token){
        Long userId = TokenManager.getInstance().getUser(token).getId();
        User user = userService.startMatch(userId);
        if(user!=null)
        return new Response<User>(user);
        return new Response(ResultCode.MATCH_FAIL,"匹配失败");
    }

    @RequestMapping(value = "/fillinresume",method = RequestMethod.POST)
    @ResponseBody
    public Response fillInResume(@RequestHeader("token")String token, @RequestBody List<Map<String,String>> resume){
        Long userId = TokenManager.getInstance().getUser(token).getId();
        User user = userService.startMatch(userId);
        user.setMobile(resume.get(0).get("mobile"));
        user.setGender(resume.get(1).get("gender"));
        user.setBirthday(resume.get(2).get("birthday"));
        user.setNickname(resume.get(3).get("nickname"));
        if(user!=null)
            return new Response<User>(user);
        return new Response(ResultCode.INCOMPLETE_FILLIN,"未填写");
    }




    @RequestMapping(value = "/verifyCode/mobile",method = RequestMethod.POST)
    @ResponseBody
    public Response sendSmsCode(@RequestBody SendSmsForm form) throws Exception{
        if(userService.userNameTaken(form.getNumber())){
            return new Response(ResultCode.ERROR_PARAMETER_WRONG,"手机已绑定");
        }

        SmsCodeSendStatus status = SmsCodeManager.getInstance().sendSms(form.getNumber(), SmsCodeType.register, null);
        if(status != SmsCodeSendStatus.success){
            if(status != SmsCodeSendStatus.unknown){
                return new Response(ResultCode.ERROR_OPERATION_FAILED,this.messageFromSmsCode(status));
            }
            return new Response(ResultCode.ERROR_OPERATION_FAILED,"服务器错误");
        }

        return new Response();
    }

    @RequestMapping(value = "/verifyCode/changeMobile",method = RequestMethod.POST)
    @ResponseBody
    public Response sendChangeMobileSmsCode(@RequestBody SendSmsForm form, @RequestHeader("token")String token) throws Exception{
        String stuCode = TokenManager.getInstance().getUser(token).getUsername();
        if(userService.userNameTaken(form.getNumber())){
            return new Response(ResultCode.ERROR_PARAMETER_WRONG,"手机已绑定");
        }

        SmsCodeSendStatus status = SmsCodeManager.getInstance().sendSms(form.getNumber(), SmsCodeType.changeMobile, stuCode);
        if(status != SmsCodeSendStatus.success){
            if(status != SmsCodeSendStatus.unknown){
                return new Response(ResultCode.ERROR_OPERATION_FAILED,this.messageFromSmsCode(status));
            }
            return new Response(ResultCode.ERROR_OPERATION_FAILED,"服务器错误");
        }

        return new Response();
    }



    @RequestMapping(value = "/forgetPwd/verifyCode/mobile",method = RequestMethod.POST)
    @ResponseBody
    public Response sendSmsForgetPasswordVerifyCode(@RequestBody SendSmsForm form) throws Exception{
        if(!userService.userNameTaken(form.getNumber())){
            return new Response(ResultCode.ERROR_PARAMETER_WRONG,"该用户名未注册");
        }

        SmsCodeSendStatus status = SmsCodeManager.getInstance().sendSms(form.getNumber(), SmsCodeType.forgetPwd, null);
        if(status != SmsCodeSendStatus.success){
            if(status != SmsCodeSendStatus.unknown){
                return new Response(ResultCode.ERROR_OPERATION_FAILED,this.messageFromSmsCode(status));
            }
            return new Response(ResultCode.ERROR_OPERATION_FAILED,"服务器错误");
        }

        return new Response();
    }

    @RequestMapping(value = "/forgetPwd/setPwd",method = RequestMethod.POST)
    @ResponseBody
    public Response resetPwd(@RequestBody RegisterForm form){
        if(!SmsCodeManager.getInstance().check(form.getMobile(),SmsCodeType.forgetPwd,form.getVerifyCode())){
            return new Response(ResultCode.ERROR_PARAMETER_WRONG,"验证码错误");
        }
        return new Response(userService.resetPwd(form.getMobile(),form.getPwd()),ResultCode.ERROR_PARAMETER_WRONG,"无此用户");
    }

    @RequestMapping(value = "/changeMobile",method = RequestMethod.POST)
    @ResponseBody
    public Response rebunding(@RequestBody RegisterForm form, @RequestHeader("token")String token){
        Long userId = TokenManager.getInstance().getUser(token).getId();
        if(!SmsCodeManager.getInstance().check(form.getMobile(),SmsCodeType.changeMobile,form.getVerifyCode())){
            return new Response(ResultCode.ERROR_PARAMETER_WRONG,"验证码错误");
        }
        return new Response(userService.changeMobile(form.getMobile(),userId),ResultCode.ERROR_PARAMETER_WRONG,"更换绑定手机号失败");
    }

//    @RequestMapping(value = "/check",method = RequestMethod.POST)
//    @ResponseBody
//    public Response checkUserName(@RequestBody User user){
//        return new Response<Boolean>(!userService.userNameTaken(user.getStudentId()));
//    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
    public Response logout(HttpServletRequest request){
        TokenManager.getInstance().logout(request.getHeader("token"));
        return new Response();
    }

    @RequestMapping(value = "/avatar",method = RequestMethod.POST)
    @ResponseBody
    public Response setAvatar(@RequestHeader("token")String token, @RequestBody User user){
        Long userId = TokenManager.getInstance().getUser(token).getId();
        return new Response(userService.setAvatar(user.getAvatar(), userId), ResultCode.ERROR_DEFAULT_CODE, "设置头像失败");
    }

    @RequestMapping(value = "/pwd",method = RequestMethod.POST)
    @ResponseBody
    public Response setPwd(@RequestHeader("token")String token, @RequestBody NewPwdForm user){
        Long userId = TokenManager.getInstance().getUser(token).getId();
        return new Response(userService.setPwd(user.getOriginPwd(),user.getNewPwd() , userId), ResultCode.ERROR_DEFAULT_CODE, "设置密码失败");
    }

    private String messageFromSmsCode(SmsCodeSendStatus status){
        switch (status){
            case fakePhone:
                return "手机号不正确";
            case frequencyLimit:
                return "向该号码发送短信过于频繁";
             default:
                 return "服务器错误";
        }
    }


}
