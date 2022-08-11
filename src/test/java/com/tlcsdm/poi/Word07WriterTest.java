package com.tlcsdm.poi;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.word.Word07Writer;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 针对Word（主要是docx格式）进行封装，实现简单的Word文件创建。
 * <p>
 * 将POI中Word生成封装为Word07Writer, 通过分段写出，实现word生成。
 *
 * @author: 唐 亮
 * @date: 2022/8/11 22:24
 * @since: 1.0
 */
public class Word07WriterTest {

    /**
     * 写出到流
     */
    @Test
    @Ignore
    public void Word07WriterTest() {
        Word07Writer writer = new Word07Writer();
        // 添加段落（标题）
        writer.addText(new Font("方正小标宋简体", Font.PLAIN, 22), "我是第一部分", "我是第二部分");
        // 添加段落（正文）
        writer.addText(new Font("宋体", Font.PLAIN, 22), "我是正文第一部分", "我是正文第二部分");
        // 写出到文件
        writer.flush(FileUtil.file("e:/wordWrite.docx"));
        // 关闭
        writer.close();
    }

    @Test
    @Ignore
    public void writePicTest() {
        Word07Writer writer = new Word07Writer();
        writer.addPicture(new File("d:\\test\\qrcodeCustom.jpg"), 100, 200);
        // 写出到文件
        writer.flush(FileUtil.file("d:/test/writePic.docx"));
        // 关闭
        writer.close();
    }

    @Test
    @Ignore
    public void writeTableTest() {
        final Word07Writer writer = new Word07Writer();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("姓名", "张三");
        map.put("年龄", "23");
        map.put("成绩", 88.32);
        map.put("是否合格", true);

        writer.addTable(CollUtil.newArrayList(map));
        writer.flush(FileUtil.file("d:/test/test.docx"));
    }

    @Test
    @Ignore
    public void writeMapAsTableTest() {
        Word07Writer writer = new Word07Writer();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("姓名", "张三");
        data.put("年龄", 23);
        data.put("成绩", 80.5);
        data.put("是否合格", true);
        data.put("考试日期", DateUtil.date());

        Map<String, Object> data2 = new LinkedHashMap<>();
        data2.put("姓名", "李四");
        data2.put("年龄", 4);
        data2.put("成绩", 59);
        data2.put("是否合格", false);
        data2.put("考试日期", DateUtil.date());

        ArrayList<Map<String, Object>> mapArrayList = CollUtil.newArrayList(data, data2);

        // 添加段落（标题）
        writer.addText(new Font("方正小标宋简体", Font.PLAIN, 22), "我是第一部分");
        // 添加段落（正文）
        writer.addText(new Font("宋体", Font.PLAIN, 13), "我是正文第一部分");
        writer.addTable(mapArrayList);
        // 写出到文件
        writer.flush(FileUtil.file("d:/test/a.docx"));
        // 关闭
        writer.close();
    }

    @Test
    public void overflowTest() {
        final Word07Writer word07Writer = new Word07Writer();
        final java.util.List<Object> list = ListUtil.list(false);
        final List<Object> list2 = ListUtil.list(false);
        list.add("溢出测试");
        list2.add(list);
        word07Writer.addTable(list);
        word07Writer.close();
    }
}
