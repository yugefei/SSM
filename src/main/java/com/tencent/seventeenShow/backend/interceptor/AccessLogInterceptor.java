package com.tencent.seventeenShow.backend.interceptor;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Edward
 * 2016/8/13.
 */
public class AccessLogInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = Logger.getLogger(AccessLogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json");
        logger.info("Request Uri:" + request.getRequestURI());
        return true;
    }
}
