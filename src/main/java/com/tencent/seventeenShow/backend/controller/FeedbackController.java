package com.tencent.seventeenShow.backend.controller;

import com.tencent.seventeenShow.backend.conf.ResultCode;
import com.tencent.seventeenShow.backend.mem.TokenManager;
import com.tencent.seventeenShow.backend.model.Feedback;
import com.tencent.seventeenShow.backend.model.Response;
import com.tencent.seventeenShow.backend.service.FeedbackService;
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
