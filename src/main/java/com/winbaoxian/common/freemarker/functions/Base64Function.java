package com.winbaoxian.common.freemarker.functions;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2019-03-08 17:26
 */
public class Base64Function implements TemplateMethodModelEx {

    /**
     * USAGE
     * base64().encode(String)
     * base64().decode(String)
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        return new BASE64();
    }


    public static class BASE64 {

        private static final Logger log = LoggerFactory.getLogger(BASE64.class);
        private static final String CHARSET_NAME = "UTF-8";

        public String encode(String data) {
            //偏移量
            try {
                return Base64.getEncoder().encodeToString(data.getBytes(CHARSET_NAME));
            } catch (Exception e) {
                log.error("Base64 encode error", e);
            }
            return null;
        }

        public String decode(String data) {
            try {
                return new String(Base64.getDecoder().decode(data), CHARSET_NAME);
            } catch (Exception e) {
                log.error("Base64 decode error", e);
            }
            return null;
        }

    }

}