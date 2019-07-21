package com.winbaoxian.common.freemarker.functions;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchNumberFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * matchNumber(string)
     * 等价于 string?matches('([0-9.]+)')
     *
     * @return [number1, number2, 33, 44...]
     * @throws TemplateModelException
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list) || list.size() < 1) {
            return null;
        }
        String content = (String) DeepUnwrap.unwrap((TemplateModel) list.get(0));
        String regex = "([\\d\\.]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        List<String> ret = new ArrayList<>();
        while (matcher.find()) {
            ret.add(matcher.group());
        }
        return ret;
    }

}
