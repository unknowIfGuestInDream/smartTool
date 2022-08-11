package com.tlcsdm.poi;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Hutool将Excel写出封装为ExcelWriter，原理为包装了Workbook对象，
 * 每次调用merge（合并单元格）或者write（写出数据）方法后只是将数据写入到Workbook，
 * 并不写出文件，只有调用flush或者close方法后才会真正写出文件。
 * <p>
 * 由于机制原因，在写出结束后需要关闭ExcelWriter对象，调用close方法即可关闭，
 * 此时才会释放Workbook对象资源，否则带有数据的Workbook一直会常驻内存。
 *
 * @author: 唐 亮
 * @date: 2022/8/11 22:03
 * @since: 1.0
 */
public class ExcelWriterTest {

    /**
     * 将行列对象写出到Excel
     */
    @Test
    @Ignore
    public void ExcelWriterTest1() {
        //数据
        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");

        List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/writeTest.xlsx");
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        //跳过当前行，既第一行，非必须，在此演示用
        writer.passCurrentRow();

        //合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "测试标题");
        //一次性写出内容，强制输出标题
        writer.write(rows, true);
        //关闭writer，释放内存
        writer.close();
    }

    /**
     * 写出Map数据
     */
    @Test
    @Ignore
    public void ExcelWriterTest2() {
        //数据
        Map<String, Object> row1 = new LinkedHashMap<>();
        row1.put("姓名", "张三");
        row1.put("年龄", 23);
        row1.put("成绩", 88.32);
        row1.put("是否合格", true);
        row1.put("考试日期", DateUtil.date());

        Map<String, Object> row2 = new LinkedHashMap<>();
        row2.put("姓名", "李四");
        row2.put("年龄", 33);
        row2.put("成绩", 59.50);
        row2.put("是否合格", false);
        row2.put("考试日期", DateUtil.date());

        ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(row1, row2);

        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/writeMapTest.xlsx");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "一班成绩单");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        // 关闭writer，释放内存
        writer.close();
    }

    /**
     * 写出Bean数据
     */
    @Test
    @Ignore
    public void ExcelWriterTest3() {
        //数据
//        TestBean bean1 = new TestBean();
//        bean1.setName("张三");
//        bean1.setAge(22);
//        bean1.setPass(true);
//        bean1.setScore(66.30);
//        bean1.setExamDate(DateUtil.date());
//
//        TestBean bean2 = new TestBean();
//        bean2.setName("李四");
//        bean2.setAge(28);
//        bean2.setPass(false);
//        bean2.setScore(38.50);
//        bean2.setExamDate(DateUtil.date());
//
//        List<TestBean> rows = CollUtil.newArrayList(bean1, bean2);

        // 通过工具类创建writer
//        ExcelWriter writer = ExcelUtil.getWriter("d:/writeBeanTest.xlsx");
//        // 合并单元格后的标题行，使用默认标题样式
//        writer.merge(4, "一班成绩单");
//        // 一次性写出内容，使用默认样式，强制输出标题
//        writer.write(rows, true);
//        // 关闭writer，释放内存
//        writer.close();
    }

    /**
     * 自定义Bean的key别名（排序标题）
     * <p>
     * 在写出Bean的时候，我们可以调用ExcelWriter对象的addHeaderAlias方法自定义Bean中key的别名，
     * 这样就可以写出自定义标题了（例如中文）。
     * <p>
     * 提示: 默认情况下Excel中写出Bean字段不能保证顺序，
     * 此时可以使用addHeaderAlias方法设置标题别名，Bean的写出顺序就会按照标题别名的加入顺序排序。
     * 如果不需要设置标题但是想要排序字段，请调用writer.addHeaderAlias("age", "age")
     * 设置一个相同的别名就可以不更换标题。 未设置标题别名的字段不参与排序，会默认排在前面。
     */
    @Test
    @Ignore
    public void ExcelWriterTest4() {
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/writeBeanTest.xlsx");

        //自定义标题别名
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("score", "分数");
        writer.addHeaderAlias("isPass", "是否通过");
        writer.addHeaderAlias("examDate", "考试时间");

        // 默认的，未添加alias的属性也会写出，如果想只写出加了别名的字段，可以调用此方法排除之
        writer.setOnlyAlias(true);

        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(4, "一班成绩单");
        // 一次性写出内容，使用默认样式，强制输出标题
        //writer.write(rows, true);
        // 关闭writer，释放内存
        writer.close();
    }

    /**
     * 写出到流
     */
    @Test
    @Ignore
    public void ExcelWriterTest5() {
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        //创建xlsx格式的
        //ExcelWriter writer = ExcelUtil.getWriter(true);
        // 一次性写出内容，使用默认样式，强制输出标题
        //writer.write(rows, true);
        //out为OutputStream，需要写出到的目标流
        //writer.flush(out);
        // 关闭writer，释放内存
        writer.close();
    }

    /**
     * 写出到客户端下载（写出到Servlet）
     */
    @Test
    @Ignore
    public void ExcelWriterTest6() {
        //1. 写出xls
        // 通过工具类创建writer，默认创建xls格式
//        ExcelWriter writer = ExcelUtil.getWriter();
//        // 一次性写出内容，使用默认样式，强制输出标题
//        writer.write(rows, true);
//        //out为OutputStream，需要写出到的目标流
//
//        //response为HttpServletResponse对象
//        response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
//        response.setHeader("Content-Disposition","attachment;filename=test.xls");
//        ServletOutputStream out=response.getOutputStream();
//
//        writer.flush(out, true);
//        // 关闭writer，释放内存
//        writer.close();
//        //此处记得关闭输出Servlet流
//        IoUtil.close(out);

        //2. 写出xlsx
//        ExcelWriter writer = ExcelUtil.getWriter(true);
//        writer.write(rows, true);
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        response.setHeader("Content-Disposition","attachment;filename=test.xlsx");
//        writer.flush(out, true);
//        writer.close();
//        IoUtil.close(out);
    }

    /**
     * 自定义Excel
     */
    @Test
    @Ignore
    public void ExcelWriterTest7() {
        //1.设置单元格背景色
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 定义单元格背景色
        StyleSet style = writer.getStyleSet();
        // 第二个参数表示是否也设置头部单元格背景
        style.setBackgroundColor(IndexedColors.RED, false);

        //2.自定义字体
        //设置内容字体
        Font font = writer.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        font.setItalic(true);
        //第二个参数表示是否忽略头部样式
        writer.getStyleSet().setFont(font, true);

        //3.写出多个sheet
        //初始化时定义表名
        ExcelWriter writer1 = new ExcelWriter("d:/aaa.xls", "表1");
        //切换sheet，此时从第0行开始写
        writer1.setSheet("表2");
        writer1.setSheet("表3");

        //4.更详细的定义样式
//        在Excel中，由于样式对象个数有限制，因此Hutool根据样式种类分为4个样式对象，
//        使相同类型的单元格可以共享样式对象。样式按照类别存在于StyleSet中，其中包括：
//        头部样式 headCellStyle
//        普通单元格样式 cellStyle
//        数字单元格样式 cellStyleForNumber
//        日期单元格样式 cellStyleForDate

//        其中cellStyleForNumber cellStyleForDate用于控制数字和日期的显示方式。
//        因此我们可以使用以下方式获取CellStyle对象自定义指定种类的样式：
//        StyleSet style = writer.getStyleSet();
//        CellStyle cellStyle = style.getHeadCellStyle();

        //5.自定义写出的值
//        你可以实现CellSetter接口来自定义写出到单元格的值，此接口只有一个方法：setValue(Cell cell)，
//        通过暴露Cell对象使得用户可以自定义输出单元格内容，甚至是样式。
        // 此处使用lambda自定义写出内容
//        List<Object> row = ListUtil.of((CellSetter) cell -> cell.setCellValue("自定义内容"));
//
//        ExcelWriter writer = ExcelUtil.getWriter("/test/test.xlsx");
//        writer.writeRow(row);
//        writer.close();

        //注意 某些特殊的字符出会导致Excel自动转义，如_xXXXX_这种格式的字符串会被当做unicode转义符，
        // 会被反转义。 此时可以使用Hutool内置的EscapeStrCellSetter
//        List<Object> row = ListUtil.of(new EscapeStrCellSetter("_x5116_"));
//
//        ExcelWriter writer = ExcelUtil.getWriter("/test/test.xlsx");
//        writer.writeRow(row);
//        writer.close();

    }
}
