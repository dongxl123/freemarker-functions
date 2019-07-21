package com.winbaoxian.common.freemarker.functions;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public class ExtractTagValuesFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * extractTagValues(string,'extractAttributeName','id','r[0-9]+'))
     * extractTagValues(string,'value','class','className'))
     * extractTagValues(string,'value','tag','tagName'))
     *
     * @return ["attribute1","attribute2","attribute3"...]
     * @throws TemplateModelException
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list) || list.size() < 1) {
            return null;
        }
        String content = (String) DeepUnwrap.unwrap((TemplateModel) list.get(0));
        if (list.size() < 4) {
            return content;
        }
        String attr = (String) DeepUnwrap.unwrap((TemplateModel) list.get(1));
        String methodName = (String) DeepUnwrap.unwrap((TemplateModel) list.get(2));
        String value = (String) DeepUnwrap.unwrap((TemplateModel) list.get(3));
        Document document = Jsoup.parse(content);
        Elements elements = null;
        if ("tag".equalsIgnoreCase(methodName)) {
            elements = document.getElementsByTag(value);
        } else if ("class".equalsIgnoreCase(methodName)) {
            elements = document.getElementsByClass(value);
        } else if ("id".equalsIgnoreCase(methodName)) {
            elements = document.getElementsByAttributeValueMatching(methodName, value);
        } else {
            elements = document.getElementsByAttributeValueMatching(methodName, value);
        }
        if (elements == null || elements.size() == 0) {
            return null;
        }
        return elements.eachAttr(attr);
    }

}
