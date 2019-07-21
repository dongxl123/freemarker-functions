package com.winbaoxian.common.freemarker.functions;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2019-03-08 17:26
 */
public class RepeatFunction implements TemplateMethodModelEx {

    /**
     * 重复N遍
     * USAGE
     * repeat("s",2)
     *
     * @return "ss"
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isNotEmpty(list)) {
            String str = list.get(0).toString();
            int count = 1;
            if (list.size() > 1) {
                count = ((SimpleNumber) list.get(1)).getAsNumber().intValue();
            }
            return StringUtils.repeat(str, count);
        }
        return StringUtils.EMPTY;
    }
}
