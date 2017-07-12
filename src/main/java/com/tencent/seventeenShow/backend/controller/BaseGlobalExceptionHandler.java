package com.tencent.seventeenShow.backend.controller;

/**
 * Created by Edward on 2016/8/10.
 */

import com.tencent.seventeenShow.backend.model.Response;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;

public class BaseGlobalExceptionHandler {
    protected Logger logger = Logger.getLogger(BaseGlobalExceptionHandler.class);
    protected static final String DEFAULT_ERROR_MESSAGE = "系统忙，请稍后再试";

    protected void respondData(HttpServletResponse response, Response dto) {
        response.setContentType("application/json");
        BaseController.writeToClient(response, dto);
    }
}
