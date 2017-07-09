package cn.edu.njue.blackStone.backend.controller.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Edward on 2017/2/9 009.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSpeciesForm {

    private Long speciesId;
    private Long userId;

    public UserSpeciesForm(){}

    public Long getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Long speciesId) {
        this.speciesId = speciesId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
