package com.tencent.seventeenShow.backend.dao;

import com.tencent.seventeenShow.backend.model.Feedback;
import org.apache.ibatis.annotations.Param;

/**
 * Created by EdwardZhou on 2017/5/27.
 * All Rights Reserved
 */
public interface FeedbackMapper {
    int addFeedback(@Param("feedback")Feedback feedback);
}
