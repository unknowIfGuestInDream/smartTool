package com.tlcsdm.freemarker;

import cn.hutool.core.lang.Dict;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.extra.template.engine.freemarker.FreemarkerEngine;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author: 唐 亮
 * @date: 2022/8/11 22:55
 * @since: 1.0
 */
public class FreemarkerTest {

    @Test
    public void freemarkerEngineTest() {
        // 字符串模板
        TemplateEngine engine = TemplateUtil.createEngine(
                new TemplateConfig("templates", TemplateConfig.ResourceMode.STRING));
        Template template = engine.getTemplate("hello,${name}");
        String result = template.render(Dict.create().set("name", "hutool"));
        Assert.assertEquals("hello,hutool", result);

        //ClassPath模板
        engine = TemplateUtil.createEngine(
                new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH).setCustomEngine(FreemarkerEngine.class));
        template = engine.getTemplate("freemarker_test.ftl");
        result = template.render(Dict.create().set("name", "tang"));
        Assert.assertEquals("hello,tang", result);

        //其它方式查找模板
//        CLASSPATH 从ClassPath加载模板
//        FILE 从File本地目录加载模板
//        WEB_ROOT 从WebRoot目录加载模板
//        STRING 从模板文本加载模板
//        COMPOSITE 复合加载模板（分别从File、ClassPath、Web-root、String方式尝试查找模板）
    }
}
