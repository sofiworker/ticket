package com.dm.ticket.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

public class EncryptUtil {

    public static String encryptPassword(String password) {
        ClassPathResource resource = new ClassPathResource("key.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource.getStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        RSA rsa = new RSA(properties.getProperty("privateKeyBase64"), properties.getProperty("publicKeyBase64"));
        byte[] encrypt = rsa.encrypt(StrUtil.bytes(password, CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        return Base64.encode(encrypt);
    }
}
