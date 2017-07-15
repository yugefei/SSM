package com.tencent.seventeenShow.backend.controller;
import com.tencent.seventeenShow.backend.conf.ResultCode;
import com.tencent.seventeenShow.backend.controller.form.*;
import com.tencent.seventeenShow.backend.controller.form.LoginForm;
import com.tencent.seventeenShow.backend.controller.vo.*;
import com.tencent.seventeenShow.backend.controller.form.GenderForm;
import com.tencent.seventeenShow.backend.mem.PeerManager;
import com.tencent.seventeenShow.backend.model.*;
import com.tencent.seventeenShow.backend.service.UserService;
import com.tencent.seventeenShow.backend.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

import static com.tencent.seventeenShow.backend.model.UserPeer.kMATCHED;
import static com.tencent.seventeenShow.backend.model.UserPeer.kRESULT_UNKNOWN;
import static com.tencent.seventeenShow.backend.model.UserPeer.kUNMATCHED;


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
    public Response<LoginVo> firstLogin(LoginForm form){

        if(userService.findTokenByOpenId(form.getOpenId())!=null)
        {
            Token token = userService.findTokenByOpenId(form.getOpenId());

            return new Response<LoginVo>(new LoginVo(token.getToken(), true, token.getSig()));
        }
        String name = "17show" +form.getOpenId().substring(0,4);

        ChangeResumeVo vo = new ChangeResumeVo();
        vo.setUsername(name);

        userService.modifyResume(form.getOpenId(),vo);
        String tokenAndId = form.getAccessToken() +form.getOpenId() + System.currentTimeMillis();

        String token = Utils.getHash(tokenAndId);
        long timeInterval = 24 * 60 * 60 * 1000 ;
        long expire = System.currentTimeMillis() + timeInterval;
        String sig = null;
        try{
            sig = Utils.getSig(form.getOpenId());
        }catch(IOException e){
            return new Response<LoginVo>(ResultCode.ERROR_OPERATION_FAILED,"failed to get Sig");
        }
        userService.firstLogin(form.getAccessToken(), form.getOpenId(), token, new Date(expire), sig);
        return new Response<LoginVo>(new LoginVo(token, false,sig));

    }

    //刷新Token
    @RequestMapping(value = "/refreshToken",method = RequestMethod.POST)
    @ResponseBody
    public Response<LoginVo> refreshToken(OAuthForm form) {

        long timeInterval = 24 * 60 * 60 * 1000;
        if (userService.findTokenByOpenId(form.getOpenId())!= null) {
            String tokenByFind = userService.findTokenByOpenId(form.getOpenId()).getToken();

            //重放攻击
            if (Math.abs(System.currentTimeMillis() - form.getTimestamp()) > 60 * 1000)
                return new Response<LoginVo>(ResultCode.REPALY_ATTACK, "校验失败");

            //数据被篡改
            String accessToken = userService.findTokenByOpenId(form.getOpenId()).getAccessToken();
            String md = accessToken.substring(0, 16) + form.getOpenId().substring(17) + form.getTimestamp();
            if (!form.getCheckSum().toLowerCase().equals(Utils.MD5(md).toLowerCase())) {
                return new Response<LoginVo>(ResultCode.FALSIFY_DATA, "数据被篡改");
            }

            //toekn 过期
            if (System.currentTimeMillis() > userService.findTokenByOpenId(form.getOpenId()).getExpire().getTime()) {
                String tmp = form.getOpenId() + userService.findTokenByOpenId(form.getOpenId()).getAccessToken() + System.currentTimeMillis();
                String newToken = Utils.getHash(tmp);
                long expire = System.currentTimeMillis() + timeInterval;
                userService.updateToken(form.getOpenId(), newToken, new Date(expire));

                return new Response<LoginVo>(new LoginVo(newToken));
            }

            long expire = userService.findTokenByOpenId(form.getOpenId()).getExpire().getTime() + timeInterval;
            userService.updateExpire(form.getOpenId(),new Date(expire));

            return new Response<LoginVo>(ResultCode.OK_CODE, "success", new LoginVo(userService.findTokenByOpenId(form.getOpenId()).getToken()));
        }
        return new Response<LoginVo>(ResultCode.ERROR_PARAMETER_WRONG, "无效的Token");
    }

    //我的标签 testok
    @RequestMapping(value = "/getlabel",method = RequestMethod.GET)
    @ResponseBody
    public Response<List<String>> getLabel(@RequestHeader("token")String token){
        if(userService.findOpenIdByToken(token)!=null)
        {
            String openId = userService.findOpenIdByToken(token);
            return new Response<List<String>>(userService.getLabel(openId));
        }
        return new Response<List<String>>(ResultCode.ERROR_DEFAULT_CODE,"无用户标签信息");
    }

    // testok
    @RequestMapping(value = "/peerResult", method = RequestMethod.GET)
    @ResponseBody
    public Response<PeerResultVo> peerResult(@RequestHeader("token")String token){
        User user = userService.getResume(userService.findOpenIdByToken(token));
        UserPeer peer =  PeerManager.g().getPeerResult(user);

        if(peer == null)
            return new Response<PeerResultVo>(ResultCode.ERROR_NOT_PEERED, "not peered");
        else
            return new Response<PeerResultVo>(new PeerResultVo(peer.getPeer(user.getOpenId()), peer.getRoomNumber(), user.equals(peer.getA())));
    }

    // 点击钻石+5s testok
    @RequestMapping(value = "/clickdiamond",method = RequestMethod.GET)
    @ResponseBody
    public Response<Map<String,Long>> clickDiamond(@RequestHeader("token")String token){
        String openId = userService.findOpenIdByToken(token);
        if(userService.getResume(openId).getDiamondBalance()<1)
            return new Response<Map<String, Long>>(ResultCode.ERROR_DEFAULT_CODE,"钻石不够");
        if(userService.clickDiamond(openId))
        {
            User user = userService.getResume(openId);
            Long diamondBalance = user.getDiamondBalance();
            Map<String, Long> map = new HashMap<String,Long>();
            map.put("diamonBalance",diamondBalance);
            return new Response<Map<String,Long>>(map);
        }
        return new Response<Map<String, Long>>(ResultCode.ERROR_DEFAULT_CODE,"error");

    }

    // 点击爱心
    @RequestMapping(value = "/clicklove",method = RequestMethod.GET)
    @ResponseBody
    public Response<LoveBalanceVo> clickLove(@RequestHeader("token")String token){
        User user = userService.getResume(userService.findOpenIdByToken(token));
        PeerManager.g().clickLike(user);
        return new Response<LoveBalanceVo>(new LoveBalanceVo(userService.getLoveNum(user.getOpenId())));
    }

    @RequestMapping(value = "/clickdislike",method = RequestMethod.GET)
    @ResponseBody
    public Response clickDislike(@RequestHeader("token")String token){
        User user = userService.getResume(userService.findOpenIdByToken(token));
        PeerManager.g().clickDislike(user);
        return new Response();
    }

    @RequestMapping(value = "/matchResult",method = RequestMethod.GET)
    @ResponseBody
    public Response<MatchResultVo> matchResult(@RequestHeader("token")String token){
        User user = userService.getResume(userService.findOpenIdByToken(token));
        int result = PeerManager.g().matchResult(user);
        if(result == kRESULT_UNKNOWN){
            return new Response<MatchResultVo>(new MatchResultVo("unknown"));
        }else if(result == kMATCHED){
            return new Response<MatchResultVo>(new MatchResultVo("matched"));
        }else if(result == kUNMATCHED){
            return new Response<MatchResultVo>(new MatchResultVo("unmatched"));
        }
        return new Response<MatchResultVo>();
    }

/*


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

    //获取用户信息 testok
    @RequestMapping(value = "/getresume",method = RequestMethod.GET)
    @ResponseBody
    public Response<User> getResume(@RequestHeader("token")String token){
        if(userService.findOpenIdByToken(token)!=null)
        {
            String openId = userService.findOpenIdByToken(token);
            User user = userService.getResume(openId);
            List<String> label = userService.getLabel(openId);
            user.setLabel(label);

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
        //充值钻石 testok
    @RequestMapping(value = "/addDiamond",method = RequestMethod.POST)
    @ResponseBody
    public Response addDiamond(@RequestHeader("token")String token,DiamondNum num) {
        String openId = userService.findOpenIdByToken(token);
        if(userService.addDiamond(openId,num.getDiamondNum()))
        {
            return new Response();
        }
        return new Response(ResultCode.ERROR_DEFAULT_CODE,"充值失败");
    }


        //修改个人资料 testok
    @RequestMapping(value = "/modifyresume",method = RequestMethod.POST)
    @ResponseBody
    public Response modifyResume(@RequestHeader("token")String token, ChangeResumeVo vo) {
        String openId = userService.findOpenIdByToken(token);
      //  String[] labels = vo.getLabel().split(",");

        if(!userService.modifyResume(openId,vo))
        {
            return new Response(ResultCode.ERROR_DEFAULT_CODE,"修改个人资料失败");
        }
/*
        if (!userService.insertLabel(openId,labels)){
            return new Response(ResultCode.ERROR_DEFAULT_CODE,"修改个人资料失败");
        }
*/
        return new Response();
    }

        //  修改性别 testok
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
        if( userService.getResume(openId).getLocalMatch() == true )
            return  new Response();
        return new Response(ResultCode.ERROR_DEFAULT_CODE,"没有成功开启本地匹配");

    }

    //获取好友信息
    @RequestMapping(value = "/getFriends",method = RequestMethod.GET)
    @ResponseBody
    public Response<List<User>> getFriends(@RequestHeader("token")String token) {
        String openId = userService.findOpenIdByToken(token);
        List<User> friends = userService.getFriends(openId);

        return new Response<List<User>>(friends);
    }

    @RequestMapping(value = "/insertLabel",method = RequestMethod.POST)
    @ResponseBody
    public Response insertLabel(@RequestHeader("token")String token,Label tags) {
        String openId = userService.findOpenIdByToken(token);
        if(!tags.getTags().contains(",")) {
           String[] lab = new String[1];
           lab[0] = tags.getTags();
           userService.insertLabel(openId,lab);
           return new Response();
        }
        String[] labels = tags.getTags().split(",");
        userService.insertLabel(openId, labels);
        return new Response();
    }
}
