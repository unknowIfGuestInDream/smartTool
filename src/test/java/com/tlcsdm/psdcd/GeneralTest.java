package com.tlcsdm.psdcd;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.cell.CellLocation;
import cn.hutool.poi.excel.cell.CellUtil;
import com.tlcsdm.common.BaseUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author: 唐 亮
 * @date: 2022/8/24 20:18
 * @since: 1.0
 */
public class GeneralTest {

    /*
     * 需要定义的配置
     */
    // 忽略的sheet
    private final List<String> ignoreSheetNames = List.of("Overview", "Summary", "Sample-CT");
    // 如果markSheetNames不为空，那么只会读取当前对象内的sheet, 忽略ignoreSheetNames配置
    private final List<String> markSheetNames = List.of("DMA");
    // excel的父级目录路径
    private final String parentDirectoryPath = "C:\\workspace\\test";
    // excel文件名称
    private final String excelName = "TestSpec_General_RH850F1KH-D8.xlsx";
    // excel中待读取内容的左上角Cell
    private final String startCell = "C19";
    // excel中待读取内容的右下角Cell列名, endCell = endCellColumn + 计算获取的最后一行行号 - 2
    private final String endCellColumn = "F";
    // excel中Generate Files值对应的Cell
    private final String generateFileCell = "C15";
    // Generate Files的父级路径, 如果为空，则默认为parentDirectoryPath值
    private final String generateFilesParentPath = "C:\\workspace";

    /*
     * 待初始化对象
     */
    // 需要读取的sheetName集合
    private List<String> sheetNames;

    @Before
    public void init() {
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(parentDirectoryPath, excelName));
        sheetNames = reader.getSheetNames().stream()
                .filter(s -> (markSheetNames.size() == 0 && !ignoreSheetNames.contains(s))
                        || (markSheetNames.size() != 0 && markSheetNames.contains(s)))
                .collect(Collectors.toList());
    }

    @Test
    public void getSheetsTest() {
        for (int i = 0; i < sheetNames.size(); i++) {
            logHandler("Start reading sheet: " + sheetNames.get(i), 1);
            ExcelReader reader = ExcelUtil.getReader(FileUtil.file(parentDirectoryPath, excelName), sheetNames.get(i));
            String endCell = getEndCell(reader);
            logHandler("endCell: " + endCell, 1);
            CellLocation start = ExcelUtil.toLocation(startCell);
            CellLocation end = ExcelUtil.toLocation(endCell);
            int startX = start.getX();
            int startY = start.getY();
            int endX = end.getX();
            int endY = end.getY();
            String generateFileName = reader.getCell(generateFileCell).getStringCellValue();
            logHandler("generateFileName: " + generateFileName, 1);
            List<List<String>> list = new ArrayList<>(endY - startY + 1);
            for (int j = startY; j <= endY; j++) {
                List<String> l = new ArrayList<>(endX - startX + 1);
                for (int j2 = startX; j2 <= endX; j2++) {
                    l.add(BaseUtils.valueOf(CellUtil.getCellValue(reader.getCell(j2, j))));
                }
                list.add(l);
            }
            //待比对文件数据提取
            String generateFileParent = generateFilesParentPath.length() == 0 ? parentDirectoryPath : generateFilesParentPath;
            File generateFile = FileUtil.file(generateFileParent, generateFileName);
            if (FileUtil.exist(generateFile)) {
                List<String> generateFileData = FileUtil.readUtf8Lines(generateFile);
                List<String> targetData = new ArrayList<>(generateFileData.size());
                for (int j = 0; j < generateFileData.size(); j++) {
                    //暂不考虑第一行超过120字符换行的问题，因为第一行通常是自动生成的信息。
                    if (j == 0) {
                        targetData.add(generateFileData.get(0));
                    }
                    //处理模板中超过120字符而换行的数据行 考虑之前数据修改的问题。当前判定逻辑是5个空格开头的进行是否为换行的判断。
                    if (generateFileData.get(i).startsWith("     ") && StrUtil.isBlank(generateFileData.get(i))) {
//                        StrUtil.trimStart()
//                        StrUtil.trimEnd()
//                        StrUtil.trim();
                    }
                }
                //两个list比对大小 如果大小不同那么报warning信息

                //比对时最好去除空白进行比对

                //FileUtil.writeUtf8Lines(list, FileUtil.file(generateFilesParentPath.length() == 0 ? parentDirectoryPath : generateFilesParentPath, generateFileName));
                //抽取文件每一行 对五个空格开始的行进行转换过滤，形成一个list
                //list逐个index.of 来进行校验
                //CompareUtil.compare()
                //结果处理 保留比对信息 行数，暂时不考虑ok等结果
                //考虑Console.table展示
//                ConsoleTable t = ConsoleTable.create();
//                t.addHeader("姓名", "年龄");
//                t.addBody("张三", "15");
//                t.addBody("李四", "29");
//                t.addBody("王二麻子", "37");
//                t.print();
            } else {
                logHandler(generateFileName + " not found, please check that the file path is correct:\n" + generateFileParent + File.separator + generateFileName, 3);
            }
            logHandler("========================= END =========================", 1);
        }
    }

    @Test
    public void generalTest1() {
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(parentDirectoryPath, excelName));
        CellLocation start = ExcelUtil.toLocation(startCell);
        CellLocation end = ExcelUtil.toLocation("F634");
        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();
        List<String> list = new ArrayList<>();
        for (int i = startY; i <= endY; i++) {
            StringJoiner sj = new StringJoiner(" ");
            for (int j = startX; j <= endX; j++) {
                sj.add(BaseUtils.valueOf(CellUtil.getCellValue(reader.getCell(j, i))));
            }
            list.add(sj.toString());
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    /**
     * 读取指定行列的excel内容
     */
    @Test
    @Ignore
    public void workExcelContentTest1() {
        ExcelReader reader = ExcelUtil
                .getReader(FileUtil.file("C:\\workspace\\test\\TestSpec_General_RH850F1KH-D8.xlsx"), "DMA");
        FileUtil.writeString(reader.readAsText(false), "C:\\workspace\\test\\text.c", CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 读取指定行列的excel内容
     */
    @Test
    @Ignore
    public void workExcelContentTest2() {
        ExcelReader reader = ExcelUtil
                .getReader(FileUtil.file("C:\\workspace\\test\\TestSpec_General_RH850F1KH-D8.xlsx"), "DMA");
        List<Object> list = reader.readColumn(2, 18, 629);
        FileUtil.writeUtf8Lines(list, "C:\\workspace\\test\\text.c");
    }

    /**
     * 日志处理
     * level:
     * 0 debug
     * 1 log
     * 2 warning
     * 3 error
     */
    private void logHandler(String message, int level) {
        switch (level) {
            case 1:
                Console.log(message);
                break;
            case 2:
                Console.log("Warning: {}", message);
                break;
            case 3:
                Console.error(message);
                break;
            case 0:
            default:
                break;
        }

    }

    /**
     * 获取EndCell值
     * <p>
     * 为End Sheet 所在行数 -2
     */
    private String getEndCell(ExcelReader reader) {
        return endCellColumn + (reader.getRowCount() - 2);
    }
}
