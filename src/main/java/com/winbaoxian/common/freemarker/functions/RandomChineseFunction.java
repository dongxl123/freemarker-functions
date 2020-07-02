package com.winbaoxian.common.freemarker.functions;

import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2020-7-2 15:32:58
 */
public class RandomChineseFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * randomChinese(count)
     * randomChinese(minCount, maxCount)
     *
     * @return 的的会计师
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
        try {
            StringBuilder sb = new StringBuilder();
            while (count-- > 0) {
                sb.append(randomOneWord());
            }
            return sb.toString();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return RandomStringUtils.random(count, (int) '\u4E00', (int) '\u9FA5', false, false);
    }

    private String randomOneWord() throws UnsupportedEncodingException {
        //一级常用汉字
        //获取高位值 B0-D7(176-215)
        int highPos =(0xB0 + Math.abs(RandomUtils.nextInt(0, 0xD7 - 0xB0)));
        //获取低位值 A1-FE(161-254)
        int lowPos = (0xA1 + Math.abs(RandomUtils.nextInt(0, 0xFE - 0xA1)));
        byte[] b = new byte[2];
        b[0] = (new Integer(highPos).byteValue());
        b[1] = (new Integer(lowPos).byteValue());
        String str = new String(b, "GBK");
        return str;
    }

}