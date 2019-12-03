package com.winbaoxian.common.freemarker.functions.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * @author dongxuanliang252
 * @date 2019-03-06 18:08
 */
public enum JsonUtils {

    INSTANCE;

    private final static String OPEN_BRACE = "{";
    private final static String CLOSE_BRACE = "}";
    private final static String OPEN_BRACKET = "[";
    private final static String CLOSE_BRACKET = "]";

    private final String JSON_DATE_FORMAT_PATTERN = "'date'(yyyy-MM-dd HH:mm:ss.S)";

    public Object parseObject(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        text = StringUtils.trim(text);
        try {
            if (StringUtils.startsWith(text, OPEN_BRACE) && StringUtils.endsWith(text, CLOSE_BRACE)) {
                return JSON.parseObject(text);
            } else if (StringUtils.startsWith(text, OPEN_BRACKET) && StringUtils.endsWith(text, CLOSE_BRACKET)) {
                return JSON.parseArray(text);
            }
        } catch (Exception e) {
            return text;
        }
        return text;
    }

    public String toJSONString(Object o) {
        return JSON.toJSONString(o, new JsonUtils.WinTestNGValueFilter(), SerializerFeature.WriteMapNullValue);
    }

    private class WinTestNGValueFilter implements ValueFilter {

        @Override
        public Object process(Object o, String filedName, Object filedValue) {
            if (filedValue instanceof ObjectId) {
                return ((ObjectId) filedValue).toString();
            } else if (filedValue instanceof Date) {
                return DateFormatUtils.format((Date) filedValue, JSON_DATE_FORMAT_PATTERN);
            }
            return filedValue;
        }
    }

}
