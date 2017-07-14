package com.tencent.seventeenShow.backend.controller;

import com.tencent.seventeenShow.backend.conf.ResultCode;
import com.tencent.seventeenShow.backend.controller.form.RecordForm;
import com.tencent.seventeenShow.backend.mem.TokenManager;
import com.tencent.seventeenShow.backend.model.Record;
import com.tencent.seventeenShow.backend.model.Response;
import com.tencent.seventeenShow.backend.service.RecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Edward on 2017/2/15 015.
 */
@RequestMapping("/record")
@Controller("recordController")
public class RecordController {
    protected Logger logger = Logger.getLogger(RecordController.class);

    @Autowired
    private RecordService recordService;

    @ResponseBody
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Response addRecord(@RequestBody RecordForm record, HttpServletRequest request, @RequestHeader("token")String token){
        if(record.getNotes().size() == 0){
            return new Response(ResultCode.ERROR_PARAMETER_WRONG, "记录无物种");
        }
        Long userId = TokenManager.getInstance().getUser(token).getId();
        record.setUserId(userId);
        recordService.addRecord(record);
        return new Response();
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Response editRecord(@RequestBody RecordForm record, HttpServletRequest request, @RequestHeader("token")String token){
        if(record.getNotes().size() == 0){
            return new Response(ResultCode.ERROR_PARAMETER_WRONG, "记录无物种");
        }
        Long userId = TokenManager.getInstance().getUser(token).getId();
        record.setUserId(userId);
        recordService.editRecord(record);
        return new Response();
    }

//    @ResponseBody
//    @RequestMapping(value = "/species/{speciesId}", method = RequestMethod.GET)
//    public Response<List<Record>> speciesRecord(HttpServletRequest request, @PathVariable("speciesId")Long speciesId){
//        Long count = 20l;
//        if(request.getParameterMap().keySet().contains("count"))
//            count = Long.parseLong(request.getParameter("count"));
//
//        Long startId = 0l;
//        if(request.getParameterMap().keySet().contains("lastId"))
//            startId = Long.parseLong(request.getParameter("lastId"));
//
//        return new Response<List<Record>>(recordService.speciesRecordList(speciesId,count,startId));
//    }

    @ResponseBody
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public Response<List<Record>> userRecord(HttpServletRequest request, @PathVariable("userId")Long speciesId){
        Long count = 20l;
        if(request.getParameterMap().keySet().contains("count"))
            count = Long.parseLong(request.getParameter("count"));

        Long startId = 0l;
        if(request.getParameterMap().keySet().contains("lastId"))
            startId = Long.parseLong(request.getParameter("lastId"));

        return new Response<List<Record>>(recordService.userRecordList(speciesId,count,startId));
    }

    @ResponseBody
    @RequestMapping(value = "/{recordId}",method = RequestMethod.GET)
    public Response<Record> recordInformation(@PathVariable("recordId")Long recordId){
        Record record = recordService.recordById(recordId);
        if(record == null){
            return new Response<Record>(ResultCode.ERROR_OPERATION_FAILED,"不存在记录");
        }

        return new Response<Record>(record);
    }

    @ResponseBody
    @RequestMapping(value = "/{recordId}", method = RequestMethod.DELETE)
    public Response deleteRecord(@PathVariable("recordId")Long recordId,@RequestHeader("token")String token){
        Long userId = TokenManager.getInstance().getUser(token).getId();
        return new Response(recordService.deleteRecord(recordId,userId), ResultCode.ERROR_DEFAULT_CODE,"无此记录");
    }

}
