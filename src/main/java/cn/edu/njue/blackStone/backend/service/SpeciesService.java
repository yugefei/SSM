package cn.edu.njue.blackStone.backend.service;

import cn.edu.njue.blackStone.backend.model.Category;
import cn.edu.njue.blackStone.backend.model.Species;

import java.util.List;
import java.util.Map;

/**
 * Created by Edward on 2017/2/8 008.
 */
public interface SpeciesService {
    Species getSpeciesInfo(Long speciesId, Long userId);

    boolean addSpeciesToCollection(Long speciesId,Long userId);

    boolean addSpeciesToObservationList(Long speciesId,Long userId);

    List<Species> userCollection(Long userId);

    List<Species> userObservation(Long userId);

    Map<String,List<String>> getFeatures(String type);

    Map<String,List<Species>> allSpecies();

    List<Species> featureQuery(Map<String,List<String>> features, String type);

    boolean removeCollection(Long speciesId, Long userId);

    List<Species> searchSpeciesForName(String key);

    Map<String, List<Category>> categories();

}
