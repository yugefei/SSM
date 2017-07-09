package cn.edu.njue.blackStone.backend.controller.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Edward on 2017/2/8 008.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendSmsForm {
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
