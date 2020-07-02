package com.winbaoxian.common.freemarker.functions;

import com.alibaba.fastjson.JSON;
import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import com.winbaoxian.common.freemarker.utils.JsonUtils;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public class SignatureFunction implements TemplateMethodModelEx {


    /**
     * USAGE
     * signature(object)
     *
     * @return string
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list)) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        Object o = DeepUnwrap.unwrap((TemplateModel) list.get(0));
        Object jo = null;
        if (o instanceof String) {
            jo = JsonUtils.INSTANCE.parseObject((String) o);
        } else {
            jo = JSON.toJSON(o);
        }
        Object newJo = JsonUtils.INSTANCE.getSortedJSONObject(jo);
        return DigestUtils.md5Hex(JSON.toJSONString(newJo));
    }


}
