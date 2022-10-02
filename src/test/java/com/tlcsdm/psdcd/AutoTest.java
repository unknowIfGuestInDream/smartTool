package com.tlcsdm.psdcd;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sikuli.basics.Settings;
import org.sikuli.script.*;

public class AutoTest {

	@BeforeClass
	public static void init() {
		Settings.ActionLogs = false;
		Settings.InfoLogs = false;
		ImagePath.setBundlePath("C:\\workspace\\test\\autotestImage");
	}

	@Test
	public void autoTest1() throws FindFailed {
		App app = App.open("C:\\workspace\\rcp\\V1.7\\eclipse\\SmartConfigurator.exe", 10);
		Region screen = new Screen();
		screen.wait("catchSmartButtons", 5);
		screen.click("newConfigurationFile");

		screen.exists("RH850U2A16", 2).click();
		screen.click("unexpand");
		screen.exists("RH850U2A16_516pin", 2).click();
		screen.click("unexpand");
		screen.exists("R7F702300EBBG", 1).click();
		screen.type("fileName", "testapi");
		//screen.paste("fileName", "testapi");
		screen.click("button_finish");
		if (screen.has("hasSameFileName", 2000)) {
			screen.click("button_yes");
		}

		screen.exists("tab_components", 3).click();
		screen.exists("addComponent", 5).click();
		if (!screen.has("window_newComponent", 3000)) {
			throw new FindFailed("window_newComponent not found");
		}
		// screen.exists("component_dmaController", 5).click();
		// screen.exists("component_riicMaster", 5).click();
		// screen.wheel("component_dmaController", Button.WHEEL_DOWN, 10);
//		screen.
//		IRobot r = screen.getScreen().getRobot();
//		r.mouseWheel(Button.WHEEL_DOWN);
//		r.delay(50);
		screen.dragDrop("scroll_down");
//		t = screen.find("").below().find("");
		//向上拖动10
//		screen.dragDrop(t, new Location(t.x,t.y-10));
	}
}
