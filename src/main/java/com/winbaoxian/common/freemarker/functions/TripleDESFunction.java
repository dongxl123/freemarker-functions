package com.winbaoxian.common.freemarker.functions;

import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2019-03-08 17:26
 */
public class TripleDESFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * tripleDES(key,iv).encrypt(String)
     * tripleDES(key,iv).decrypt(String)
     * tripleDES(key).encrypt(String)
     * tripleDES(key).decrypt(String)
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list)) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        } else if (list.size() == 1) {
            String key = (String) DeepUnwrap.unwrap((TemplateModel) list.get(0));
            return new TripleDES(key);
        } else {
            String key = (String) DeepUnwrap.unwrap((TemplateModel) list.get(0));
            String iv = (String) DeepUnwrap.unwrap((TemplateModel) list.get(1));
            return new TripleDES(key, iv);
        }
    }


    public static class TripleDES {

        private static final Logger log = LoggerFactory.getLogger(TripleDES.class);
        private static final String CHARSET_NAME = "UTF-8";
        private static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
        private static final String KEY_ALGORITHM = "DESede";
        private String key;
        private String iv;

        public TripleDES(String key) {
            this.key = key;
        }

        public TripleDES(String key, String iv) {
            this.key = key;
            this.iv = iv;
        }

        public String encrypt(String data) {
            //偏移量
            try {
                byte[] dataBytes = data.getBytes(CHARSET_NAME);
                SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
                SecretKey secretKey = skf.generateSecret(new DESedeKeySpec(key.getBytes(CHARSET_NAME)));
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                if (StringUtils.isNotBlank(iv)) {
                    //设置偏移量参数
                    byte[] ivBytes = iv.getBytes(CHARSET_NAME);
                    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
                } else {
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                }
                byte[] encryped = cipher.doFinal(dataBytes);
                return Base64.encodeBase64String(encryped);
            } catch (Exception e) {
                log.error("TripleDES encrypt error", e);
            }
            return null;
        }


        public String decrypt(String data) {
            try {
                byte[] encryp = Base64.decodeBase64(data);
                SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
                SecretKey secretKey = skf.generateSecret(new DESedeKeySpec(key.getBytes(CHARSET_NAME)));
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                if (StringUtils.isNotBlank(iv)) {
                    byte[] ivBytes = iv.getBytes(CHARSET_NAME);
                    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
                } else {
                    cipher.init(Cipher.DECRYPT_MODE, secretKey);
                }
                byte[] original = cipher.doFinal(encryp);
                return new String(original);
            } catch (Exception e) {
                log.error("TripleDES decrypt error", e);
            }
            return null;
        }
    }

}