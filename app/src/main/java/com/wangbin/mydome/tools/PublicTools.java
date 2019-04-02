package com.wangbin.mydome.tools;


import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * @ClassName PublicTools
 * @Description 工具类获取基础URL以及获取UUID
 * @Author WangBin
 * @Date 2019/3/20 17:59
 */
public class PublicTools {

    /**
     * 从configs.properties获取基础的URL
     *
     * @param context context
     * @return Map<String,String>
     */
    public static Map<String, String> readProperties(Context context) {
        Map<String, String> paramsMap = new HashMap<>();
        Properties dbProps = new Properties();
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is2 = am.open("configs.properties");
            dbProps.load(is2);
            try {
                is2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String WEBURL = dbProps.getProperty("WEBURL");
        String NAMESPACE = dbProps.getProperty("NAMESPACE");
        paramsMap.put("WEBURL", WEBURL);
        paramsMap.put("NAMESPACE", NAMESPACE);
        return paramsMap;

    }

    /**
     * 获取UUID
     *
     * @return String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
