package com.winbaoxian.common.freemarker.functions;

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
        if (CollectionUtils.isEmpty(list) || list.size() < 1) {
            return null;
        }
        String content = (String) DeepUnwrap.unwrap((TemplateModel) list.get(0));
        Document document = Jsoup.parse(content);
        return document;
    }

}
