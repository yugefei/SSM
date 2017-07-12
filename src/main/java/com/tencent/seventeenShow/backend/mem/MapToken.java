package com.tencent.seventeenShow.backend.mem;

import com.tencent.seventeenShow.backend.mem.Token;

import java.util.Date;
import java.util.Map;

/**
 * Created by gefeiyu on 2017/7/12.
 */
public class MapToken {
    private Map<String,Token>map;
    public Map<String, Token> getMap() {
        return map;
    }

    public void setMap(Map<String, Token> map) {
        this.map = map;
    }
    public void put(String tokenAndId,Token token)
    {
        map.put(tokenAndId,token);
    }

    public boolean check(String tokenAndId)
    {

        if(!this.map.containsKey(tokenAndId))
            return false;
        if(this.map.get(tokenAndId).getExipreTime().compareTo(new Date()) == -1 ) { //expire time 比现在时间小 说明过期
            this.map.remove(tokenAndId);
            return false;
        }
        return true;
    }
}
