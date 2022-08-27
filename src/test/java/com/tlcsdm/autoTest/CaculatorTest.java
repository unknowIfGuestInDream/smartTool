package com.tlcsdm.autoTest;

import org.junit.Test;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import java.io.IOException;

/**
 * @author: 唐 亮
 * @date: 2022/8/26 22:46
 * @since: 1.0
 */
public class CaculatorTest {

    @Test
    public void caculatorTest() throws IOException, FindFailed {
        Runtime.getRuntime().exec("calc.exe");
        Region screen = new Screen();
        screen.click("E:\\testPlace\\2.png");
        screen.click("E:\\testPlace\\and.png");
        screen.click("E:\\testPlace\\5.png");
        screen.click("E:\\testPlace\\equal.png");
        if (screen.exists("E:\\testPlace\\7.png").isValid()) {
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
    }
}
