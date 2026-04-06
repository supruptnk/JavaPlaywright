package com.demo.hooks;

import com.microsoft.playwright.*;

/**
 * PlaywrightManager — think of this as your WebDriverManager equivalent.
 *
 * SELENIUM YOU KNOW:
 *   WebDriver driver = new ChromeDriver();
 *
 * PLAYWRIGHT EQUIVALENT:
 *   Playwright playwright = Playwright.create();
 *   Browser browser = playwright.chromium().launch();
 *   BrowserContext context = browser.newContext();
 *   Page page = context.newPage();
 *
 * WHY THE EXTRA STEPS?
 *   BrowserContext = isolated session (like incognito).
 *   Multiple contexts can run in ONE browser process — faster than Selenium's
 *   one-driver-per-thread model for parallel tests.
 */
public class PlaywrightManager {

    // ThreadLocal ensures each parallel test thread gets its own instances
    private static final ThreadLocal<Playwright> playwrightTL   = new ThreadLocal<>();
    private static final ThreadLocal<Browser>    browserTL      = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> contextTL  = new ThreadLocal<>();
    private static final ThreadLocal<Page>       pageTL         = new ThreadLocal<>();

    public static void initBrowser(boolean headless) {
        Playwright playwright = Playwright.create();

        Browser browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions()
                .setHeadless(headless)          // false = you can watch it run
                .setSlowMo(100)                 // slows actions by 100ms — good for debugging
        );

        // BrowserContext = isolated session per test (no cookie/state bleed between tests)
        // In Selenium you got this "for free" by creating a new driver — here it's explicit
        BrowserContext context = browser.newContext(
            new Browser.NewContextOptions()
                .setViewportSize(1280, 800)
        );

        Page page = context.newPage();

        playwrightTL.set(playwright);
        browserTL.set(browser);
        contextTL.set(context);
        pageTL.set(page);
    }

    /** The Page object is your main handle — equivalent of WebDriver in Selenium */
    public static Page getPage() {
        return pageTL.get();
    }

    public static void closeBrowser() {
        if (contextTL.get() != null)   contextTL.get().close();
        if (browserTL.get() != null)   browserTL.get().close();
        if (playwrightTL.get() != null) playwrightTL.get().close();
        pageTL.remove();
        contextTL.remove();
        browserTL.remove();
        playwrightTL.remove();
    }
}
