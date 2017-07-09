package cn.edu.njue.blackStone.backend.controller;

import cn.edu.njue.blackStone.backend.conf.ResultCode;
import cn.edu.njue.blackStone.backend.controller.form.LoginForm;
import cn.edu.njue.blackStone.backend.controller.form.NewPwdForm;
import cn.edu.njue.blackStone.backend.controller.form.RegisterForm;
import cn.edu.njue.blackStone.backend.controller.form.SendSmsForm;
import cn.edu.njue.blackStone.backend.controller.vo.LoginVo;
import cn.edu.njue.blackStone.backend.mem.SmsCode.SmsCodeManager;
import cn.edu.njue.blackStone.backend.mem.SmsCode.SmsCodeSendStatus;
import cn.edu.njue.blackStone.backend.mem.SmsCode.SmsCodeType;
import cn.edu.njue.blackStone.backend.mem.TokenManager;
import cn.edu.njue.blackStone.backend.mem.TokenModel;
import cn.edu.njue.blackStone.backend.model.Response;
import cn.edu.njue.blackStone.backend.model.User;
import cn.edu.njue.blackStone.backend.service.UserService;
import cn.edu.njue.blackStone.backend.utils.Utils;
import cn.edu.njue.blackStone.backend.utils.exception.MobileOccupiedException;
import cn.edu.njue.blackStone.backend.utils.exception.StudentIdOccupiedException;
import cn.edu.njue.blackStone.backend.utils.exception.StudentNameException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.HashMap;
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

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Response<LoginVo>login(@RequestBody LoginForm form){
        User user = userService.login(form.getUsername(),form.getPwd());
        if(user !=  null){
            if(user.getAvatar() != null){
                user.setAvatar("http://img.blackstone.ebirdnote.cn/" + user.getAvatar());
            }
            TokenManager manager = TokenManager.getInstance();
            TokenModel model = manager.tokenize(user);
            LoginVo vo = new LoginVo();
            vo.setUser(user);
            vo.setToken(model.getToken());
            vo.setExpireAt(model.getExpireTime().getTime());
            return new Response<LoginVo>(vo);
        }else{
            return new Response<LoginVo>(ResultCode.ERROR_PARAMETER_WRONG,"用户名/密码不匹配");
        }
    }



    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Response registerPhone(@RequestBody RegisterForm form){
        if(!SmsCodeManager.getInstance().check(form.getMobile(),SmsCodeType.register,form.getVerifyCode())){
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
        String stuCode = TokenManager.getInstance().getUser(token).getStudentId();
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
