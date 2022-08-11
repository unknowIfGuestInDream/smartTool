package com.tlcsdm.common;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author: 唐 亮
 * @date: 2022/8/11 19:41
 * @since: 1.0
 */
public class BaseUtilsTest {

    @Test
    public void number2CNMonetaryUnitTest() {
        System.out.println(BaseUtils.number2CNMonetaryUnit(new BigDecimal("172.5")));
    }

    @Test
    public void dateToStrTest() {
        System.out.println(BaseUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    @Ignore
    public void dealCommonExcelTest() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.forEach(item -> item.replace("V_SUCCESS", ("1".equals(item.get("V_SUCCESS")) ? "成功" : "失败")));
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>(16);
        linkedHashMap.put("服务名", "V_SERVICE");
        linkedHashMap.put("模块名", "V_TITLE");
        linkedHashMap.put("操作类型", "V_OPERATETYPE");
        linkedHashMap.put("操作人", "V_OPERATEPER");
        linkedHashMap.put("操作时间", "V_CREATETIME");
        linkedHashMap.put("客户端IP", "V_IP");
        linkedHashMap.put("状态", "V_SUCCESS");
        linkedHashMap.put("traceid", "I_TRACEID");
        BaseUtils.dealCommonExcel(wb, sheet, list, linkedHashMap);
    }
}
