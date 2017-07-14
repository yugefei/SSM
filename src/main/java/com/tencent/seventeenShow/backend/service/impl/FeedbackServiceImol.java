package com.tencent.seventeenShow.backend.service.impl;

import com.tencent.seventeenShow.backend.dao.FeedbackMapper;
import com.tencent.seventeenShow.backend.model.Feedback;
import com.tencent.seventeenShow.backend.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by EdwardZhou on 2017/5/27.
 * All Rights Reserved
 */
@Service("feedbackService")
public class FeedbackServiceImol implements FeedbackService{

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public boolean addFeedback(Feedback feedback) {
        return feedbackMapper.addFeedback(feedback) == 1;
    }
}
