package com.aisisea.oa.commons.utils.token;

import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.util.Base64;
import sun.security.ec.ECPrivateKeyImpl;
import sun.security.ec.ECPublicKeyImpl;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;


/**
 * 提供生成钥匙对和获取钥匙对的方法
 * @author Sea
 */
public class KeyManager {

    public static void generateKeyToFile(File publicKeyFile, File privateKeyFile) throws Exception {
        //KeyPairGenerator支持的算法：DiffieHellman DSA RSA EC
        //https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(Curve.P_521.toECParameterSpec());
        //获得密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //获得公钥私钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        //将公钥私钥转换为Base64编码
        byte[] publicKeyEncoded = publicKey.getEncoded();
        byte[] privateKeyEncoded = privateKey.getEncoded();
        String publicKeyBase64 = Base64.encode(publicKeyEncoded).toString();
        String privateKeyBase64 = Base64.encode(privateKeyEncoded).toString();
        //保存到文件
        FileUtils.writeStringToFile(publicKeyFile, publicKeyBase64);
        FileUtils.writeStringToFile(privateKeyFile, privateKeyBase64);
    }

    public static KeyPair getKeyPairFromFile(File publicKeyFile, File privateKeyFile) throws Exception{
        //从文件中读取
        String publicKeyBase64 = FileUtils.readFileToString(publicKeyFile);
        String privateKeyBase64 = FileUtils.readFileToString(privateKeyFile);
        //将Base64字符串解码
        byte[] publicKeyEncoded = new Base64(publicKeyBase64).decode();
        byte[] privateKeyEncoded = new Base64(privateKeyBase64).decode();
        //生成EC密钥
        PublicKey publicKey = new ECPublicKeyImpl(publicKeyEncoded);
        PrivateKey privateKey = new ECPrivateKeyImpl(privateKeyEncoded);
        //生成密钥对
        KeyPair keyPair = new KeyPair(publicKey, privateKey);

        return keyPair;
    }

}
