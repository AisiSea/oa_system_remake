package com.aisisea.oa.commons.utils.token;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;

import java.io.IOException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 提供Token的生成方法以及解析方法
 * @author AisiSea
 */
public class TokenUtils {

    private ECPublicKey publicKey;
    private ECPrivateKey privateKey;

    public TokenUtils() {
    }

    public TokenUtils(ECPublicKey publicKey, ECPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public ECPublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(ECPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public ECPrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(ECPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * 根据传入的 Map 和 有效时间 生成 Token
     * @param map
     * @param effective
     * @return
     */
    public String createToken(Map<String, Object> map, long effective) {
        //根据传入的有效时间生成 签发时间 和 过期时间
        Long iat = new Date().getTime();
        Long exp = iat + effective;
        //将上述时间放入map中
        Map<String, Object> payloadMap = new HashMap<>(map);
        payloadMap.put("iat", iat);
        payloadMap.put("exp", exp);
        try {
            //使用map生成payload的JSON字符串
            ObjectMapper objectMapper = new ObjectMapper();
            String data = objectMapper.writeValueAsString(payloadMap);
            //生成header
            String id = UUID.randomUUID().toString();
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES512)
                    .type(JOSEObjectType.JWT)
                    .keyID(id)
                    .build();
            //生成payload，并生成JWS对象
            Payload payload = new Payload(data);
            JWSObject jwsObject = new JWSObject(header, payload);
            //签名
            JWSSigner jwsSigner = new ECDSASigner( privateKey);
            jwsObject.sign(jwsSigner);
            //生成token
            return jwsObject.serialize();
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /**
     * 判断传入的 Token 是否合法
     * @param token
     * @return
     */
    public boolean tokenVerifier(String token) {
        //设置要解析的token
        JWSObject jwsObject = null;
        try {
            jwsObject = JWSObject.parse(token);
        } catch (ParseException e) { e.printStackTrace(); }
        return tokenVerifier(jwsObject);
    }

    /**
     * 判断 JWSObject 对象中的 Token 是否合法
     * @param jwsObject
     * @return
     */
    public boolean tokenVerifier(JWSObject jwsObject) {
        if (jwsObject == null)
            return false;
        try {
            //使用公钥生成解析器
            JWSVerifier verifier = new ECDSAVerifier(publicKey);
            //检验token
            return jwsObject.verify(verifier);
        } catch (JOSEException e) { e.printStackTrace(); }
        return false;
    }

    /**
     * 根据传入的 Token 字符串获取 Payload 的 JSON 字符串
     * @param token
     * @return
     */
    public String getPayloadToString(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            if (!tokenVerifier(jwsObject))
                return null;
            //获得Payload
            return jwsObject.getPayload().toString();
        } catch (ParseException e) { e.printStackTrace(); }
        return null;
    }

    /**
     * 根据传入的 Token 字符串将其 Payload 转换为 Map
     * @param token
     * @return
     */
    public Map<String, Object> getMapFromToken(String token) {
        String payload = getPayloadToString(token);
        return getMapFromJSON(payload);
    }

    /**
     * 将传入的 JSON 字符串转换为 Map
     * @param json
     * @return
     */
    public Map<String, Object> getMapFromJSON(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() { });
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    /**
     * 根据传入的 Token 判断该 Token 是否过期
     * @param token
     * @return
     */
    public boolean isExpireFromToken(String token) {
        Map<String, Object> map = getMapFromToken(token);
        if (map == null)
            return true;
        long exp = (long) map.get("exp");
        return isExpire(exp);
    }

    /**
     * 根据传入的 Token 解析出来的 JSON 判断该 Token 是否过期
     * @param json
     * @return
     */
    public boolean isExpireFromJSON(String json) {
        Map<String, Object> map = getMapFromJSON(json);
        if (map == null)
            return true;
        long exp = (long) map.get("exp");
        return isExpire(exp);
    }

    /**
     * 判断指定 exp 在当前时间是否已经过期
     * @param exp
     * @return
     */
    public boolean isExpire(long exp) {
        long date = new Date().getTime();
        if (exp - date <= 0)
            return true;
        return false;
    }

}
