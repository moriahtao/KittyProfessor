package com.login.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mengtao on 2/25/18.
 */
public class WebUtils {

    public static Map successMap(Object obj){
        Map map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", obj);
        return map;
    }
    public static Map failedMap(String message){
        Map map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("message", message);
        return map;
    }
}
