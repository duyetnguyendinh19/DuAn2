package com.vn.common;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.HashMap;
import java.util.Locale;

public class ThymeleafUtil {

    public static String getHtmlContentInClassPath(String templateFile, HashMap<String, Object> map) {
        ClassLoaderTemplateResolver templateresolver = new ClassLoaderTemplateResolver();
        templateresolver.setTemplateMode("HTML");
        templateresolver.setCharacterEncoding("UTF-8");
        templateresolver.setCacheable(false);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateresolver);

        final Context ctx = new Context(Locale.US);
        for (String key : map.keySet()) {
            Object val = map.get(key);
            ctx.setVariable(key, val);
        }
        final String htmlContent = templateEngine.process(templateFile, ctx);
        return htmlContent;
    }

}
