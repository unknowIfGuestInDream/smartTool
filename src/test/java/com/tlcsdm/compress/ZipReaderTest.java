package com.tlcsdm.compress;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ZipUtil;

public class ZipReaderTest {

	@Test
	@Ignore
	public void unzipTest() {
		File unzip = ZipUtil.unzip("d:/java.zip", "d:/test/java");
		Console.log(unzip);
	}
}
