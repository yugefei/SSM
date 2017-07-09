package cn.edu.njue.blackStone.backend.controller;

import cn.edu.njue.blackStone.backend.conf.ResultCode;
import cn.edu.njue.blackStone.backend.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Edward on 2016/8/10.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseGlobalExceptionHandler {

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseStatus(HttpStatus.OK)
    public void handleSqlError(HttpServletRequest req, HttpServletResponse rsp, Exception e) throws Exception {
        logger.error(e.getMessage());
        Response dto = new Response();
        dto.setCode(22);
        dto.setMessage("数据库出现错误");
        this.respondData(rsp,dto);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public void handleOtherError(HttpServletRequest req, HttpServletResponse rsp, Exception e) throws Exception {
        e.printStackTrace();
        Response dto = new Response();
        dto.setCode(ResultCode.ERROR_DEFAULT_CODE);
        dto.setMessage("服务器错误");
        this.respondData(rsp,dto);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    public void handleMethodNotSupportedError(HttpServletRequest req, HttpServletResponse rsp, Exception e) throws Exception {
        logger.error(e.getStackTrace());
        Response dto = new Response();
        dto.setCode(ResultCode.ERROR_HTTP_METHOD_NOT_SUPPORTTED);
        dto.setMessage("当前接口不支持此http method");
        this.respondData(rsp,dto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    public void handleBodyNotReadableError(HttpServletRequest req, HttpServletResponse rsp, Exception e) throws Exception {
        e.printStackTrace();
        logger.error(e.getStackTrace());
        Response dto = new Response();
        dto.setCode(ResultCode.ERROR_PARAMETER_WRONG);
        dto.setMessage("参数错误");
        this.respondData(rsp,dto);
    }
}
