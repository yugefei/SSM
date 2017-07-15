package com.tencent.seventeenShow.backend.controller;
import com.tencent.seventeenShow.backend.conf.ResultCode;
import com.tencent.seventeenShow.backend.controller.form.*;
import com.tencent.seventeenShow.backend.controller.form.LoginForm;
import com.tencent.seventeenShow.backend.controller.vo.ChangeResumeVo;
import com.tencent.seventeenShow.backend.controller.form.GenderForm;
import com.tencent.seventeenShow.backend.controller.vo.LoginVo;
import com.tencent.seventeenShow.backend.model.*;
import com.tencent.seventeenShow.backend.service.UserService;
import com.tencent.seventeenShow.backend.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.HashMap;
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


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    //第一次登录
    public Response<LoginVo> firstLogin(@RequestBody LoginForm form){

        if(userService.findTokenByOpenId(form.getOpenId())!=null)
        {
            String token = userService.findTokenByOpenId(form.getOpenId()).getToken();

            return new Response<LoginVo>(new LoginVo(token, true));
        }
        String tokenAndId = form.getAccessToken() +form.getOpenId() + System.currentTimeMillis();

        String token = Utils.MD5(tokenAndId);
        long timeInterval = 24 * 60 * 60 * 1000 ;
        long expire = System.currentTimeMillis() + timeInterval;
        Date date = new Date(expire);
        userService.firstLogin(form.getAccessToken(), form.getOpenId(), token, date);
        return new Response<LoginVo>(new LoginVo(token, false));

    }


        //刷新Token
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
            if (System.currentTimeMillis() > userService.findTokenByOpenId(form.getOpenId()).getExpire().getTime()) {
                String tmp = form.getOpenId() + userService.findTokenByOpenId(form.getOpenId()).getAccessToken() + System.currentTimeMillis();
                String newToken = Utils.MD5(tmp);
                long expire = System.currentTimeMillis() + timeInterval;

                Date date = new Date(expire);
                userService.updateToken(form.getOpenId(), newToken, date);

                return new Response<String>(ResultCode.OK_CODE, "新的token", newToken);
            }

            Date expire = new Date(userService.findTokenByOpenId(form.getOpenId()).getExpire().getTime() + timeInterval);
            userService.updateExpire(form.getOpenId(),expire);

            return new Response<String>(ResultCode.OK_CODE, "success", userService.findTokenByOpenId(form.getOpenId()).getToken());
        }
        return new Response<String>(ResultCode.ERROR_PARAMETER_WRONG, "无效的Token");
    }

    //我的标签
    @RequestMapping(value = "/getlabel",method = RequestMethod.GET)
    @ResponseBody
    public Response<List<String>> getLabel(@RequestHeader("token")String token){
        if(userService.findOpenIdByToken(token)!=null)
        {
            String openId = userService.findOpenIdByToken(token);
            return new Response<List<String>>(ResultCode.OK_CODE,"success",userService.getLabel(openId));
        }
        return new Response<List<String>>(ResultCode.ERROR_DEFAULT_CODE,"无用户标签信息");
    }


    //开始写接口咯


    // 点击钻石+5s
    @RequestMapping(value = "/clickdiamond",method = RequestMethod.GET)
    @ResponseBody
    public Response<Map<String,Integer>> clickDiamond(@RequestHeader("token")String token){
        String openId = userService.findOpenIdByToken(token);
        if(userService.clickDiamond(openId))
        {
            User user = userService.getResume(openId);
            int diamondBalance = user.getDiamondBalance();
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("diamonBalance",diamondBalance);
            return new Response(map);
        }
        return new Response<Map<String, Integer>>(ResultCode.ERROR_DEFAULT_CODE,"error");

    }

/*
    // 点击爱心
    @RequestMapping(value = "/clicklove",method = RequestMethod.GET)
    @ResponseBody
    public Response clickLove(@RequestHeader("token")String token, ClickDiamond diamond){
        userService.clickLove(diamond.getOpenId());
        String openId1 = userService.findOpenIdByToken(token);
        String openId2 = diamond.getOpenId();
        userService.changeMatch(openId1,openId2);

        return new Response(ResultCode.OK_CODE,"点击喜欢成功");
    }

    //点击X
    @RequestMapping(value = "/clickdislike",method = RequestMethod.GET)
    @ResponseBody
    public Response disLike(@RequestHeader("token")String token,  ClickDiamond diamond){
        userService.clickDislike(diamond.getOpenId());
        String openId1 = userService.findOpenIdByToken(token);
        String openId2 = diamond.getOpenId();
        userService.changeNotMatch(openId1,openId2);

        return new Response(ResultCode.OK_CODE,"点击不喜欢成功");

    }
    */
/*

    // 是否相互点击爱心
    @RequestMapping(value = "/isMatch",method = RequestMethod.GET)
    @ResponseBody
    public Response<Integer> isMatch(@RequestHeader("token")String token, ClickDiamond diamond){

        String openId1 = userService.findOpenIdByToken(token);
        String openId2 = diamond.getOpenId();
        if(userService.isMatch(openId1,openId2))
            return new Response<Integer>(ResultCode.OK_CODE,"互相喜欢",1);

        return new Response<Integer>(ResultCode.OK_CODE,"不是相互喜欢成功",0);
    }*/

    //获取用户信息
    @RequestMapping(value = "/getresume",method = RequestMethod.GET)
    @ResponseBody
    public Response<User> getResume(@RequestHeader("token")String token){
        if(userService.findOpenIdByToken(token)!=null)
        {
            String openId = userService.findOpenIdByToken(token);
            User user = userService.getResume(openId);
            return new Response<User>(user);
        }
        return new Response<User>(ResultCode.ERROR_DEFAULT_CODE,"无用户信息");
    }
/*
    //获取好友信息
    @RequestMapping(value = "/getfriendinfo",method = RequestMethod.GET)
    @ResponseBody
    public Response<FriendInfo> getFriendInfo(@RequestHeader("token")String token,ClickDiamond diamond){
        if(userService.findOpenIdByToken(token)!=null)
        {
            String openId1 = userService.findOpenIdByToken(token);
            String openId2 = diamond.getOpenId();
            if(userService.isMatch(openId1,openId2))
            {
                FriendInfo info = userService.getFriendInfo(openId2);
                return new Response<FriendInfo>(ResultCode.OK_CODE,"friendInfo",info);
            }
        }
        return new Response<FriendInfo>(ResultCode.ERROR_DEFAULT_CODE,"不是好友关系",null);
    }

*/
        //充值钻石
    @RequestMapping(value = "/addDiamond",method = RequestMethod.GET)
    @ResponseBody
    public Response addDiamond(@RequestHeader("token")String token) {
        String openId = userService.findOpenIdByToken(token);
        if(userService.addDiamond(openId))
        {
            int diamondBalance = userService.getResume(openId).getDiamondBalance();
            return new Response();
        }
        return new Response(ResultCode.ERROR_DEFAULT_CODE,"充值失败");
    }


        //修改个人资料
    @RequestMapping(value = "/modifyresume",method = RequestMethod.GET)
    @ResponseBody
    public Response modifyResume(@RequestHeader("token")String token, ChangeResumeVo vo) {
        String openId = userService.findOpenIdByToken(token);
        if(userService.modifyResume(openId,vo))
        {
            return new Response();

        }
       return new Response(ResultCode.ERROR_DEFAULT_CODE,"修改个人资料失败");
    }

        //  修改性别
    @RequestMapping(value = "/modifygender",method = RequestMethod.POST)
    @ResponseBody
    public Response modifyGender(@RequestHeader("token")String token, GenderForm gender) {
        String openId = userService.findOpenIdByToken(token);
        if(userService.modifyGender(openId,gender.getGender()))
        {
            return new Response();
        }
        return new Response(ResultCode.ERROR_DEFAULT_CODE,"修改性别失败");

    }
    // 是否开启本地匹配
    @RequestMapping(value = "/localMatch",method = RequestMethod.GET)
    @ResponseBody
    public Response localMatch(@RequestHeader("token")String token) {
        String openId = userService.findOpenIdByToken(token);
        if( userService.getResume(openId).getLocalMatch() == 1 )
            return  new Response<Integer>();
        return new Response<Integer>(ResultCode.ERROR_DEFAULT_CODE,"没有成功开启本地匹配");

    }
}
