package com.tlcsdm.psdcd;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

public class DTSTriggerSourceDocument {
	// excel的父级目录路径
	private final String parentDirectoryPath = "C:\\workspace\\test";
	// excel文件名称
	private final String excelName = "u2a_DTS_Transfer_request_Table.xlsx";
	// 模板文件名
	private String resultFileName = "DTSTriggerSourceDocument.xlsx";

	private final String sheetName = "DTS trigger";
	// 数据开始行
	private final int beginRowNum = 5;
	// 数据结束行
	private final int endRowNum = 132;
	// List初始容量
	private final int initialCapacity = 647;
	// group0-3 value所在列
	private final String group0ValueLine = "C";
	private final String group1ValueLine = "E";
	private final String group2ValueLine = "G";
	private final String group3ValueLine = "I";

	private final String number = "2-02-001-";
	private final String control = "ComboBox";
	private final String labelEn = "Trigger resource";
	private final String labelJa = "起動要因";
	private final String condition = "Always enable";
	// 数据开始写入行
	private final int beginWriteRowNum = 3;
	// 数据开始写入行
	private final List<ArrayList<String>> groupLines = new ArrayList<>();

	// trigger factor信息
	private List<Map<Integer, String>> triggerFactorList = new ArrayList<>(128);
	private List<Integer> triggerFactorRowNumList = new ArrayList<>(128);
	private List<List<Map<Integer, String>>> conditionList = new ArrayList<>(128);

	@Before
	public void init() {
		// initGroupLine("N", "O", "P", "Q"); // U2A16-516
		initGroupLine("R", "S", "T", "U"); // U2A16-373
		// initGroupLine("V", "W", "X", "Y"); // U2A16-292
		initGroupLine("Z", "AA", "AB", "AC"); // U2A8-373
		// initGroupLine("AD", "AE", "AF", "AG"); // U2A8-292
		initGroupLine("AH", "AI", "AJ", "AK"); // U2A6-292
		initGroupLine("AL", "AM", "AN", "AO"); // U2A6-176
		initGroupLine("AP", "AQ", "AR", "AS"); // U2A6-156
		initGroupLine("AT", "AU", "AV", "AW"); // U2A6-144
	}

	@AfterClass
	public static void afterAll() {

	}

	@Test
	public void dealData() {
		readData();
		BigExcelWriter excelWriter = ExcelUtil.getBigWriter(FileUtil.file(parentDirectoryPath + "\\" + resultFileName));
		int line = beginWriteRowNum;
		for (int i = 0; i < 128; i++) {
			// excelWriter.getCell("B" + line).setCellValue(number + String.format("%03d",
			// i));
			excelWriter.writeCellValue("B" + line, number + String.format("%03d", i));
			int rowNum = triggerFactorRowNumList.get(i);
			for (int j = 0; j < rowNum; j++) {
				if (j == 0) {
					excelWriter.writeCellValue("C" + line, control);
					excelWriter.writeCellValue("D" + line, rowNum);
					excelWriter.writeCellValue("E" + line, labelEn);
					excelWriter.writeCellValue("F" + line, labelJa);
					excelWriter.writeCellValue("G" + line, condition);
					excelWriter.writeCellValue("S" + line, "");
					excelWriter.writeCellValue("T" + line, condition);
//					excelWriter.getCell("C" + line).setCellValue(control);
//					excelWriter.getCell("D" + line).setCellValue(rowNum);
//					excelWriter.getCell("E" + line).setCellValue(labelEn);
//					excelWriter.getCell("F" + line).setCellValue(labelJa);
//					excelWriter.getCell("G" + line).setCellValue(condition);
//					excelWriter.getCell("S" + line).setCellValue(""); // initial value
//					excelWriter.getCell("T" + line).setCellValue(condition);
				}
//				excelWriter.getCell("Q" + line).setCellValue("Group 0 : xxx");
//				excelWriter.getCell("R" + line).setCellValue("Group 0 : xxx");
				excelWriter.writeCellValue("Q" + line, "Group 0 : xxx");
				excelWriter.writeCellValue("R" + line, "Group 0 : xxx");
				// BJ DMATRGSEL.DTSSEL0.UINT32 &= /8的结果
				// BK Config.c
				// BL R_Config_DTS%s_Create
				// BM _DTSn0_TRANSFER_REQUEST_GROUP_CLEAR /8的余数
				// BN DMATRGSEL.DTSSEL0.UINT32 |=
				// BO Config.c
				// BP R_Config_DTS%s_Create
				// BQ _DTSn0_TRANSFER_REQUEST_GROUP_0 /8的余数 && group

				// CL

			}

			line += rowNum;
		}
		File file = FileUtil.newFile(parentDirectoryPath + "\\triggerSource\\" + resultFileName);
		excelWriter.flush(file);
		excelWriter.close();
	}

	// 读取数据
	private void readData() {
		ExcelReader reader = ExcelUtil.getReader(FileUtil.file(parentDirectoryPath, excelName), sheetName);
		File file = FileUtil.newFile(parentDirectoryPath + "\\triggerSource\\" + resultFileName);
		if (file.exists()) {
			FileUtil.del(file);
		}
		for (int i = beginRowNum; i <= endRowNum; i++) {
			Map<Integer, String> map = new HashMap<>(8);
			int rowNum = 4;
			String group0Value = reader.getCell(group0ValueLine + i).getStringCellValue();
			String group1Value = reader.getCell(group1ValueLine + i).getStringCellValue();
			String group2Value = reader.getCell(group2ValueLine + i).getStringCellValue();
			String group3Value = reader.getCell(group3ValueLine + i).getStringCellValue();
			if ("Reserved".equals(group0Value)) {
				rowNum--;
			}
			if ("Reserved".equals(group1Value)) {
				rowNum--;
			}
			if ("Reserved".equals(group2Value)) {
				rowNum--;
			}
			if ("Reserved".equals(group3Value)) {
				rowNum--;
			}
			triggerFactorRowNumList.add(rowNum);
			map.put(0, group0Value);
			map.put(1, group1Value);
			map.put(2, group2Value);
			map.put(3, group3Value);
			triggerFactorList.add(map);
		}

		for (ArrayList<String> arrayList : groupLines) {
			List<Map<Integer, String>> list = new ArrayList<>();
			for (int i = beginRowNum; i <= endRowNum; i++) {
				Map<Integer, String> map = new HashMap<>(16);
				map.put(0, reader.getCell(arrayList.get(0) + i).getStringCellValue());
				map.put(1, reader.getCell(arrayList.get(1) + i).getStringCellValue());
				map.put(2, reader.getCell(arrayList.get(2) + i).getStringCellValue());
				map.put(3, reader.getCell(arrayList.get(3) + i).getStringCellValue());
				list.add(map);
			}
			conditionList.add(list);
		}

		reader.close();
	}

	// 初始化变量
	private void initGroupLine(String group0Line, String group1Line, String group2Line, String group3Line) {
		List<String> list = new ArrayList<>();
		list.add(group0Line);
		list.add(group1Line);
		list.add(group2Line);
		list.add(group3Line);
		groupLines.add((ArrayList<String>) list);
	}
}
