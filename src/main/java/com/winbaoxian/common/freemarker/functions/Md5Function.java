package com.winbaoxian.common.freemarker.functions;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2019-8-20 18:05:20
 */
public class Md5Function implements TemplateMethodModelEx {

    /**
     * USAGE
     * md5().md5Hex(String)
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        return new Md5();
    }


    public static class Md5 {

        public String md5Hex(String data) {
            try {
                return DigestUtils.md5Hex(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }


}