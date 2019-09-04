package com.winbaoxian.common.freemarker.functions;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlExtractFunction implements TemplateMethodModelEx {

    /**
     * USAGE
     * urlExtract(url).getPath()
     * urlExtract(url).getParameter(key)
     *
     * @param list
     * @return
     * @throws TemplateModelException
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (CollectionUtils.isNotEmpty(list)) {
            String urlStr = (String) DeepUnwrap.unwrap((TemplateModel) list.get(0));
            return new UrlExtractor(urlStr);
        }
        return null;
    }

    public static class UrlExtractor {

        private String path;
        private Map<String, String> parameters = new HashMap<>();

        public UrlExtractor(String url) {
            if (StringUtils.isBlank(url)) {
                return;
            }
            String[] urlParts = StringUtils.split(url, "?");
            this.path = urlParts[0];
            if (urlParts.length < 2) {
                return;
            }
            String[] parameterParts = StringUtils.split(urlParts[1], "&");
            if (parameterParts.length < 1) {
                return;
            }
            try {
                for (String parameterPart : parameterParts) {
                    String[] ps = StringUtils.split(parameterPart, "=");
                    if (ps.length < 1) {
                        continue;
                    }
                    if (ps.length < 2) {
                        parameters.put(ps[0], StringUtils.EMPTY);
                    } else {
                        parameters.put(ps[0], URLDecoder.decode(ps[1], "utf-8"));
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        public String getPath() {
            return this.path;
        }

        public String getParameter(String key) {
            return parameters.get(key);
        }

    }

}
