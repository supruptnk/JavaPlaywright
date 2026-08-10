package Utils;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.ScreenshotOptions;


public class ScreenshotUtils {
    
    public static void takeScreenshot(Page page, String path){
        page.screenshot(new ScreenshotOptions().setPath(Paths.get(path)));
    }

    public static void takeFullPageScreenshot(Page page, String path){
        page.screenshot(new ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
    }
}
