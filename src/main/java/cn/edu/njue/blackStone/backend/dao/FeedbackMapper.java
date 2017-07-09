package cn.edu.njue.blackStone.backend.dao;

import cn.edu.njue.blackStone.backend.model.Feedback;
import org.apache.ibatis.annotations.Param;

/**
 * Created by EdwardZhou on 2017/5/27.
 * All Rights Reserved
 */
public interface FeedbackMapper {
    int addFeedback(@Param("feedback")Feedback feedback);
}
