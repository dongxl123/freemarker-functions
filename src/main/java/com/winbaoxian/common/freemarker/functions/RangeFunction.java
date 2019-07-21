package com.winbaoxian.common.freemarker.functions;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2019-03-08 17:26
 */
public class RangeFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * range(start, end)
     *
     * @return [1, 2, 3, 4, 6...]
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        List<Integer> retList = new ArrayList<>();
        int start = 0;
        int end = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            start = ((SimpleNumber) list.get(0)).getAsNumber().intValue();
            if (list.size() > 1) {
                end = ((SimpleNumber) list.get(1)).getAsNumber().intValue();
            }
        }
        for (int i = start; i <= end; i++) {
            retList.add(i);
        }
        return retList;
    }

}
