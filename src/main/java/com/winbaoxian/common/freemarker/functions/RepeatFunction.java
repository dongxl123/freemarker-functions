package com.winbaoxian.common.freemarker.functions;

import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
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
        if (CollectionUtils.isEmpty(list)) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        String baseStr = (String) DeepUnwrap.unwrap((TemplateModel) list.get(0));
        int count = 1;
        if (list.size() > 1) {
            count = ((SimpleNumber) list.get(1)).getAsNumber().intValue();
        }
        return StringUtils.repeat(baseStr, count);
    }

}
