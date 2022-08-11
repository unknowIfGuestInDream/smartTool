package com.tlcsdm.poi;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * ExcelUtil.getReader方法只是将实体Excel文件转换为ExcelReader对象进行操作
 * 通过构造ExcelReader对象，指定被读取的Excel文件、流或工作簿，然后调用readXXX方法读取内容为指定格式。
 *
 * @author: 唐 亮
 * @date: 2022/8/11 19:49
 * @since: 1.0
 */
public class ExcelReaderTest {

    /**
     * 从文件中读取Excel为ExcelReader
     */
    @Test
    @Ignore
    public void ExcelReaderTest1() {
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("test.xlsx"));
    }

    /**
     * 从流中读取Excel为ExcelReader（比如从ClassPath中读取Excel文件）
     */
    @Test
    @Ignore
    public void ExcelReaderTest2() {
        ExcelReader reader = ExcelUtil.getReader(ResourceUtil.getStream("aaa.xlsx"));
    }

    /**
     * 读取指定的sheet
     */
    @Test
    @Ignore
    public void ExcelReaderTest3() {
        ExcelReader reader;
        //通过sheet编号获取
        reader = ExcelUtil.getReader(FileUtil.file("test.xlsx"), 0);
        //通过sheet名获取
        reader = ExcelUtil.getReader(FileUtil.file("test.xlsx"), "sheet1");
    }

    /**
     * 读取大数据量的Excel
     */
    @Test
    @Ignore
    public void ExcelReaderTest4() {
        ExcelUtil.readBySax("aaa.xlsx", 0, createRowHandler());
    }

    private RowHandler createRowHandler() {
        return (i, l, list) -> Console.log("[{}] [{}] {}", i, l, list);
    }

    /**
     * 读取Excel中所有行和列，都用列表表示
     */
    @Test
    @Ignore
    public void ExcelReaderTest5() {
        ExcelReader reader = ExcelUtil.getReader("d:/aaa.xlsx");
        List<List<Object>> readAll = reader.read();
    }

    /**
     * 读取为Map列表，默认第一行为标题行，Map中的key为标题，value为标题对应的单元格值。
     */
    @Test
    @Ignore
    public void ExcelReaderTest6() {
        ExcelReader reader = ExcelUtil.getReader("d:/aaa.xlsx");
        List<Map<String, Object>> readAll = reader.readAll();
    }

    /**
     * 读取为Bean列表，Bean中的字段名为标题，字段值为标题对应的单元格值。
     */
    @Test
    @Ignore
    public void ExcelReaderTest7() {
        ExcelReader reader = ExcelUtil.getReader("d:/aaa.xlsx");
//        List<Person> all = reader.readAll(Person.class);
    }

}
