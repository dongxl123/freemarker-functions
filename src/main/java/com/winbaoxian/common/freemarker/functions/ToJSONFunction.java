package com.winbaoxian.common.freemarker.functions;

import com.alibaba.fastjson.JSON;
import com.winbaoxian.common.freemarker.functions.utils.JsonUtils;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2019-7-15 16:27:20
 */
public class ToJSONFunction implements TemplateMethodModelEx {

    /**
     * 返回json
     * USAGE
     * toJSON("{\"a\":1,\"b\":2}")
     *
     * @return {"a":1,"b":2}
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isNotEmpty(list)) {
            Object model = list.get(0);
            Object o = DeepUnwrap.unwrap((TemplateModel) model);
            if (o instanceof String) {
                return JsonUtils.INSTANCE.parseObject((String) o);
            } else {
                return JSON.toJSON(o);
            }
        }
        return null;
    }
}
