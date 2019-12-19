package com.winbaoxian.common.freemarker.functions;

import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;
import org.seimicrawler.xpath.JXDocument;

import java.util.List;

public class JsoupXpathFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * jsoupXpath(string)
     *
     * @return JXDocument
     * @throws TemplateModelException
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list)) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        String content = (String) DeepUnwrap.unwrap((TemplateModel) list.get(0));
        JXDocument document = JXDocument.create(content);
        return document;
    }

}
