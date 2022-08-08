package com.tlcsdm.words;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.SaveFormat;
import com.aspose.words.SaveOptions;
import wordUtil.LicenseLoad;

/**
 * 支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
 *
 * @author: TangLiang
 * @date: 2022/3/25 9:45
 * @since: 1.0
 */
public class HtmlConver {
    static {
        LicenseLoad.getLicense();
    }

    /**
     * html文本转换doc
     *
     * @param html html文本
     */
    public static void htmlToDoc(String html, String wordPath) {
        htmlToObject(html, wordPath, "DOC");
    }

    /**
     * html文本转换docx
     *
     * @param html html文本
     */
    public static void htmlToDocx(String html, String wordPath) {
        htmlToObject(html, wordPath, "DOCX");
    }

    /**
     * html文本转换pdf
     *
     * @param html html文本
     */
    public static void htmlToPdf(String html, String wordPath) {
        htmlToObject(html, wordPath, "PDF");
    }

    /**
     * html文本转换object
     *
     * @param html html文本
     */
    public static void htmlToObject(String html, String wordPath, String saveFormat) {
        try {
            Document doc = new Document();
            DocumentBuilder builder = new DocumentBuilder(doc);
            builder.insertHtml(html);
            //生成doc文件
            doc.save(wordPath, SaveOptions.createSaveOptions(SaveFormat.fromName(saveFormat)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
