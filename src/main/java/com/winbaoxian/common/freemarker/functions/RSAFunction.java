package com.winbaoxian.common.freemarker.functions;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

public class RSAFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * rsa().encrypt(data,publicKey)
     * rsa().decrypt(data,privateKey)
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        return new RSA();
    }

    public static class RSA {

        private static final Logger log = LoggerFactory.getLogger(RSA.class);
        private static final String CHARSET_NAME = "UTF-8";
        private static final String ALGORITHM_RSA = "RSA";

        public String encrypt(String data, String publicKey) {
            try {
                //base64编码的公钥
                byte[] decoded = Base64.decodeBase64(publicKey);
                PublicKey pubKey = KeyFactory.getInstance(ALGORITHM_RSA).generatePublic(new X509EncodedKeySpec(decoded));
                //RSA加密
                Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
                cipher.init(Cipher.ENCRYPT_MODE, pubKey);
                cipher.update(data.getBytes(CHARSET_NAME));
                byte[] encryped = cipher.doFinal();
                return Base64.encodeBase64String(encryped);
            } catch (Exception e) {
                log.error("rsa encrypt error", e);
            }
            return null;
        }

        public String decrypt(String data, String privateKey) {
            try {
                //64位解码加密后的字符串
                byte[] encryp = Base64.decodeBase64(data.getBytes(CHARSET_NAME));
                //base64编码的私钥
                byte[] decoded = Base64.decodeBase64(privateKey);
                PrivateKey priKey = KeyFactory.getInstance(ALGORITHM_RSA).generatePrivate(new PKCS8EncodedKeySpec(decoded));
                //RSA解密
                Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
                cipher.init(Cipher.DECRYPT_MODE, priKey);
                byte[] original = cipher.doFinal(encryp);
                return new String(original);
            } catch (Exception e) {
                log.error("rsa decrypt error", e);
            }
            return null;
        }
    }


}
