package com.aisisea.oa.commons.utils.token;

import java.io.File;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

/**
 * 提供获取钥匙对并创建TokenUtils的方法
 * @author Sea
 */
public class TokenUtilsBuilder {

    //当create参数不传时，默认为true
    public static TokenUtils build(File publicKeyFile, File privateKeyFile) throws Exception{
        return build(publicKeyFile, privateKeyFile, false);
    }

    public static TokenUtils build(File publicKeyFile, File privateKeyFile, boolean create) throws Exception{
        //System.out.println(publicKeyFile.exists());
        if (!publicKeyFile.exists() || !privateKeyFile.exists()) {
            if (create)
                KeyManager.generateKeyToFile(publicKeyFile, privateKeyFile);
            else return null;
        }

        KeyPair keyPair = KeyManager.getKeyPairFromFile(publicKeyFile, privateKeyFile);
        TokenUtils tokenUtils = new TokenUtils
                ((ECPublicKey) keyPair.getPublic(), (ECPrivateKey) keyPair.getPrivate());

        return tokenUtils;
    }
}
