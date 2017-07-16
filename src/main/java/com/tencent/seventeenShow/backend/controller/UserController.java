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

import javax.xml.transform.Result;
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

    // 登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Response<LoginVo> firstLogin(LoginForm form){
        // 通过 openID 去查找 Token
        Token token = userService.findTokenByOpenId(form.getOpenId());
        // 如果 token 存在
        if(token != null){
            return new Response<LoginVo>(new LoginVo(token.getToken(), true, token.getSig()));
        }

        // 初始用户名，17show + openID 前四位
        String name = "17show" +form.getOpenId().substring(0,4);

        // 设置默认用户信息
        ChangeResumeVo vo = new ChangeResumeVo();
        vo.setUsername(name);

        userService.modifyResume(form.getOpenId(),vo);
        // 计算校验和
        String tokenAndId = form.getAccessToken() +form.getOpenId() + System.currentTimeMillis();

        String tokenStr = Utils.getHash(tokenAndId);
        long timeInterval = 24 * 60 * 60 * 1000 ;
        long expire = System.currentTimeMillis() + timeInterval;
        String sig = null; 
        try{
            sig = Utils.getSig(form.getOpenId());
        }catch(IOException e){
            return new Response<LoginVo>(ResultCode.ERROR_OPERATION_FAILED,"failed to get Sig");
        }
        if(!userService.regToHx(form.getOpenId())){
            return new Response<LoginVo>(ResultCode.ERROR_OPERATION_FAILED,"failed to reg to huaxin cloud");
        }
        userService.firstLogin(form.getAccessToken(), form.getOpenId(), tokenStr, new Date(expire), sig);
        return new Response<LoginVo>(new LoginVo(tokenStr, false,sig));

    }

    // 刷新Token
    @RequestMapping(value = "/refreshToken",method = RequestMethod.POST)
    @ResponseBody
    public Response<LoginVo> refreshToken(OAuthForm form) {

        long timeInterval = 24 * 60 * 60 * 1000;
        if (userService.findTokenByOpenId(form.getOpenId())!= null) {
            String tokenByFind = userService.findTokenByOpenId(form.getOpenId()).getToken();

            // 重放攻击
            if (Math.abs(System.currentTimeMillis() - form.getTimestamp()) > 60 * 1000){
                return new Response<LoginVo>(ResultCode.REPALY_ATTACK, "校验失败");
            }

            // 数据被篡改
            String accessToken = userService.findTokenByOpenId(form.getOpenId()).getAccessToken();
            String md = accessToken.substring(0, 16) + form.getOpenId().substring(17) + form.getTimestamp();
            if (!form.getCheckSum().toLowerCase().equals(Utils.MD5(md).toLowerCase())) {
                return new Response<LoginVo>(ResultCode.FALSIFY_DATA, "数据被篡改");
            }

            // toekn 过期
            if (System.currentTimeMillis() > userService.findTokenByOpenId(form.getOpenId()).getExpire().getTime()) {
                String tmp = form.getOpenId() + userService.findTokenByOpenId(form.getOpenId()).getAccessToken() + System.currentTimeMillis();
                String newToken = Utils.getHash(tmp);
                long expire = System.currentTimeMillis() + timeInterval;
                userService.updateToken(form.getOpenId(), newToken, new Date(expire));

                return new Response<LoginVo>(new LoginVo(newToken));
            }

            // 计算 token 过期时间
            long expire = userService.findTokenByOpenId(form.getOpenId()).getExpire().getTime() + timeInterval;
            userService.updateExpire(form.getOpenId(),new Date(expire));

            return new Response<LoginVo>(ResultCode.OK_CODE, "success", new LoginVo(userService.findTokenByOpenId(form.getOpenId()).getToken()));
        }
        return new Response<LoginVo>(ResultCode.ERROR_PARAMETER_WRONG, "无效的Token");
    }

    /**  
     * 获取我的标签
     * 
     * 通过 token 获取用户的 openID
     * 再利用 openID 去获取用户标签
     *
     */
    @RequestMapping(value = "/getlabel",method = RequestMethod.GET)
    @ResponseBody
    public Response<List<String>> getLabel(@RequestHeader("token")String token){
        if(userService.findOpenIdByToken(token)!=null){
            String openId = userService.findOpenIdByToken(token);
            return new Response<List<String>>(userService.getLabel(openId));
        }
        return new Response<List<String>>(ResultCode.ERROR_DEFAULT_CODE,"无用户标签信息");
    }

    /**
     * 匹配结果
     */
    @RequestMapping(value = "/peerResult", method = RequestMethod.GET)
    @ResponseBody
    public Response<PeerResultVo> peerResult(@RequestHeader("token")String token){
        User user = userService.getResume(userService.findOpenIdByToken(token));
        
        // 通过 openID 去获取匹配结果
        UserPeer peer =  PeerManager.g().getPeerResult(user);
        // 如果结果为空，返回匹配失败
        if(peer == null){
            return new Response<PeerResultVo>(ResultCode.ERROR_NOT_PEERED, "not peered");
        }
        else{
            return new Response<PeerResultVo>(new PeerResultVo(peer.getPeer(user.getOpenId()), peer.getRoomNumber(), user.equals(peer.getA())));
        }
    }

    /** 
     * 点击钻石 +5s 
     */
    @RequestMapping(value = "/clickdiamond",method = RequestMethod.GET)
    @ResponseBody
    public Response<Map<String,Long>> clickDiamond(@RequestHeader("token")String token){
        String openId = userService.findOpenIdByToken(token);

        // 如果用户的钻石余额少于1
        if(userService.getResume(openId).getDiamondBalance()<1){
            return new Response<Map<String, Long>>(ResultCode.ERROR_DEFAULT_CODE,"钻石不够");
        }
        // 消费钻石的操作
        if(userService.clickDiamond(openId)){
            User user = userService.getResume(openId);

            PeerManager.g().add5s(user);

            Long diamondBalance = user.getDiamondBalance();
            Map<String, Long> map = new HashMap<String,Long>();
            map.put("diamondBalance",diamondBalance);
            return new Response<Map<String,Long>>(map);
        }
        logger.info("====\n\ndiamond error");
        return new Response<Map<String, Long>>(ResultCode.ERROR_DEFAULT_CODE,"error");
    }

    @RequestMapping(value = "/diamondBalance", method = RequestMethod.GET)
    @ResponseBody
    public Response<Map<String,Long>> diamondBalance(@RequestHeader("token")String token){
        User user = userService.getResume(userService.findOpenIdByToken(token));
        Map<String ,Long> map = new HashMap<String, Long>();
        map.put("diamondBalance", user.getDiamondBalance());
        return new Response<Map<String, Long>>(map);
    }

    /**
     * 当前会话状态
     */
    @RequestMapping(value = "/peerStatus", method = RequestMethod.GET)
    @ResponseBody
    public Response<PeerResultVo> peerStatus(@RequestHeader("token")String token){
        User user = userService.getResume(userService.findOpenIdByToken(token));
        UserPeer peer = PeerManager.g().getPeerResult(user);
        return new Response<PeerResultVo>(new PeerResultVo(peer.getPeer(user.getOpenId()), peer.getRoomNumber(), user.equals(peer.getA()), peer.getTotalSeconds()));
    }

    /** 
     * 点击爱心
     */
    @RequestMapping(value = "/clicklove",method = RequestMethod.GET)
    @ResponseBody
    public Response<LoveBalanceVo> clickLove(@RequestHeader("token")String token){
        User user = userService.getResume(userService.findOpenIdByToken(token));
        // 给匹配对象比心
        if(PeerManager.g().clickLike(user)){
            userService.decLove(user.getOpenId());
        }
        return new Response<LoveBalanceVo>(new LoveBalanceVo(userService.getLoveNum(user.getOpenId())));
    }

    /** 
     * 点击 X (不喜欢)
     */
    @RequestMapping(value = "/clickdislike",method = RequestMethod.GET)
    @ResponseBody
    public Response clickDislike(@RequestHeader("token")String token){
        User user = userService.getResume(userService.findOpenIdByToken(token));
        // 给匹配对象打 X 
        PeerManager.g().clickDislike(user);
        return new Response();
    }

    /**
     * 双方最终的匹配结果
     */
    @RequestMapping(value = "/leaveMatch",method = RequestMethod.GET)
    @ResponseBody
    public Response leaveMatch(@RequestHeader("token")String token){
        User user = userService.getResume(userService.findOpenIdByToken(token));
        PeerManager.g().removePeer(user);
        return new Response();
    }

    /** 
     * 双方最终的匹配结果
     */
    @RequestMapping(value = "/matchResult",method = RequestMethod.GET)
    @ResponseBody
    public Response<MatchResultVo> matchResult(@RequestHeader("token")String token){
        User user = userService.getResume(userService.findOpenIdByToken(token));
        // 获取匹配结果
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

    /** 
     * 获取用户信息
     */
    @RequestMapping(value = "/getresume",method = RequestMethod.GET)
    @ResponseBody
    public Response<User> getResume(@RequestHeader("token")String token){
        String openId = userService.findOpenIdByToken(token);
        if(openId != null){
            // 从 user 表获取用户基本信息
            User user = userService.getResume(openId);
            // 从 label 表获取用户标签
            List<String> label = userService.getLabel(openId);
            // 设置 user 对象的标签属性
            user.setLabel(label);
            return new Response<User>(user);
        }
        return new Response<User>(ResultCode.ERROR_DEFAULT_CODE,"无用户信息");
    }

    /**
     * 获取其他用户信息
     */
    @RequestMapping(value = "/getResume",method = RequestMethod.POST)
    @ResponseBody
    public Response<User> getOtherResume(OpenIdForm form){
        if(form.getOpenId() != null){
            // 从 user 表获取用户基本信息
            User user = userService.getResume(form.getOpenId());
            // 从 label 表获取用户标签
            List<String> label = userService.getLabel(form.getOpenId());
            // 设置 user 对象的标签属性
            user.setLabel(label);
            return new Response<User>(user);
        }
        return new Response<User>(ResultCode.ERROR_DEFAULT_CODE,"无用户信息");
    }

    /** 
     * 充值钻石
     */
    @RequestMapping(value = "/addDiamond",method = RequestMethod.POST)
    @ResponseBody
    public Response addDiamond(@RequestHeader("token")String token,DiamondNum num) {
        String openId = userService.findOpenIdByToken(token);
        // 充值操作
        if(userService.addDiamond(openId,num.getDiamondNum())){
            return new Response();
        }
        return new Response(ResultCode.ERROR_DEFAULT_CODE,"充值失败");
    }


    /** 
     * 修改个人资料
     */
    @RequestMapping(value = "/modifyresume",method = RequestMethod.POST)
    @ResponseBody
    public Response modifyResume(@RequestHeader("token")String token, ChangeResumeVo vo) {
        String openId = userService.findOpenIdByToken(token);

        // 修改个人信息操作，返回 boolean 修改成功与否
        if(!userService.modifyResume(openId,vo)){
            return new Response(ResultCode.ERROR_DEFAULT_CODE,"修改个人资料失败");
        }
        return new Response();
    }

    /** 
     * 修改性别
     */
    @RequestMapping(value = "/modifygender",method = RequestMethod.POST)
    @ResponseBody
    public Response modifyGender(@RequestHeader("token")String token, GenderForm gender) {
        String openId = userService.findOpenIdByToken(token);

        // 修改性别操作
        if(userService.modifyGender(openId,gender.getGender())){
            return new Response();
        }
        return new Response(ResultCode.ERROR_DEFAULT_CODE,"修改性别失败");

    }

    /** 
     * 本地匹配是否开启
     */
    @RequestMapping(value = "/localMatch",method = RequestMethod.GET)
    @ResponseBody
    public Response localMatch(@RequestHeader("token")String token) {
        String openId = userService.findOpenIdByToken(token);
        if( userService.getResume(openId).getLocalMatch() == true )
            return  new Response();
        return new Response(ResultCode.ERROR_DEFAULT_CODE,"没有成功开启本地匹配");

    }

    /** 
     * 获取好友列表
     */
    @RequestMapping(value = "/getFriends",method = RequestMethod.GET)
    @ResponseBody
    public Response<List<User>> getFriends(@RequestHeader("token")String token) {
        String openId = userService.findOpenIdByToken(token);
        List<User> friends = userService.getFriends(openId);
        return new Response<List<User>>(friends);
    }

    /** 
     * 添加个人标签
     */
    @RequestMapping(value = "/insertLabel",method = RequestMethod.POST)
    @ResponseBody
    public Response insertLabel(@RequestHeader("token")String token,Label tags) {
        String openId = userService.findOpenIdByToken(token);
        // 拆分标签，以 , 为分割
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
