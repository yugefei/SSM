package cn.edu.njue.blackStone.backend.interceptor;


import cn.edu.njue.blackStone.backend.conf.ResultCode;
import cn.edu.njue.blackStone.backend.controller.BaseController;
import cn.edu.njue.blackStone.backend.mem.TokenManager;
import cn.edu.njue.blackStone.backend.model.Response;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Edward on 2016/8/10.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = Logger.getLogger(AuthInterceptor.class);
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
//        logger.info("In Interceptor");
        if(token == null || !TokenManager.getInstance().check(token)){
            Response dto = new Response();
            dto.setCode(ResultCode.ERROR_TOKEN_INVALID_CODE);
            dto.setMessage("token 不合法");
            BaseController.writeToClient(response,dto);
            return false;
        }
        return true;
    }
}
