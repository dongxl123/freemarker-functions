package com.winbaoxian.common.freemarker.functions;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2019-03-08 17:26
 */
public class RandomFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * random()
     *
     * @return 0-1
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        return Math.random();
    }
}
