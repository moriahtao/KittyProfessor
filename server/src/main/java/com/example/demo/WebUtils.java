package com.example.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leizhang on 2/25/18.
 */
public class WebUtils {

    public static Map successMap(Object obj){
        Map map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", obj);
        return map;
    }
}
