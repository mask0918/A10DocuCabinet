package com.bst.pidms.utils;

import com.alibaba.fastjson.JSONObject;
import com.bst.pidms.entity.OwnFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: BST
 * @Date: 2019/4/8 20:05
 */
public class MapUtils {

    public static String loadJson(String url) throws Exception {
        //读取url,返回json串
        StringBuilder json = new StringBuilder();
        URL oracle = new URL(url);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine = null;
        while ((inputLine = in.readLine()) != null) {
            json.append(inputLine);
        }
        in.close();

        return json.toString();
    }

    public static String getAddress(String longitude, String latitude) throws Exception {
        String api = "https://restapi.amap.com/v3/geocode/regeo?output=json&location=" + DDtoDMS(longitude) + "," + DDtoDMS(latitude) + "&key=61a068880cfc0680764fc804a81a61ef&radius=1000";
        String result = loadJson(api);
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject regeocode = jsonObject.getJSONObject("regeocode");
        return regeocode.get("formatted_address").toString();
    }

    public static Double DDtoDMS(String dd) {
        String[] split = dd.split("°");
        String[] split1 = split[1].trim().split("'");
        String[] split2 = split1[1].trim().split("\"");
        return Double.valueOf(split[0]) + Double.valueOf(split1[0]) / 60 + Double.valueOf(split2[0]) / 3600;
    }

    public static Map<String, List<OwnFile>> sortMapByKey(Map<String, List<OwnFile>> oriMap, final boolean isRise) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
//        Map<String, List<OwnFile>> sortMap = new TreeMap<>(new Comparator<String>() {
////            @Override
////            public int compare(String o1, String o2) {
////                if (isRise) {
////                    // 升序排序
////                    return o1.compareTo(o2);
////                } else {
////                    // 降序排序
////                    return o2.compareTo(o1);
////                }
////            }
////        });
        Map<String, List<OwnFile>> sortMap = new TreeMap<>((String o1, String o2) -> (o2.compareTo(o1)));
        sortMap.putAll(oriMap);
        return sortMap;
    }

}
