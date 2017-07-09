package cn.edu.njue.blackStone.backend.utils;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Edward on 2016/8/11.
 */
public class JsonDateValueProcessor implements JsonValueProcessor {
    private String format ="yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public JsonDateValueProcessor() {
        super();
    }

    public JsonDateValueProcessor(String format) {
        super();
        this.format = format;
    }

    @Override
    public Object processArrayValue(Object paramObject,
                                    JsonConfig paramJsonConfig) {
        return process(paramObject);
    }

    @Override
    public Object processObjectValue(String paramString, Object paramObject,
                                     JsonConfig paramJsonConfig) {
        return process(paramObject);
    }


    private Object process(Object value){
        if(value instanceof Date){
            return ((Date) value).getTime();
//            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
//            return sdf.format(value);
        }
        return value == null ? "" : value.toString();
    }

}