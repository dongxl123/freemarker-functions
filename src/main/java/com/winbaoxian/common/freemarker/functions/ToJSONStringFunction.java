package com.winbaoxian.common.freemarker.functions;

import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import com.winbaoxian.common.freemarker.utils.JsonUtils;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2019-03-08 17:26
 */
public class ToJSONStringFunction implements TemplateMethodModelEx {

    /**
     * 返回json string
     * USAGE
     * toPrettyJSONString({"a":1,"b":2})
     * toPrettyJSONString({"a":1,"b":2}, isPretty)
     *
     * @return "{\"a\":1,\"b\":2}"
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list)) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        Object model = list.get(0);
        boolean isPretty = false;
        if (list.size() > 1) {
            isPretty = ((SimpleNumber) list.get(1)).getAsNumber().intValue() > 0;
        }
        if (isPretty) {
            return JsonUtils.INSTANCE.toPrettyJSONString(DeepUnwrap.unwrap((TemplateModel) model));
        }
        return JsonUtils.INSTANCE.toJSONString(DeepUnwrap.unwrap((TemplateModel) model));
    }
}
