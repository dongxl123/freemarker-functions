package com.winbaoxian.common.freemarker.functions;

import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2020-7-2 15:32:58
 */
public class RandomCharFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * randomChar(string, count)
     * randomChar(string, minCount, maxCount)
     *
     * @return 的的会计师
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list)) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        if (list.size() < 2) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        String string = ((SimpleScalar) list.get(0)).getAsString();
        int minCount = ((SimpleNumber) list.get(1)).getAsNumber().intValue();
        int count = minCount;
        if (list.size() > 2) {
            int maxCount = ((SimpleNumber) list.get(2)).getAsNumber().intValue();
            count = RandomUtils.nextInt(minCount, maxCount + 1);
            if (maxCount < minCount) {
                throw new TemplateModelException(TemplateMethodModelExMsg.ERROR_PARAMETER_FORMAT);
            }
        }
        return RandomStringUtils.random(count, string);
    }

}
