package com.tlcsdm.psdcd;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * UD文档CT部分生成
 * 
 * @author os_tangliang
 *
 */
public class UDExcelCTTest {

	// 结果信息输出路径
	private final String resultPath = "C:\\workspace\\test";
	// 结果文件名称(目前支持结果输出到excel)
	private final String resultFileName = "UDCT.xlsx";
	private List<String> devices = List.of("RH850U2A16-292pin", "RH850U2A16-373pin", "RH850U2A16-516pin",
			"RH850U2A8-292pin", "RH850U2A8-373pin", "RH850U2A6-144pin", "RH850U2A6-156pin", "RH850U2A6-176pin",
			"RH850U2A6-292pin");

	// TAUD TAUJ 0-15 0-3
	// change resource change device
	@BeforeClass
	public static void init() {

	}

	@Test
	public void uDExcelCTTest() {

	}

}
