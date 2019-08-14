package com.winbaoxian.common.freemarker.functions;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.Base64;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2019-03-08 17:26
 */
public class Base64Function implements TemplateMethodModelEx {

    /**
     * USAGE
     * aes(key,iv).encrypt(String)
     * aes(key,iv).decrypt(String)
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        return new BASE64() ;
    }


    public static class BASE64 {

        private final static String CHARSET_NAME = "UTF-8";


        public String encode(String data) {
            //偏移量
            try {
               return Base64.getEncoder().encodeToString(data.getBytes(CHARSET_NAME));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        public String decode(String data) {
            try {
                return new String(Base64.getDecoder().decode(data),CHARSET_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}