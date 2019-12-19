package com.winbaoxian.common.freemarker.functions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public class JsonPathExtractFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * jsonPathExtract(jsonObject, "$.result")
     *
     * @return Document
     * @throws TemplateModelException
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list) || list.size() < 2) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        Object content = DeepUnwrap.unwrap((TemplateModel) list.get(0));
        String path = (String) DeepUnwrap.unwrap((TemplateModel) list.get(1));
        return JSONPath.extract(JSON.toJSONString(content), path);
    }

}
