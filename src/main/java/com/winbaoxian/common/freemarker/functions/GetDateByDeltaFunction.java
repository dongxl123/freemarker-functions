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
 * @date2020-5-15 11:03:46
 * <p>
 * 获取基于当前时间的日期
 * <p>
 * getDateByDelta(year, month, date)
 * getDateByDelta(year, month)
 * getDateByDelta(year)
 * year: 年，正负数
 * date: 日，正负数
 */
public class GetDateByDeltaFunction implements TemplateMethodModelEx {

    @Override
    public Object exec(List list) throws TemplateModelException {
        int year = 0;
        int month =0;
        int date = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            year = ((SimpleNumber) list.get(0)).getAsNumber().intValue();
            if (list.size() > 1) {
                month = ((SimpleNumber) list.get(1)).getAsNumber().intValue();
                if (list.size() > 2) {
                    date = ((SimpleNumber) list.get(2)).getAsNumber().intValue();
                }
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DATE, date);
        return dateFormat.format(calendar.getTime());
    }

}
