package com.tlcsdm.psdcd;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.cell.CellLocation;
import cn.hutool.poi.excel.cell.CellUtil;

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
	private final String parentDirectoryUrl = "C:\\workspace\\test";
	// excel文件名称
	private final String excelName = "TestSpec_General_RH850F1KH-D8.xlsx";
	// excel中待读取内容的左上角Cell
	private final String startCell = "C19";
	// excel中待读取内容的右下角Cell列名, endCell = endCellColumn + 计算获取的最后一行行号 - 2
	private final String endCellColumn = "F";
	// excel中Generate Files值对应的Cell
	private final String generateFileCell = "C15";

	/*
	 * 待初始化对象
	 */
	// 需要读取的sheetName集合
	private List<String> sheetNames;

	@Before
	public void init() {
		ExcelReader reader = ExcelUtil.getReader(FileUtil.file(parentDirectoryUrl, excelName));
		sheetNames = reader.getSheetNames().stream()
				.filter(s -> (markSheetNames.size() == 0 && !ignoreSheetNames.contains(s))
						|| (markSheetNames.size() != 0 && markSheetNames.contains(s)))
				.collect(Collectors.toList());
	}

	@Test
	public void getSheetsTest() {
		for (int i = 0; i < sheetNames.size(); i++) {
			logHandler("开始读取 sheet: " + sheetNames.get(i));
			ExcelReader reader = ExcelUtil.getReader(FileUtil.file(parentDirectoryUrl, excelName), sheetNames.get(i));
			String endCell = getEndCell(reader);
			logHandler("endCell: " + endCell);
			CellLocation start = ExcelUtil.toLocation(startCell);
			CellLocation end = ExcelUtil.toLocation(endCell);
			int startX = start.getX();
			int startY = start.getY();
			int endX = end.getX();
			int endY = end.getY();
			String generateFile = reader.getCell(generateFileCell).getStringCellValue();
			logHandler("generateFile: " + generateFile);
			List<List<String>> list = new ArrayList<>();
			for (int j = startY; j < endY; j++) {
				List<String> l = new ArrayList<>();
				for (int j2 = startX; j2 < endX; j2++) {
					l.add(valueOf(CellUtil.getCellValue(reader.getCell(j2, j))));
				}
				list.add(l);
			}

			FileUtil.writeUtf8Lines(list, FileUtil.file(parentDirectoryUrl, generateFile));
			logHandler("========================================");
		}
	}

	@Test
	public void generalTest1() {
		ExcelReader reader = ExcelUtil.getReader(FileUtil.file(parentDirectoryUrl, excelName));
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
				sj.add(valueOf(CellUtil.getCellValue(reader.getCell(j, i))));
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
	public void workExcelContentTest1() {
		ExcelReader reader = ExcelUtil
				.getReader(FileUtil.file("C:\\workspace\\test\\TestSpec_General_RH850F1KH-D8.xlsx"), "DMA");
		FileUtil.writeString(reader.readAsText(false), "C:\\workspace\\test\\text.c", CharsetUtil.CHARSET_UTF_8);
	}

	/**
	 * 读取指定行列的excel内容
	 */
	@Test
	public void workExcelContentTest2() {
		ExcelReader reader = ExcelUtil
				.getReader(FileUtil.file("C:\\workspace\\test\\TestSpec_General_RH850F1KH-D8.xlsx"), "DMA");
		List<Object> list = reader.readColumn(2, 18, 629);
		FileUtil.writeUtf8Lines(list, "C:\\workspace\\test\\text.c");
	}

	/**
	 * 日志处理
	 */
	private void logHandler(String message) {
		Console.log(message);
	}

	/**
	 * 获取EndCell值
	 */
	private String getEndCell(ExcelReader reader) {
		return endCellColumn + (reader.getRowCount() - 2);
	}

	private String valueOf(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}
}
