package com.winbaoxian.common.freemarker.functions;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ToCookieStringFunction implements TemplateMethodModelEx {


    /**
     * 返回cookies格式的string
     * USAGE
     * toCookieString(["JSESSIONID=Gnj-mixq9v8lFSbaBRg7LJOIxkkQs2LPyTOhRgkOBgDfU55Mo15N!952609197","userId=11233"])
     * toCookieString({"JSESSIONID":"Gnj-mixq9v8lFSbaBRg7LJOIxkkQs2LPyTOhRgkOBgDfU55Mo15N!952609197","userId":"11233"})
     * toCookieString("JSESSIONID=Gnj-mixq9v8lFSbaBRg7LJOIxkkQs2LPyTOhRgkOBgDfU55Mo15N!952609197;userId=11233")
     *
     * @param list
     * @return "JSESSIONID=Gnj-mixq9v8lFSbaBRg7LJOIxkkQs2LPyTOhRgkOBgDfU55Mo15N!952609197;userId=11233"
     * @throws TemplateModelException
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isNotEmpty(list)) {
            Object model = list.get(0);
            Object o = DeepUnwrap.unwrap((TemplateModel) model);
            return toCookieString(o);
        }
        return null;
    }

    private String toCookieString(Object o) {
        if (o == null) {
            return StringUtils.EMPTY;
        }
        if (ClassUtils.isAssignable(o.getClass(), Map.class)) {
            List<String> list = new ArrayList<>();
            Map cookieMap = (Map) o;
            for (Object key : cookieMap.keySet()) {
                Object value = cookieMap.get(key);
                String keyString = String.format("%s=%s", key.toString(), value.toString());
                list.add(keyString);
            }
            return StringUtils.join(list, ";");
        } else if (ClassUtils.isAssignable(o.getClass(), Collection.class)) {
            return StringUtils.join((Collection) o, ";");
        } else if (ClassUtils.isAssignable(o.getClass(), CharSequence.class)) {
            return o.toString();
        }
        return StringUtils.EMPTY;
    }


}
