package com.winbaoxian.common.freemarker.functions;

import com.alibaba.fastjson.JSONArray;
import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import com.winbaoxian.common.freemarker.utils.JsonUtils;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * USAGE
 * shuffle(list)
 * shuffle(listStr)
 */
public class ShuffleFunction implements TemplateMethodModelEx {

    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list)) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        Object content = DeepUnwrap.unwrap((TemplateModel) list.get(0));
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
            throw new TemplateModelException(TemplateMethodModelExMsg.ERROR_PARAMETER_FORMAT);
        }
        Collections.shuffle(jsonArray);
        return jsonArray;
    }

}
