package com.tlcsdm.psdcd;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.tlcsdm.common.BaseUtils;
import com.tlcsdm.diff.DiffHandleUtils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.poi.excel.BigExcelWriter;
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
	private final List<String> markSheetNames = List.of();
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
	private final String generateFilesParentPath = "C:\\runtime-smc.rh850.product\\src\\smc_gen\\general";

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

	/**
	 * 需要读取的excel部分单独放到一个sheet,然后转换文本，运用其他工具进行比对 考虑手动生成测试文件： 遍历将区域内容读取生成到文件中
	 */
	@Test
	public void generalTest1() {
		File testFile = FileUtil.file(parentDirectoryPath, excelName);
		Map<String, String> generateFileMap = new HashMap<>();
		for (String sheetName : sheetNames) {
			BigExcelWriter excelWriter = ExcelUtil.getBigWriter();
			logHandler("========================= Begin Reading " + sheetName + " =========================", 1);
			logHandler("Start reading sheet: " + sheetName, 1);
			ExcelReader reader = ExcelUtil.getReader(testFile, sheetName);
			String endCell = getEndCell(reader);
			logHandler("endCell: " + endCell, 1);
			CellLocation start = ExcelUtil.toLocation(startCell);
			CellLocation end = ExcelUtil.toLocation(endCell);
			int startX = start.getX();
			int startY = start.getY();
			int endX = end.getX();
			int endY = end.getY();
			String generateFileName = reader.getCell(generateFileCell).getStringCellValue();
			generateFileMap.put(sheetName, generateFileName);
			excelWriter.setSheet(sheetName);
			List<List<String>> list = new ArrayList<>(endY - startY + 1);
			for (int j = startY; j <= endY; j++) {
				List<String> l = new ArrayList<>(endX - startX + 1);
				boolean isDefine = false;
				String firstValue = BaseUtils.valueOf(CellUtil.getCellValue(reader.getCell(startX, j)));
				if ("#define".equals(firstValue) || "#ifndef".equals(firstValue)) {
					isDefine = true;
				}
				for (int j2 = startX; j2 <= endX; j2++) {
					String cellValue = BaseUtils.valueOf(CellUtil.getCellValue(reader.getCell(j2, j)));
					if (isDefine && j2 < endX) {
						cellValue += " ";
					}
					l.add(cellValue);
				}
				list.add(l);
			}
			excelWriter.write(list, false);
			File file = FileUtil.file(parentDirectoryPath + "\\result", sheetName + ".xlsx");
			excelWriter.flush(file);
			excelWriter.close();
			logHandler("========================= End Reading " + sheetName + " =========================", 1);
		}

		for (String sheetName : sheetNames) {
			ExcelReader reader = ExcelUtil.getReader(parentDirectoryPath + "\\result\\" + sheetName + ".xlsx",
					sheetName);
			String generateFileName = generateFileMap.get(sheetName);
			FileUtil.writeUtf8String(reader.readAsText(false).replaceAll("\\t", ""),
					FileUtil.file(parentDirectoryPath + "\\result\\files", generateFileName));
			reader.close();
			logHandler("========================= Begin Comparing " + generateFileName + " =========================",
					1);
			String generateFileParent = generateFilesParentPath.length() == 0 ? parentDirectoryPath
					: generateFilesParentPath;
			File generateFile = FileUtil.file(generateFileParent, generateFileName);
			if (FileUtil.exist(generateFile)) {
				List<String> diffString = DiffHandleUtils.diffString(
						parentDirectoryPath + "\\result\\files\\" + generateFileName,
						generateFileParent + "\\" + generateFileName);
				DiffHandleUtils.generateDiffHtml(diffString, parentDirectoryPath + "\\result\\" + sheetName + ".html");
			}
			logHandler("========================= End Comparing " + generateFileName + " =========================", 1);
		}
	}

	/**
	 * 日志处理 level: 0 debug 1 log 2 warning 3 error
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
