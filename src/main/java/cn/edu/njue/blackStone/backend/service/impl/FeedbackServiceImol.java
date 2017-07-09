package cn.edu.njue.blackStone.backend.service.impl;

import cn.edu.njue.blackStone.backend.dao.FeedbackMapper;
import cn.edu.njue.blackStone.backend.model.Feedback;
import cn.edu.njue.blackStone.backend.service.FeedbackService;
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
