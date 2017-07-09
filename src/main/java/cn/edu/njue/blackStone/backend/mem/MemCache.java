package cn.edu.njue.blackStone.backend.mem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Edward on 2017/3/23 023.
 */
public class MemCache {
    private static MemCache ourInstance = new MemCache();

    private Map<String,Object> map;

    public static MemCache getInstance() {
        return ourInstance;
    }

    private MemCache() {
        map = new HashMap<String,Object>();
    }

    public Object get(String key){
        if(map.containsKey(key))
            return map.get(key);

        return null;
    }

    public void set(String key,Object value){
        map.put(key,value);
    }
}
