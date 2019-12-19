package com.winbaoxian.common.freemarker.functions;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.winbaoxian.common.freemarker.constant.TemplateMethodModelExMsg;
import com.winbaoxian.common.freemarker.utils.JsonUtils;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * @author dongxuanliang252
 * @date 2019-7-5 17:41:06
 */
@SuppressWarnings("unchecked")
public class DescartesFunction implements TemplateMethodModelEx {

    private static final Logger log = LoggerFactory.getLogger(DescartesFunction.class);

    /**
     * USAGE
     * descartes(alias1, list1, alias2,list2...)
     * descartes('a',[1,2],'b',[5,6])
     *
     * @return [{"a":1,"b":5},{"a":1,"b":6},{"a":2,"b":5},{"a":2,"b":6}]
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isEmpty(list) || list.size() % 2 != 0) {
            throw new TemplateModelException(TemplateMethodModelExMsg.ERROR_PARAMETER_FORMAT);
        }
        Map<String, JSONArray> map = new LinkedHashMap<>();
        for (int i = 0; i < list.size(); i = i + 2) {
            String alias = list.get(i).toString();
            Object o = DeepUnwrap.unwrap((TemplateModel) list.get(i + 1));
            putObjectIntoMap(alias, o, map);
        }
        List<JSONObject> retList = descartes(map);
        return retList;
    }

    private void putObjectIntoMap(String alias, Object o, Map<String, JSONArray> map) {
        if (o == null) {
            return;
        } else if (o instanceof List) {
            if (CollectionUtils.isEmpty((List) o)) {
                return;
            } else {
                map.put(alias, new JSONArray((List<Object>) o));
            }
        } else if (o instanceof String) {
            Object jsonRet = JsonUtils.INSTANCE.parseObject((String) o);
            if (jsonRet == null) {
                return;
            } else if (jsonRet instanceof String) {
                JSONArray array = new JSONArray();
                array.add(o);
                map.put(alias, array);
            } else {
                putObjectIntoMap(alias, jsonRet, map);
            }
        } else {
            JSONArray array = new JSONArray();
            array.add(o);
            map.put(alias, array);
        }
    }

    private List<JSONObject> descartes(Map<String, JSONArray> map) {
        return descartes(map, new ArrayList<>());
    }

    private List<JSONObject> descartes(Map<String, JSONArray> map, List<JSONObject> result) {
        if (map.isEmpty()) {
            return result;
        }
        String alias = map.keySet().iterator().next();
        JSONArray valueList = map.get(alias);
        map.remove(alias);
        List<JSONObject> newResult = new ArrayList<>();
        if (result.isEmpty()) {
            for (Object o : valueList) {
                JSONObject newJo = new JSONObject();
                newJo.put(alias, o);
                newResult.add(newJo);
            }
        } else {
            for (JSONObject jo : result) {
                for (Object o : valueList) {
                    JSONObject newJo = deepClone(jo);
                    newJo.put(alias, o);
                    newResult.add(newJo);
                }
            }
        }
        return descartes(map, newResult);
    }

    private <T> T deepClone(T object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (Exception e) {
            log.error("deepClone error", e);
            return null;
        }
    }

}
