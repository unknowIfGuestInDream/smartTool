package com.tlcsdm.template;

import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;

/**
 * TemplateEngine创建
 *
 * @author: 唐 亮
 * @date: 2022/8/12 12:07
 * @since: 1.0
 */
public class FreemarkerUtils {

    private final static String path = "templates";

    private FreemarkerUtils() {
    }

    private static class StringResource {
        private final static TemplateEngine instance = TemplateUtil.createEngine(
                new TemplateConfig(path, TemplateConfig.ResourceMode.STRING));
    }

    private static class ClassPathResource {
        private final static TemplateEngine instance = TemplateUtil.createEngine(
                new TemplateConfig(path, TemplateConfig.ResourceMode.CLASSPATH));
    }

    private static class FileResource {
        private final static TemplateEngine instance = TemplateUtil.createEngine(
                new TemplateConfig(path, TemplateConfig.ResourceMode.FILE));
    }

    private static class WebRootResource {
        private final static TemplateEngine instance = TemplateUtil.createEngine(
                new TemplateConfig(path, TemplateConfig.ResourceMode.WEB_ROOT));
    }

    private static class CompositeResource {
        private final static TemplateEngine instance = TemplateUtil.createEngine(
                new TemplateConfig(path, TemplateConfig.ResourceMode.COMPOSITE));
    }

    public static TemplateEngine getStringEngine() {
        return StringResource.instance;
    }

    public static TemplateEngine getClassPathEngine() {
        return ClassPathResource.instance;
    }

    public static TemplateEngine getFileEngine() {
        return FileResource.instance;
    }

    public static TemplateEngine getWebRootEngine() {
        return WebRootResource.instance;
    }

    public static TemplateEngine getCompositeEngine() {
        return CompositeResource.instance;
    }

}
