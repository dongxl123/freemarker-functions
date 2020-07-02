package com.winbaoxian.common.freemarker;

import com.winbaoxian.common.freemarker.functions.*;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public abstract class AbstractFreemarkerConfiguration {

    protected void settings(Configuration configuration) throws TemplateException {
        commonSettings(configuration);
        customSettings(configuration);
    }

    /**
     * 通用配置
     *
     * @param configuration
     * @throws TemplateException
     */
    private void commonSettings(Configuration configuration) throws TemplateException {
        configuration.setSetting(Configuration.NUMBER_FORMAT_KEY, "0.######;;roundingMode=halfUp");
        configuration.setSetting(Configuration.DATE_FORMAT_KEY, "yyyy-MM-dd");
        configuration.setSetting(Configuration.TIME_FORMAT_KEY, "HH:mm:ss");
        configuration.setSetting(Configuration.DATETIME_FORMAT_KEY, "yyyy-MM-dd HH:mm:ss");
        configuration.setSetting(Configuration.URL_ESCAPING_CHARSET_KEY, "ISO-8859-1");
        configuration.setSharedVariable("random", new RandomFunction());
        configuration.setSharedVariable("range", new RangeFunction());
        configuration.setSharedVariable("toJSONString", new ToJSONStringFunction());
        configuration.setSharedVariable("toJSON", new ToJSONFunction());
        configuration.setSharedVariable("toCookieString", new ToCookieStringFunction());
        configuration.setSharedVariable("descartes", new DescartesFunction());
        configuration.setSharedVariable("repeat", new RepeatFunction());
        configuration.setSharedVariable("matchNumber", new MatchNumberFunction());
        configuration.setSharedVariable("extractTagValues", new ExtractTagValuesFunction());
        configuration.setSharedVariable("getBirthday", new GetBirthdayFunction());
        configuration.setSharedVariable("escapeString", new EscapeStringFunction());
        configuration.setSharedVariable("unEscapeString", new UnEscapeStringFunction());
        configuration.setSharedVariable("jsoup", new JsoupFunction());
        configuration.setSharedVariable("jsoupXpath", new JsoupXpathFunction());
        configuration.setSharedVariable("generateIdNum", new GenerateIdNumFunction());
        configuration.setSharedVariable("makeDateByDelta", new MakeDateByDeltaFunction());
        configuration.setSharedVariable("getDateByDelta", new GetDateByDeltaFunction());
        configuration.setSharedVariable("aes", new AESFunction());
        configuration.setSharedVariable("rsa", new RSAFunction());
        configuration.setSharedVariable("base64", new Base64Function());
        configuration.setSharedVariable("md5", new Md5Function());
        configuration.setSharedVariable("hmac", new HmacFunction());
        configuration.setSharedVariable("tripleDES", new TripleDESFunction());
        configuration.setSharedVariable("urlExtract", new UrlExtractFunction());
        configuration.setSharedVariable("jsonPathExtract", new JsonPathExtractFunction());
        configuration.setSharedVariable("listByKey", new ListByKeyFunction());
        configuration.setSharedVariable("round", new RoundFunction());
        configuration.setSharedVariable("signature", new SignatureFunction());
        configuration.setSharedVariable("randomNumber", new RandomNumberFunction());
        configuration.setSharedVariable("randomLetter", new RandomLetterFunction());
        configuration.setSharedVariable("randomChinese", new RandomChineseFunction());
        configuration.setSharedVariable("randomName", new RandomNameFunction());
        configuration.setSharedVariable("randomChar", new RandomCharFunction());
    }

    /**
     * 扩展配置
     *
     * @param configuration
     */
    protected void customSettings(Configuration configuration) throws TemplateException {

    }

}
