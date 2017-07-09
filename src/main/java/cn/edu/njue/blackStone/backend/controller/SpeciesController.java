package cn.edu.njue.blackStone.backend.controller;

import cn.edu.njue.blackStone.backend.conf.ResultCode;
import cn.edu.njue.blackStone.backend.controller.form.SearchForm;
import cn.edu.njue.blackStone.backend.controller.form.UserSpeciesForm;
import cn.edu.njue.blackStone.backend.mem.TokenManager;
import cn.edu.njue.blackStone.backend.model.Category;
import cn.edu.njue.blackStone.backend.model.Response;
import cn.edu.njue.blackStone.backend.model.Species;
import cn.edu.njue.blackStone.backend.model.User;
import cn.edu.njue.blackStone.backend.service.SpeciesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    @RequestMapping(value = "/{speciesId}",method = RequestMethod.GET)
    @ResponseBody
    public Response<Species> getSpeciesInfo(@PathVariable("speciesId")Long speciesId, HttpServletRequest request){
        String token;
        Long userId = 0L;

        token = request.getHeader("token");
        if(token != null){
            User user = TokenManager.getInstance().getUser(token);
            if(user != null){
                userId = user.getId();
            }
        }

        Species species = speciesService.getSpeciesInfo(speciesId, userId);
        species.getFeatures().remove("id"); //jackson转换的时候会报id错误... 去掉. [脏代码]
        return new Response<Species>(species);
    }

    @RequestMapping(value = "/addToCollection",method = RequestMethod.POST)
    @ResponseBody
    public Response addToCollection(HttpServletRequest request,@RequestBody UserSpeciesForm form,@RequestHeader("token")String token){
        Long userId = TokenManager.getInstance().getUser(token).getId();
        return new Response(speciesService.addSpeciesToCollection(form.getSpeciesId(),userId), ResultCode.ERROR_OPERATION_FAILED,"添加收藏失败");
    }
    @ResponseBody
    @RequestMapping(value = "/collection/{speciesId}", method = RequestMethod.DELETE)
    public Response removeCollection (@RequestHeader("token")String token, @PathVariable("speciesId")Long speciesId){
        Long userId = TokenManager.getInstance().getUser(token).getId();
        return new Response(speciesService.removeCollection(speciesId, userId), ResultCode.ERROR_DEFAULT_CODE, "取消收藏失败");
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
