package com.tencent.seventeenShow.backend.controller.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Edward on 2017/4/29 029.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteForm {
    private Long speciesId;
    private Long recordId;
    private String remark;

    public Long getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Long speciesId) {
        this.speciesId = speciesId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
