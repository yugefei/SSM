package com.tencent.seventeenShow.backend.dao;

import com.tencent.seventeenShow.backend.model.Species;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Edward on 2017/2/8 008.
 */
public interface SpeciesMapper {
    int addSpeciesToFavorite(@Param("speciesId") Long speciesId, @Param("userId") Long userId);

    int addSpeciesToObservationList(@Param("speciesId") Long speciesId, @Param("userId") Long userId);

    Species getSpeciesInfo(@Param("speciesId")Long id,@Param("userId")Long userId);

    Long selectLong(@Param("param")Long l);

    List<Species> userCollection(@Param("userId")Long userId);

    List<Species> userObservation(@Param("userId")Long userId);

    List<String> featureTypes(@Param("type")String type);

    List<String> features(@Param("type")String type,@Param("feature")String feature);

    List<Species> allSpecies();

    List<Species> featureQuery(@Param("type")String type, @Param("features")Map<String, List<String>> features);

    Species speciesBaseInfo(@Param("species_id")Long id);

    int removeCollection(@Param("speciesId")Long speciesId, @Param("userId")Long userId);

    List<Species> search(@Param("key")String key);

    List<Species> insects();


}
