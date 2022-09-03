package com.tlcsdm.system;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import org.junit.Test;

/**
 * 屏幕截图
 *
 * @author: 唐 亮
 * @date: 2022/9/3 22:10
 * @since: 1.0
 */
public class SnapTest {

    /**
     * 截全屏
     */
    @Test
    public void SnapTest1() {
        Snap cam = new Snap("E:\\testPlace\\" + DatePattern.PURE_DATETIME_FORMAT.format(new DateTime()));
        cam.snapshot();
    }

    /**
     * 按照指定的高度 宽度进行截屏
     */
    @Test
    public void SnapTest2() {
        Snap cam = new Snap("E:\\testPlace\\" + DatePattern.PURE_DATETIME_FORMAT.format(new DateTime()));
        cam.snapshot(300, 400);
    }

    /**
     * 按照指定的坐标开始截屏指定高度 宽度的图片
     */
    @Test
    public void SnapTest3() {
        Snap cam = new Snap("E:\\testPlace\\" + DatePattern.PURE_DATETIME_FORMAT.format(new DateTime()));
        cam.snapshot(200, 200, 300, 400);
    }
}
