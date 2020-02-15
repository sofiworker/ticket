package com.dm.ticket.util;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.crypto.asymmetric.RSA;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Properties;

/**
 * @description jwt工具类包含jwt的创建、验证
 */
public class JwtUtil {
    //token过期时间，使用60分钟
    private final static long TIME = 60 * 60 * 1000;

    public static String createToken() throws JWTCreationException {
        RSA rsa = getRsa();
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) rsa.getPublicKey(), (RSAPrivateKey) rsa.getPrivateKey());
        return JWT.create()
                .withIssuer("ticket")
                .withExpiresAt(new Date(System.currentTimeMillis() + TIME))
                .sign(algorithm);
    }

    public static void verifyToken(String token) throws JWTVerificationException {
        RSA rsa = getRsa();
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) rsa.getPublicKey(), (RSAPrivateKey) rsa.getPrivateKey());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("ticket")
                .build();
        verifier.verify(token);
    }

    private static RSA getRsa() {
        ClassPathResource resource = new ClassPathResource("key.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource.getStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String privateKeyBase64 = properties.getProperty("privateKeyBase64");
        String publicKeyBase64 = properties.getProperty("publicKeyBase64");
        return new RSA(privateKeyBase64, publicKeyBase64);
    }
}
