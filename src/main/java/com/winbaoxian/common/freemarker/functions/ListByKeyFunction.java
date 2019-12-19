package com.winbaoxian.common.freemarker.functions;

import com.alibaba.fastjson.JSONArray;
import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import com.winbaoxian.common.freemarker.utils.JsonUtils;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListByKeyFunction implements TemplateMethodModelEx {

    private final static String DEFAULT_SEPARATOR = ",";

    /**
     * USAGE
     * listByKey(listObject, key, separatorChar)
     * <p>
     * listByKey(string,key)
     * listByKey(list,key)
     * listByKey(string,key,',')
     * listByKey(list,key,',')
     *
     * @return Document
     * @throws TemplateModelException
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list) || list.size() < 2) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        String sep = DEFAULT_SEPARATOR;
        Object content = DeepUnwrap.unwrap((TemplateModel) list.get(0));
        String key = (String) DeepUnwrap.unwrap((TemplateModel) list.get(1));
        if (list.size() > 2) {
            sep = (String) DeepUnwrap.unwrap((TemplateModel) list.get(2));
        }
        JSONArray jsonArray = null;
        if (content instanceof List) {
            jsonArray = new JSONArray((List) content);
        } else if (content instanceof String) {
            Object jsonRet = JsonUtils.INSTANCE.parseObject((String) content);
            if (jsonRet instanceof JSONArray) {
                jsonArray = (JSONArray) jsonRet;
            }
        }
        if (CollectionUtils.isEmpty(jsonArray)) {
            return null;
        }
        List<Object> retList = new ArrayList();
        for (Object o : jsonArray) {
            Map jo = (Map) o;
            if (jo.containsKey(key)) {
                retList.add(jo.get(key));
            }
        }
        return StringUtils.join(retList, sep);
    }

}
