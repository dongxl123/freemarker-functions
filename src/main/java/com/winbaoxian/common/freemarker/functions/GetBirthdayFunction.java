package com.winbaoxian.common.freemarker.functions;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.List;

public class GetBirthdayFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * getBirthday(age)
     *
     * @return 1992-11-12
     * @throws TemplateModelException
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list) || list.size() < 1) {
            return null;
        }
        Integer age = ((SimpleNumber) list.get(0)).getAsNumber().intValue();
        Date date = DateUtils.addMonths(DateUtils.addYears(new Date(), -age), -3);
        return DateFormatUtils.format(date, "yyyy-MM-dd");
    }

}
