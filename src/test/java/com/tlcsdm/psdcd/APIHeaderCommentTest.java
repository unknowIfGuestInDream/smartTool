package com.tlcsdm.psdcd;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

/**
 *
 * @date: 2022/8/24 17:25
 * @since: 1.0
 */
public class APIHeaderCommentTest {
	/**
	 * 全局初始化
	 */
	@BeforeClass
	public static void init() {
	}

	@Test
	public void APIHeaderCommentTest1() {
		ExcelReader reader = ExcelUtil
				.getReader(FileUtil.file("C:\\workspace\\test\\TestSpec_General_RH850F1KH-D8.xlsx"), "DMA");
		FileUtil.writeString(reader.readAsText(false), "C:\\workspace\\test\\text.c", CharsetUtil.CHARSET_UTF_8);
	}

}
