package cn.edu.njue.blackStone.backend.service.impl;

import cn.edu.njue.blackStone.backend.dao.UserMapper;
import cn.edu.njue.blackStone.backend.model.User;
import cn.edu.njue.blackStone.backend.service.UserService;
import cn.edu.njue.blackStone.backend.utils.exception.MobileOccupiedException;
import cn.edu.njue.blackStone.backend.utils.exception.StudentIdOccupiedException;
import cn.edu.njue.blackStone.backend.utils.exception.StudentNameException;
import cn.edu.njue.blackStone.backend.utils.exception.UserException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Edward on 2017/2/7 007.
 */
@Service("UserService")
public class UserServiceImpl  implements UserService{

    private Logger logger = Logger.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username,String pwd){
        return userMapper.loginByNameAndPwd(username,pwd);
    }

    @Override
    public boolean register(User user,String pwd) throws StudentIdOccupiedException,MobileOccupiedException,StudentNameException{
        if(userMapper.studentIdRegistered(user.getStudentId()) >= 1){
            throw new StudentIdOccupiedException();
        }
        if(userMapper.mobileOccupied(user.getMobile()) >= 1){
            throw new MobileOccupiedException();
        }

        if(userMapper.checkIdAndName(user.getStudentId(),user.getName()) != 1){
            throw new StudentNameException();
        }
        return userMapper.register(user,pwd) == 1;
    }

    @Override
    public boolean userNameTaken(String userName) {
        return userMapper.mobileOccupied(userName) >= 1;
    }

    @Override
    public boolean resetPwd(String userId, String pwd) {
        return userMapper.setPwd(userId,pwd) >= 1;
    }

    @Override
    public boolean studentRegistered(String studentId) {
        return userMapper.studentIdRegistered(studentId) >= 1;
    }

    @Override
    public boolean setAvatar(String avatarUrl, Long userId) {
        return userMapper.setAvatar(avatarUrl, userId) == 1;
    }

    @Override
    public boolean setPwd(String originPwd, String newPwd, Long userId) {
        return userMapper.setPwdWithOrigin(originPwd, newPwd, userId) == 1;
    }

    @Override
    public boolean changeMobile(String mobile, Long userId) {
        return userMapper.rebundingMobile(mobile, userId) == 1;
    }
}
