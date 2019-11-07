package com.winbaoxian.common.freemarker.functions;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2019-8-20 18:05:20
 */
public class HmacFunction implements TemplateMethodModelEx {

    private static final Logger log = LoggerFactory.getLogger(HmacFunction.class);

    /**
     * USAGE
     * hmac().hmacMd5Hex(key, String)
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        return new Hmac();
    }


    public static class Hmac {

        public String hmacMd5Hex(String key, String data) {
            try {
                HmacUtils hmac = new HmacUtils(HmacAlgorithms.HMAC_MD5, key);
                return hmac.hmacHex(data);
            } catch (Exception e) {
                log.error("Md5.md5Hex error", e);
            }
            return null;
        }
    }


}