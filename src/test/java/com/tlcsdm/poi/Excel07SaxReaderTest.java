package com.tlcsdm.poi;

import cn.hutool.core.lang.Console;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Excel07SaxReader只支持Excel2007格式的Sax读取。
 *
 * @author: 唐 亮
 * @date: 2022/8/11 22:00
 * @since: 1.0
 */
public class Excel07SaxReaderTest {

    /**
     * ExcelUtil快速读取
     */
    @Test
    @Ignore
    public void Excel07SaxReader1() {
        ExcelUtil.readBySax("aaa.xls", 1, createRowHandler());
    }

    /**
     * 构建对象读取
     * reader方法的第二个参数是sheet的序号，-1表示读取所有sheet，0表示第一个sheet
     */
    @Test
    @Ignore
    public void Excel07SaxReader2() {
        Excel07SaxReader reader = new Excel07SaxReader(createRowHandler());
        reader.read("d:/text.xlsx", 0);
    }

    /**
     * 首先我们实现一下RowHandler接口，这个接口是Sax读取的核心，
     * 通过实现handle方法编写我们要对每行数据的操作方式
     * （比如按照行入库，入List或者写出到文件等），在此我们只是在控制台打印。
     */
    private RowHandler createRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                Console.log("[{}] [{}] {}", sheetIndex, rowIndex, rowlist);
            }
        };
    }
}
