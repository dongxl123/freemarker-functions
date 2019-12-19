package com.winbaoxian.common.freemarker.functions;

import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class EscapeStringFunction implements TemplateMethodModelEx {
    /**
     * USAGE
     * escapeString("a\"")
     * 同 ?j_string
     *
     * @param list
     * @return a\"
     * @throws TemplateModelException
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list)) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        String str = (String) DeepUnwrap.unwrap((TemplateModel) list.get(0));
        return StringEscapeUtils.escapeJava(str);
    }
}
