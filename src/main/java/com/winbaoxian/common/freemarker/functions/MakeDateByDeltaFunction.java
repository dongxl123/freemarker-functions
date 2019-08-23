package com.winbaoxian.common.freemarker.functions;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.collections.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2019-03-08 17:26
 * <p>
 * 获取基于当前时间的日期
 * <p>
 * makeDateByDelta(year, date)
 * year: 年，正负数
 * date: 日，正负数
 */
public class MakeDateByDeltaFunction implements TemplateMethodModelEx {

    @Override
    public Object exec(List list) throws TemplateModelException {
        int year = 0;
        int date = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            year = ((SimpleNumber) list.get(0)).getAsNumber().intValue();
            if (list.size() > 1) {
                date = ((SimpleNumber) list.get(1)).getAsNumber().intValue();
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.DATE, date);
        return dateFormat.format(calendar.getTime());
    }
}
