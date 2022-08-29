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

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.ConsoleTable;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
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

	// https://blog.csdn.net/qq_33697094/article/details/121681707

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
	private final String generateFilesParentPath = "C:\\workspace\\test";

	/*
	 * 待初始化对象
	 */
	// 需要读取的sheetName集合
	private List<String> sheetNames;
	// 比对结果
	private List<Map<String, String>> result = new ArrayList<>();

	@Before
	public void init() {
		ExcelReader reader = ExcelUtil.getReader(FileUtil.file(parentDirectoryPath, excelName));
		sheetNames = reader.getSheetNames().stream()
				.filter(s -> (markSheetNames.size() == 0 && !ignoreSheetNames.contains(s))
						|| (markSheetNames.size() != 0 && markSheetNames.contains(s)))
				.collect(Collectors.toList());
	}

	/**
	 * 读取文档数据和生成的文件数据 判断文档数据每一个单元格是否在生成文件数据中
	 */
	@Test
	public void generalTest1() {
		for (String sheetName : sheetNames) {
			logHandler("========================= BEGIN " + sheetName + " =========================", 1);
			logHandler("Start reading sheet: " + sheetName, 1);
			ExcelReader reader = ExcelUtil.getReader(FileUtil.file(parentDirectoryPath, excelName), sheetName);
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
					String cellValue;
					// 首列的开头保留空格
					if (j == startY) {
						cellValue = StrUtil.trimEnd(BaseUtils.valueOf(CellUtil.getCellValue(reader.getCell(j2, j))));
					} else {
						cellValue = StrUtil.trim(BaseUtils.valueOf(CellUtil.getCellValue(reader.getCell(j2, j))));
					}
					l.add(cellValue);
				}
				list.add(l);
			}
			// 待比对文件数据抽取
			String generateFileParent = generateFilesParentPath.length() == 0 ? parentDirectoryPath
					: generateFilesParentPath;
			File generateFile = FileUtil.file(generateFileParent, generateFileName);
			if (FileUtil.exist(generateFile)) {
				List<String> generateFileData = FileUtil.readUtf8Lines(generateFile);
				// 生成文件数据清洗
				List<String> targetData = new ArrayList<>(generateFileData.size());
				for (int j = 0; j < generateFileData.size(); j++) {
					// 暂不考虑第一行超过120字符换行的问题，因为第一行通常是自动生成的信息。
					// 处理模板中超过120字符而换行的数据行 考虑之前数据修改的问题。当前判定逻辑是10个空格开头的条件以及当前行是否为空白来判断是否为上一行数据的换行。
					// 且不会有连换两行的情况
					if (j > 0 && generateFileData.get(j).startsWith("          ")
							&& StrUtil.isEmpty(generateFileData.get(j))) {
						targetData.set(targetData.size() - 1,
								targetData.get(targetData.size() - 1) + " " + StrUtil.trim(generateFileData.get(j)));
						continue;
					}
					targetData.add(StrUtil.trimEnd(generateFileData.get(j)));
				}
				// 两个list比对大小 如果大小不同那么报warning信息
				if (targetData.size() != list.size()) {
					logHandler(
							"The number of lines of document data and generated document data are not equal in component "
									+ sheetName,
							2);
				}
				// 数据比对
				// list逐行校验, 以文档数据遍历, 判断每个单元格数据是否在当前行
				for (int j = 0; j < list.size(); j++) {
					List<String> l = list.get(j);
					int startIndex = 0;
					for (String s : l) {
						if (StrUtil.isEmpty(s)) {
							continue;
						}
						startIndex = targetData.get(j).indexOf(s, startIndex);
						// 不存在时
						if (startIndex == -1) {
							Map<String, String> map = new HashMap<>(4);
							map.put("line", String.valueOf(j + 1));
							result.add(map);
							break;
						}
					}
				}
				// 结果信息处理
				if (result.size() == 0) {
					logHandler(sheetName + " matching complete, no NG", 1);
				} else {
					ConsoleTable t = ConsoleTable.create();
					t.addHeader("component", "line", "result");
					for (Map<String, String> stringStringMap : result) {
						t.addBody(sheetName, stringStringMap.get("line"), "NG");
					}
					FileUtil.writeUtf8String(t.toString(), FileUtil.file(parentDirectoryPath,
							excelName.substring(0, excelName.lastIndexOf(".")) + ".txt"));
				}
			} else {
				logHandler(generateFileName + " not found, please check that the file path is correct:\n"
						+ generateFileParent + File.separator + generateFileName, 3);
			}
			logHandler("========================= END " + sheetName + " =========================", 1);
		}
	}

	/**
	 * 需要读取的excel部分单独放到一个sheet,然后转换文本，运用其他工具进行比对 考虑手动生成测试文件： 遍历将区域内容读取生成到文件中
	 */
	@Test
	public void generalTest2() {
		String sheetName = "DMA";
		String fileType = "h";
		ExcelReader reader = ExcelUtil.getReader(FileUtil.file(parentDirectoryPath, excelName), sheetName);
		FileUtil.writeString(reader.readAsText(false), FileUtil.file(parentDirectoryPath, sheetName + "." + fileType),
				CharsetUtil.CHARSET_UTF_8);
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
