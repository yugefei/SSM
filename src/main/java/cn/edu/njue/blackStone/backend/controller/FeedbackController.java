package cn.edu.njue.blackStone.backend.controller;

import cn.edu.njue.blackStone.backend.conf.ResultCode;
import cn.edu.njue.blackStone.backend.mem.TokenManager;
import cn.edu.njue.blackStone.backend.model.Feedback;
import cn.edu.njue.blackStone.backend.model.Response;
import cn.edu.njue.blackStone.backend.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by EdwardZhou on 2017/5/27.
 * All Rights Reserved
 */
@RequestMapping("/feedback")
@Controller("feedbackController")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public Response addFeedback(@RequestHeader("token")String token, @RequestBody Feedback feedback){
        Long userid = TokenManager.getInstance().getUser(token).getId();
        feedback.setUserId(userid);
        return new Response(feedbackService.addFeedback(feedback), ResultCode.ERROR_DEFAULT_CODE,"添加反馈失败");
    }
}
