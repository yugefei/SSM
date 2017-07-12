package com.tencent.seventeenShow.backend.controller;

import com.tencent.seventeenShow.backend.model.Response;
import com.tencent.seventeenShow.backend.utils.JsonDateValueProcessor;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Edward on 2016/8/6.
 */
public class BaseController {
    private static Logger logger = Logger.getLogger(BaseController.class);
    private static void writeToClient(HttpServletResponse response , String result) {

        try {
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.getWriter().write(result);
        }catch (IOException e){
            logger.error("---write to Client error");
            e.printStackTrace();
        }
    }
    public static void writeToClient(HttpServletResponse response ,JSONObject result) {

        if (null != result) {
            writeToClient(response,result.toString());
        }
    }

    public static void writeToClient(HttpServletResponse response ,Response result) {

        if (null != result) {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            writeToClient(response,JSONObject.fromObject(result,jsonConfig).toString());
        }
    }

    public static void writeToClient(HttpServletResponse response, Object result){
        if (null != result) {
            Response dto = new Response();
            dto.setData(result);
            writeToClient(response,dto);
        }
    }
    public static void writeToClient(HttpServletResponse response,boolean successed,Long ifErrorCode,String errorMessage){
        if(successed)
            writeToClient(response,new Response());
        else
            writeToClient(response,new Response(ifErrorCode,errorMessage));
    }
}