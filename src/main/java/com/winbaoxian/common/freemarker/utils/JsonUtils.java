package com.winbaoxian.common.freemarker.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

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

    private final static SerializeConfig config = new SerializeConfig();

    static {
        config.put(ObjectId.class, ToStringSerializer.instance);
    }

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

    public Object toJSON(Object javaObject) {
        return JSON.parse(toJSONString(javaObject));
    }

    public String toJSONString(Object o) {
        return JSON.toJSONString(o, config, SerializerFeature.WriteMapNullValue);
    }

    public String toPrettyJSONString(Object o) {
        return JSON.toJSONString(o, config, SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat);
    }

}
