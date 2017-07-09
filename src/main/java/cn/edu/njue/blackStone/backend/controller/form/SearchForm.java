package cn.edu.njue.blackStone.backend.controller.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by EdCho on 2017/5/29 029.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchForm {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
