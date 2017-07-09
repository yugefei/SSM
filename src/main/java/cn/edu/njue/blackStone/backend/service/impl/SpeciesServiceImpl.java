package cn.edu.njue.blackStone.backend.service.impl;

import cn.edu.njue.blackStone.backend.dao.SpeciesMapper;
import cn.edu.njue.blackStone.backend.mem.MemCache;
import cn.edu.njue.blackStone.backend.model.Category;
import cn.edu.njue.blackStone.backend.model.Species;
import cn.edu.njue.blackStone.backend.service.SpeciesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Edward on 2017/2/8 008.
 */
@Service("SpeciesService")
public class SpeciesServiceImpl implements SpeciesService {

    private Logger logger = Logger.getLogger(SpeciesServiceImpl.class);

    @Autowired
    private SpeciesMapper speciesMapper;
    @Override
    public     Species getSpeciesInfo(Long speciesId, Long userId){
        Species species = speciesMapper.getSpeciesInfo(speciesId, userId);
        species.getFeatures().remove("species_id");
        species.getFeatures().remove("id");

        if(species.getFeatures().containsKey("tweet_image")){
            String img = this.combine(species.getFeatures().get("tweet_image"));
            String wav = this.combine(species.getFeatures().get("tweet_sound"));

            species.getFeatures().put("tweet_image",img);
            species.getFeatures().put("tweet_sound",wav);
        }

        return species;
    }

    @Override
    public boolean addSpeciesToCollection(Long speciesId, Long userId) {
        return speciesMapper.addSpeciesToFavorite(speciesId,userId) > 0;
    }

    @Override
    public boolean addSpeciesToObservationList(Long speciesId, Long userId) {
        return speciesMapper.addSpeciesToObservationList(speciesId,userId) > 0;
    }

    @Override
    public List<Species> userCollection(Long userId) {
        return speciesMapper.userCollection(userId);
    }

    @Override
    public List<Species> userObservation(Long userId) {
        return speciesMapper.userObservation(userId);
    }

    @Override
    public Map<String, List<String>> getFeatures(String type) {
        Object obj = MemCache.getInstance().get(type);
        if(obj != null)
            return (Map<String, List<String>>)obj;


        String[] keys = this.getFeatureColumn(type);

        Map<String,List<String>> features = new HashMap<String, List<String>>();
        for(String key : keys){
            if(key.equals("id"))
                continue;

            features.put(key,speciesMapper.features(type,key));
        }
        MemCache.getInstance().set(type,features);
        return features;

    }

    @Override
    public Map<String, List<Species>> allSpecies() {
        Object obj = MemCache.getInstance().get("birdList");
        if(obj != null)
            return (Map<String, List<Species>>)obj;

        Map<String, List<Species>> result = new HashMap<String, List<Species>>();
        List<Species> spe = speciesMapper.allSpecies();
        for(Species species : spe){
            if(!result.containsKey(species.getSpeciesType())){
                result.put(species.getSpeciesType(),new ArrayList<Species>());
            }
            result.get(species.getSpeciesType()).add(species);
        }
        MemCache.getInstance().set("birdList",result);
        return result;
    }

    @Override
    public List<Species> featureQuery(Map<String, List<String>> features, String type) {
        return speciesMapper.featureQuery(type, features);
    }

    @Override
    public boolean removeCollection(Long speciesId, Long userId) {
        return speciesMapper.removeCollection(speciesId, userId) != 0;
    }

    private String[] getFeatureColumn(String type){
        Map<String, String[]> types;
        types = new HashMap<String, String[]>();
        String[] birdF = {"shape","tone","tail_shape","habitat"};
        types.put("bird",birdF);

        String[] amphibiaF = {"fil_major_color","fil_vocal_sac","fil_web","fil_biotope"};
        types.put("amphibia",amphibiaF);

        String[] insectF = {"fil_mouthparts","fil_wing","fil_leg","fil_tentacle","fil_life_cycle"};
        types.put("insect",insectF);

        String[] reptilesF = {"fil_shape","fil_major_color","fil_has_bigscale","fil_biotope","fil_micro_biotope"};
        types.put("reptiles",reptilesF);

        return types.get(type);
    }

    private String combine(String str){
        String[] strs = str.split(";");
        if(strs.length == 0)
            return "";

        StringBuilder builder = new StringBuilder("http://img.blackstone.ebirdnote.cn/");
        builder.append(strs[0]);
        for(int i = 1; i < strs.length; ++i){
            builder.append(";http://img.blackstone.ebirdnote.cn/");
            builder.append(strs[i]);
        }

        return builder.toString();
    }

    @Override
    public List<Species> searchSpeciesForName(String key) {
        if(key.length() == 0)
            return new ArrayList<Species>();

        return speciesMapper.search(key);
    }

    @Override
    public Map<String, List<Category>> categories(){
        Map<String, List<Category>> result = new HashMap<String, List<Category>>();
        List<Category> categories = new ArrayList<Category>(3);
        Category category = new Category();
        category.setName("两栖类");
        category.setSpeciesType("amphibia");
        category.setImg("http://img.blackstone.ebirdnote.cn/Foa36KgDoamKC2tHTkebyHiqQ4a5");
        categories.add(category);

        category = new Category();
        category.setName("爬行类");
        category.setSpeciesType("reptiles");
        category.setImg("http://img.blackstone.ebirdnote.cn/FqcWn35rpUxlgZQE9LRXmUP-BYLa");
        categories.add(category);

        category = new Category();
        category.setName("鸟类");
        category.setSpeciesType("bird");
        category.setImg("http://img.blackstone.ebirdnote.cn/FrB8yFe1jrZ29DFMxu-4t78rxbiA");
        categories.add(category);

        result.put("脊椎动物",categories);

        List<Category> categoriesInsects = new ArrayList<Category>(16);
        List<Species> insects = speciesMapper.insects();
        for(Species s : insects){
            Category cate = new Category();
            cate.setName(s.getChineseName());
            cate.setSpeciesType("insect");
            cate.setImg(s.getMainPhoto());
            categoriesInsects.add(cate);
        }

        result.put("无脊椎动物", categoriesInsects);
        return result;
    }

}
