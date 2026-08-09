package DriverManager;

import java.util.List;
import java.util.Map;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Browser.NewContextOptions;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import java.util.Map;

public class BrowserManager {

    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserTL = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContextTL = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageTL = new ThreadLocal<>();

    private BrowserManager(){}

    public static void setPlaywright(){
        playwright.set(Playwright.create());
    }

    public static Playwright getPlaywright(){
        return playwright.get();
    }

    public static BrowserType getBrowserType(String browser){
        browser = browser.toLowerCase();
        BrowserType browsertype = switch(browser){
            case "chrome" -> getPlaywright().chromium();
            case "firefox" -> getPlaywright().firefox();
            case "safari" -> getPlaywright().webkit();
            default -> getPlaywright().chromium();
        };
        return browsertype;
    }

    public static void setBrowser(String browserString){
        BrowserType browsertype = getBrowserType(browserString);
        Browser browser = browsertype.launch(new BrowserType.LaunchOptions().setHeadless(true)
                                                        .setSlowMo(10)
                                                        .setChannel("chrome")
                                                    .setArgs(List.of("--disable-blink-features=AutomationControlled")));
        browserTL.set(browser);
    }

    public static Browser getBrowser(){
        return browserTL.get();
    }

    public static void setBrowserContext(){
        browserContextTL.set(
            getBrowser().newContext(
                    new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"))
                
                );
    }

    public static BrowserContext getBrowserContext(){
        return browserContextTL.get();
    }


    public static void initPage(){
        pageTL.set(getBrowserContext().newPage());
    }

    public static Page getPage(){
        return pageTL.get();
    }

}
