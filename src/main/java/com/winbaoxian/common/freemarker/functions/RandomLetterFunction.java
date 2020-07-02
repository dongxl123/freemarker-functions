package com.winbaoxian.common.freemarker.functions;

import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import freemarker.template.SimpleNumber;
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
public class RandomLetterFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * randomLetter(count)
     * randomLetter(minCount, maxCount)
     *
     * @return abcd
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list)) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        int minCount = ((SimpleNumber) list.get(0)).getAsNumber().intValue();
        int count = minCount;
        if (list.size() > 1) {
            int maxCount = ((SimpleNumber) list.get(1)).getAsNumber().intValue();
            if (maxCount < minCount) {
                throw new TemplateModelException(TemplateMethodModelExMsg.ERROR_PARAMETER_FORMAT);
            }
            count = RandomUtils.nextInt(minCount, maxCount + 1);
        }
        return RandomStringUtils.randomAlphabetic(count);
    }

}
