package com.winbaoxian.common.freemarker.functions;

import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2019-03-08 17:26
 */
public class RoundFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * round(number, scale)
     * round(number)
     *
     * @return number with scale
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        int scale = 0;
        if (CollectionUtils.isEmpty(list) || list.size() < 1) {
            throw new TemplateModelException(TemplateMethodModelExMsg.MISSING_PARAMETERS);
        }
        double value = ((SimpleNumber) list.get(0)).getAsNumber().doubleValue();
        if (list.size() > 1) {
            scale = ((SimpleNumber) list.get(1)).getAsNumber().intValue();
        }
        BigDecimal bd = new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

}
