package com.winbaoxian.common.freemarker.functions;

import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2019-03-08 17:26
 */
public class AESFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * aes(key,iv).encrypt(String)
     * aes(key,iv).decrypt(String)
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list) || list.size() < 2) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        String key = (String) DeepUnwrap.unwrap((TemplateModel) list.get(0));
        String iv = (String) DeepUnwrap.unwrap((TemplateModel) list.get(1));
        return new AES(key, iv);
    }


    public static class AES {

        private static final Logger log = LoggerFactory.getLogger(AES.class);
        private static final String CHARSET_NAME = "UTF-8";
        private static final String ALGORITHM_AES = "AES";
        private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

        private String key;
        private String iv;

        public AES(String key, String iv) {
            this.key = key;
            this.iv = iv;
        }

        public String encrypt(String data) {
            //偏移量
            try {
                byte[] ivBytes = iv.getBytes(CHARSET_NAME);
                byte[] dataBytes = data.getBytes(CHARSET_NAME);
                //填充
                SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(CHARSET_NAME), ALGORITHM_AES);
                //设置偏移量参数
                IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
                byte[] encryped = cipher.doFinal(dataBytes);
                return Base64.encodeBase64String(encryped);
            } catch (Exception e) {
                log.error("aes encrypt error", e);
            }
            return null;
        }

        public String decrypt(String data) {
            try {
                byte[] ivBytes = iv.getBytes(CHARSET_NAME);
                byte[] encryp = Base64.decodeBase64(data);
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(CHARSET_NAME), ALGORITHM_AES);
                IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
                byte[] original = cipher.doFinal(encryp);
                return new String(original);
            } catch (Exception e) {
                log.error("aes decrypt error", e);
            }
            return null;
        }
    }

}