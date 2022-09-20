package com.tlcsdm.autoTest;

import cn.hutool.core.io.resource.ResourceUtil;
import org.junit.Test;
import org.sikuli.basics.Settings;
import org.sikuli.script.*;

import javax.swing.plaf.multi.MultiPopupMenuUI;
import java.io.IOException;

/**
 * @author: 唐 亮
 * @date: 2022/8/26 22:46
 * @since: 1.0
 */
public class CaculatorTest {

    @Test
    public void caculatorTest() throws IOException, FindFailed {
        Settings.ActionLogs=false;
        Settings.InfoLogs=false;
        ImagePath.setBundlePath("E:\\javaWorkSpace\\smartTool\\src\\main\\resources\\static\\private\\sikuliX\\caculator");
        App.run("calc.exe");
        Region screen = new Screen();
        //screen.click(ResourceUtil.getResource("static/private/sikuliX/caculator/2.png").getPath());
        screen.click("2.png");
        screen.click(ResourceUtil.getResource("static/private/sikuliX/caculator/and.png").getPath());
        screen.click(ResourceUtil.getResource("static/private/sikuliX/caculator/5.png").getPath());
        screen.click(ResourceUtil.getResource("static/private/sikuliX/caculator/equal.png").getPath());
        if (screen.exists(ResourceUtil.getResource("static/private/sikuliX/caculator/7.png").getPath()).isValid()) {
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
    }
}
