package com.tlcsdm.psdcd;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.cell.CellLocation;
import cn.hutool.poi.excel.cell.CellUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author: 唐 亮
 * @date: 2022/8/24 20:18
 * @since: 1.0
 */
public class GeneralTest {

    @BeforeClass
    public static void init() {

    }

    @Test
    public void generalTest1() {
        ExcelReader reader = ExcelUtil.getReader("E:\\testPlace\\test.xlsx");
        CellLocation start = ExcelUtil.toLocation("B5");
        CellLocation end = ExcelUtil.toLocation("D9");
        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();
        List<String> list = new ArrayList<>();
        for (int i = startY; i <= endY; i++) {
            StringJoiner sj = new StringJoiner(" ");
            for (int j = startX; j <= endX; j++) {
                sj.add(valueOf(CellUtil.getCellValue(reader.getCell(j, i))));
            }
            list.add(sj.toString());
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    private String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
}
