package com.tencent.seventeenShow.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by EdCho on 2017/5/29 029.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Category {
    private Long id;
    private String name;
    private String img;
    private String speciesType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSpeciesType() {
        return speciesType;
    }

    public void setSpeciesType(String speciesType) {
        this.speciesType = speciesType;
    }
}
