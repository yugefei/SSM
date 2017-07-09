package cn.edu.njue.blackStone.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;

/**
 * Created by Edward on 2017/2/14 014.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Record {
    private Long id;
    private Long userId;
    private String userNickname;
    private Date time;
    private Double lat;
    private Double lon;
    private String observationPalName;
    private List<NoteCounter> noteCounts;
    private List<Note> notes;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<NoteCounter> getNoteCounts() {
        return noteCounts;
    }

    public void setNoteCounts(List<NoteCounter> noteCounts) {
        this.noteCounts = noteCounts;
    }

    public Record() {
    }
}
