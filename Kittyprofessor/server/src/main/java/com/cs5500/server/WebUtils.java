package com.cs5500.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mengtao on 2/25/18.
 *
 * response mapping functions
 */
public class WebUtils {

    /**
     * success response to client
     * @param obj the obj to be sent to client
     * @return a 200 response and obj
     */
    public static ResponseEntity successMap(Object obj){
        Map map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", obj);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /**
     * create success response to client
     * @param obj the obj to be sent to client
     * @return a 201 response and obj
     */
    public static ResponseEntity createSuccessMap(Object obj) {
        Map map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", obj);
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    /**
     * success response to client when expected failure happens
     * @param obj the msg
     * @return the 200 response and message
     */
    public static ResponseEntity failedMap(Object obj){
        Map map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("message", obj);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /**
     * an exception when failure happens
     * @param obj the msg
     * @param status the http failure code
     * @return the failure response and err msg
     */
    public static ResponseEntity failedMapWithStatus(Object obj, HttpStatus status) {
        Map map = new HashMap<String, Object>();
        map.put("failed", true);
        map.put("data", obj);
        return new ResponseEntity(map, status);
    }
}
