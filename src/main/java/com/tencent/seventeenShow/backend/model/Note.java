package com.tencent.seventeenShow.backend.model;

/**
 * Created by Edward on 2017/4/29 029.
 */
public class Note {
    private Long id;
    private Species species;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
