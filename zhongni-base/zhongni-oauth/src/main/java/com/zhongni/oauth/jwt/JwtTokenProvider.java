package com.zhongni.oauth.jwt;

import com.alibaba.fastjson.JSON;
import com.zhongni.oauth.enums.BusinessExceptionEnum;
import com.zhongni.oauth.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.expiration}")
    private Long expirationTime;

    @Value("${rsa.jwt.public.key}")
    private String publicKey;

    @Value("${rsa.jwt.private.key}")
    private String privateKey;

    // 生成令牌时使用 RSA 私钥进行签名
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Map<String, Object> claims = new HashMap<>();
            try {
                return Jwts.builder()
                        .setClaims(claims)
                        .setSubject(JSON.toJSONString(userDetails))
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                        .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                        .compact();
            } catch (Exception e) {
                log.error("generateToken is Exception : {}", e.getMessage(), e);
                throw new BusinessException(BusinessExceptionEnum.UNKNOWN_EXCEPTION);
        }
    }

    // 解析令牌时使用 RSA 公钥进行验证
    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getPublicKey())
                    .build()
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("parseToken is Exception : {}", e.getMessage(), e);
            throw new BusinessException(BusinessExceptionEnum.CHECK_SIGN_FAIL);
        }
    }

    public PrivateKey getPrivateKey() throws Exception {
        // 进行 Base64 解码
        byte[] decodedBytes = Base64.getDecoder().decode(privateKey);
        // 使用 PKCS8EncodedKeySpec 进行密钥规范化
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public PublicKey getPublicKey() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec spec =  new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }
}