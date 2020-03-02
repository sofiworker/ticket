package com.dm.ticket.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 自定义缓存key生成
 */
public class CacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        Map<String, Object> map = new HashMap<>();
        map.put("target", target.getClass().toGenericString());
        map.put("method", method.getName());
        if (params.length > 0) {
            int i = 0;
            for (Object o : params) {
                map.put("params-" + i, o);
                i++;
            }
        }
        String str = JSONObject.toJSON(map).toString();
        byte[] hash = null;
        String s;
        try {
            hash = MessageDigest.getInstance("MD5").digest(str.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert hash != null;
        s= MD5Encoder.encode(hash);
        return s;
    }
}
