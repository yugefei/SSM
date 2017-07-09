package cn.edu.njue.blackStone.backend.dao;
import cn.edu.njue.blackStone.backend.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


/**
 * Created by Edward on 2016/9/18.
 */
public interface UserMapper {
//    User login(@Param("openId") String openId);
    User loginByNameAndPwd(@Param("loginName") String loginName, @Param("password") String password);
    int register(@Param("nu") User user,@Param("pwd")String pwd);
    int studentIdRegistered(@Param("username")String studentId);
    int mobileOccupied(@Param("username")String mobile);
    int setPwd(@Param("id")String mobile,@Param("pwd")String pwd);

    int checkIdAndName(@Param("studentId")String studentId,@Param("name")String name);
    int setAvatar(@Param("avatarUrl")String avatarUrl, @Param("userId")Long userId);
    int setPwdWithOrigin(@Param("oriPwd")String originPwd, @Param("newPwd")String newPwd, @Param("userId")Long userId);

    int rebundingMobile(@Param("mobile")String mobile, @Param("userId")Long userId);
//    Long bindOpenId(@Param("openId") String openId, @Param("userId") Long userId);
//    Long unbunding(@Param("userId") Long userId);
//    Long unbundingByOpenId(@Param("open_id") String openId);
}
