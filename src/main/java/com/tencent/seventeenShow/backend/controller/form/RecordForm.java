package com.tencent.seventeenShow.backend.controller.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

/**
 * Created by Edward on 2017/4/29 029.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordForm {
    private Long id;
    private Long userId;
    private Boolean addToObservedList;
    private Date time;
    private Double lat;
    private Double lon;
    private String observationPalName;
    private List<NoteForm> notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getAddToObservedList() {
        return addToObservedList;
    }

    public void setAddToObservedList(Boolean addToObservedList) {
        this.addToObservedList = addToObservedList;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getObservationPalName() {
        return observationPalName;
    }

    public void setObservationPalName(String observationPalName) {
        this.observationPalName = observationPalName;
    }

    public List<NoteForm> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteForm> notes) {
        this.notes = notes;
    }
}
