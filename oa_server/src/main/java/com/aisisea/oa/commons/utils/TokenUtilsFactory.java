package com.aisisea.oa.commons.utils;

import com.aisisea.oa.commons.utils.token.TokenUtils;
import com.aisisea.oa.commons.utils.token.TokenUtilsBuilder;

import java.io.File;

public class TokenUtilsFactory {

    private static String publicKeyPath;
    private static String privateKeyPath;
    //使用饿汉式单例模式
    private static TokenUtils tokenUtils;

    static {
        setKeyPath("public.key", "private.key");
    }

    private TokenUtilsFactory() {
    }

    public static String getPublicKeyPath() {
        return publicKeyPath;
    }

    public static String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public static TokenUtils setKeyPath(String publicKeyPath, String privateKeyPath) {
        //设置路径参数
        TokenUtilsFactory.publicKeyPath = TokenUtilsFactory.class.getClassLoader().getResource(publicKeyPath).getPath();
        TokenUtilsFactory.privateKeyPath = TokenUtilsFactory.class.getClassLoader().getResource(privateKeyPath).getPath();
        //创建File对象
        File publicKeyFile = new File(TokenUtilsFactory.publicKeyPath);
        File privateKeyFile = new File(TokenUtilsFactory.privateKeyPath);
        //System.out.println(publicKeyFile.getAbsolutePath());
        //利用File对象创建TokenUtils对象
        try {
            tokenUtils = TokenUtilsBuilder.build(publicKeyFile, privateKeyFile);
        } catch (Exception e) {
            tokenUtils = null;
            e.printStackTrace();
        }

        return tokenUtils;
    }

    public static TokenUtils getTokenUtils() {
        return tokenUtils;
    }
}
