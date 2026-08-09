package Utils;

import java.nio.file.Path;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.ScreenshotOptions;


public class ScreenshotUtils {
    
    public static void takeScreenshot(Page page, Path path){
        page.screenshot(new ScreenshotOptions().setPath(path));
    }

    public static void takeFullPageScreenshot(Page page, Path path){
        page.screenshot(new ScreenshotOptions().setPath(path).setFullPage(true));
    }
}
