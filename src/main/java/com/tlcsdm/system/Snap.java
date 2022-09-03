package com.tlcsdm.system;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Snap {

    private String fileName;
    private String defaultName = "GuiCamera";
    private String imageFormat;//图像文件的格式
    private String defaultImageFormat = "png";
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

    public Snap() {
        fileName = defaultName;
        imageFormat = defaultImageFormat;
    }

    public Snap(String s) {
        fileName = s;
        imageFormat = defaultImageFormat;
    }

    public Snap(String s, String format) {
        fileName = s;
        imageFormat = format;
    }

    /**
     * 对屏幕进行拍照-截全屏
     **/
    public void snapshot() {
        snapshot((int) d.getWidth(), (int) d.getHeight());
    }

    /**
     * 对屏幕进行拍照-按照指定的高度 宽度进行截屏
     **/
    public void snapshot(int width, int Height) {
        snapshot(0, 0, width, Height);
    }

    /**
     * 对屏幕进行拍照-按照指定的高度 宽度进行截屏
     **/
    public void snapshot(int x, int y, int width, int Height) {
        try {
            //拷贝屏幕到一个BufferedImage对象screenshot
            BufferedImage screenshot = (new Robot()).createScreenCapture(
                    new Rectangle(x, y, width, Height));
            //根据文件前缀变量和文件格式变量，自动生成文件名
            String name = fileName + "." + imageFormat;
            File f = new File(name);
            //将screenshot对象写入图像文件
            ImageIO.write(screenshot, imageFormat, f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
