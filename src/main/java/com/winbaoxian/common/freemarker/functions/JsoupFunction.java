package com.winbaoxian.common.freemarker.functions;

import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

public class JsoupFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * jsoup(string)
     *
     * @return Document
     * @throws TemplateModelException
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list)) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        String content = (String) DeepUnwrap.unwrap((TemplateModel) list.get(0));
        Document document = Jsoup.parse(content);
        return document;
    }

}
