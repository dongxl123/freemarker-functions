package com.winbaoxian.common.freemarker.functions;

import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class UnEscapeStringFunction implements TemplateMethodModelEx {
    /**
     * USAGE
     * unEscapeString("a\"")
     * 同 ?eval
     *
     * @param list
     * @return a"
     * @throws TemplateModelException
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list)) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        String str = (String) DeepUnwrap.unwrap((TemplateModel) list.get(0));
        return StringEscapeUtils.unescapeJava(str);
    }
}
