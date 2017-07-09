package cn.edu.njue.blackStone.backend.service;

import cn.edu.njue.blackStone.backend.model.User;
import cn.edu.njue.blackStone.backend.utils.exception.MobileOccupiedException;
import cn.edu.njue.blackStone.backend.utils.exception.StudentIdOccupiedException;
import cn.edu.njue.blackStone.backend.utils.exception.StudentNameException;
import cn.edu.njue.blackStone.backend.utils.exception.UserException;
import org.springframework.stereotype.Service;

/**
 * Created by Edward on 2017/2/7 007.
 */

public interface UserService {
    User login(String username,String pwd);

    boolean register(User user,String pwd)  throws StudentIdOccupiedException,MobileOccupiedException,StudentNameException;

    boolean userNameTaken(String userName);

    boolean studentRegistered(String studentId);

    boolean resetPwd(String userName, String pwd);

    boolean setAvatar(String avatarUrl, Long userId);

    boolean setPwd(String originPwd, String newPwd, Long userId);

    boolean changeMobile(String mobile, Long userId);
}
