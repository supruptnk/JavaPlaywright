package Pages;



import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class app {

    public static void main(String[] args){
        Playwright playwright = Playwright.create();
        BrowserType browserType = playwright.chromium();
        Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();

        page.navigate("https://www.amazon.in");

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("viewport_screenshot.png"))
            );
        
            page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("fullpage_screenshot.png"))
                .setFullPage(true)
            );

            // 3️⃣ Take a screenshot of a specific element
            Locator heading = page.locator("h1");
            heading.screenshot(new Locator.ScreenshotOptions()
                .setPath(Paths.get("element_screenshot.png"))
            );

            playwright.close();
    }
    
}
