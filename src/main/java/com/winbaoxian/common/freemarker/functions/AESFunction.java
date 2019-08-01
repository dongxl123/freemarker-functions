package com.winbaoxian.common.freemarker.functions;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;

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
            return null;
        }
        String key = (String) DeepUnwrap.unwrap((TemplateModel) list.get(0));
        String iv = (String) DeepUnwrap.unwrap((TemplateModel) list.get(1));
        return new AES(key, iv);
    }


    public static class AES {

        private final static String CHARSET_NAME = "UTF-8";
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
                SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(CHARSET_NAME), "AES");
                //设置偏移量参数
                IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
                byte[] encryped = cipher.doFinal(dataBytes);
                return Base64.encodeBase64String(encryped);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public String decrypt(String data) {
            try {
                byte[] ivBytes = iv.getBytes(CHARSET_NAME);
                byte[] encryp = Base64.decodeBase64(data);
                Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
                SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(CHARSET_NAME), "AES");
                IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
                byte[] original = cipher.doFinal(encryp);
                return new String(original);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}