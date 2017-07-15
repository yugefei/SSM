package com.tencent.seventeenShow.backend.controller;

import com.tencent.seventeenShow.backend.conf.ResultCode;
import com.tencent.seventeenShow.backend.controller.form.SearchForm;
import com.tencent.seventeenShow.backend.controller.form.UserSpeciesForm;
import com.tencent.seventeenShow.backend.mem.TokenManager;
import com.tencent.seventeenShow.backend.model.Category;
import com.tencent.seventeenShow.backend.model.Response;
import com.tencent.seventeenShow.backend.model.Species;
import com.tencent.seventeenShow.backend.model.User;
import com.tencent.seventeenShow.backend.service.SpeciesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Edward on 2017/2/8 008.
 */
@Controller("speciesController")
@RequestMapping("/species")
public class SpeciesController {

    private Logger logger = Logger.getLogger(SpeciesController.class);
    @Autowired
    private SpeciesService speciesService;

    @RequestMapping(value = "/categories",method = RequestMethod.GET)
    @ResponseBody
    public Response<Map<String, List<Category>>> homePageCategories(){
        return new Response<Map<String, List<Category>>>(speciesService.categories());
    }


    @RequestMapping(value = "/collection/{userId}",method = RequestMethod.GET)
    @ResponseBody
    public Response<List<Species>> userCollection(@PathVariable("userId")Long userId){
        return new Response<List<Species>>(speciesService.userCollection(userId));
    }

    @RequestMapping(value = "/observeList/{userId}",method = RequestMethod.GET)
    @ResponseBody
    public Response<List<Species>> userObservation(@PathVariable("userId")Long userId){
        return new Response<List<Species>>(speciesService.userObservation(userId));
    }

    @RequestMapping(value = "/featureList/{type}",method = RequestMethod.GET)
    @ResponseBody
    public Response<Map<String,List<String>>> getSpeciesTypeFeature(@PathVariable("type")String type){
        if(!type.equals("amphibia") && !type.equals("bird") && !type.equals("insect") && !type.equals("reptiles"))
            return new Response<Map<String, List<String>>>(ResultCode.ERROR_PARAMETER_WRONG,"物种类型错误");

        return new Response<Map<String, List<String>>>(speciesService.getFeatures(type));
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Response<Map<String,List<Species>>> getSpeciesList(){
        return new Response<Map<String, List<Species>>>(speciesService.allSpecies());
    }

    @RequestMapping(value = "/query/{speciesType}", method = RequestMethod.POST)
    @ResponseBody
    public Response<List<Species>> featureQuery(@PathVariable("speciesType")String type, @RequestBody Map<String,List<String>> features){
        if(!type.equals("amphibia") && !type.equals("bird") && !type.equals("insect") && !type.equals("reptiles"))
            return new Response<List<Species>>(ResultCode.ERROR_PARAMETER_WRONG,"物种类型错误");
        return new Response<List<Species>>(speciesService.featureQuery(features, type));
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Response<List<Species>> searchSpecies(@RequestBody SearchForm searchForm){
        return new Response<List<Species>>(speciesService.searchSpeciesForName(searchForm.getKey()));
    }


}
