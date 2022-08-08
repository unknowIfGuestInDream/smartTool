package com.tlcsdm.words;

import com.aspose.words.Document;
import com.aspose.words.HtmlSaveOptions;
import com.aspose.words.SaveFormat;
import wordUtil.LicenseLoad;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
 *
 * @author: TangLiang
 * @date: 2022/3/25 9:45
 * @since: 1.0
 */
public class WordConver {
    static {
        LicenseLoad.getLicense();
    }

    /**
     * word输入流转换html文本
     *
     * @param in word输入流
     */
    public static String wordToHtml(InputStream in) {
        ByteArrayOutputStream htmlStream = new ByteArrayOutputStream();
        String htmlText = "";
        try {
            Document doc = new Document(in);
            HtmlSaveOptions opts = new HtmlSaveOptions(SaveFormat.HTML);
            opts.setExportXhtmlTransitional(true);
            opts.setExportImagesAsBase64(true);
            opts.setExportPageSetup(true);
            doc.save(htmlStream, opts);
            htmlText = new String(htmlStream.toByteArray(), StandardCharsets.UTF_8);
            htmlStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlText;
    }

    /**
     * word转换html
     *
     * @param wordPath word文件路径
     * @param htmlPath 生成的html路径
     */
    public static void wordToHtml(String wordPath, String htmlPath) {
        wordToObject(wordPath, htmlPath, "HTML");
    }

    /**
     * word输入流转换pdf流
     *
     * @param in word输入流
     */
    public static void wordToPdf(InputStream in, OutputStream out) {
        wordToObject(in, out, "PDF");
    }

    /**
     * word转换pdf
     *
     * @param wordPath word文件路径
     * @param pdfPath  生成的pdf路径
     */
    public static void wordToPdf(String wordPath, String pdfPath) {
        wordToObject(wordPath, pdfPath, "PDF");
    }

    /**
     * word输入流转换docx流
     *
     * @param in word输入流
     */
    public static void wordToDocx(InputStream in, OutputStream out) {
        wordToObject(in, out, "DOCX");
    }

    /**
     * word转换docx
     *
     * @param wordPath word文件路径
     * @param docxPath 生成的pdf路径
     */
    public static void wordToDocx(String wordPath, String docxPath) {
        wordToObject(wordPath, docxPath, "DOCX");
    }

    /**
     * word输入流转换doc流
     *
     * @param in word输入流
     */
    public static void wordToDoc(InputStream in, OutputStream out) {
        wordToObject(in, out, "DOC");
    }

    /**
     * word转换doc
     *
     * @param wordPath word文件路径
     * @param docPath  生成的pdf路径
     */
    public static void wordToDoc(String wordPath, String docPath) {
        wordToObject(wordPath, docPath, "DOC");
    }

    /**
     * word输入流转换object流
     *
     * @param in  word输入流
     * @param out object输出流
     */
    public static void wordToObject(InputStream in, OutputStream out, String saveFormat) {
        try {
            Document doc = new Document(in);
            doc.save(out, SaveFormat.fromName(saveFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * word转换object
     *
     * @param wordPath   word文件路径
     * @param objectPath 生成的object路径
     * @param saveFormat 生成的pdf路径 SaveFormat
     */
    public static void wordToObject(String wordPath, String objectPath, String saveFormat) {
        try {
            Document doc = new Document(wordPath);
            File file = new File(objectPath);
            FileOutputStream os = new FileOutputStream(file);
            doc.save(os, SaveFormat.fromName(saveFormat));
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
