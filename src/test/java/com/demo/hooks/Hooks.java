package com.demo.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import com.microsoft.playwright.Page;

/**
 * Hooks — same @Before / @After pattern you use today with Selenium.
 *
 * SELENIUM YOU KNOW:
 *   @Before public void setUp() { driver = new ChromeDriver(); }
 *   @After  public void tearDown() { driver.quit(); }
 *
 * PLAYWRIGHT: same structure, different manager class.
 */
public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("▶ Starting: " + scenario.getName());
        // Set headless=false to watch the browser during learning
        // Set headless=true for CI pipeline runs
        PlaywrightManager.initBrowser(false);
    }

    @After
    public void tearDown(Scenario scenario) {
        Page page = PlaywrightManager.getPage();

        // ✅ Playwright built-in: screenshot on failure — no extra library needed
        // In Selenium you needed AShot or TakesScreenshot casting
        if (scenario.isFailed() && page != null) {
            byte[] screenshot = page.screenshot(
                new Page.ScreenshotOptions().setFullPage(true)
            );
            scenario.attach(screenshot, "image/png", "failure-screenshot");
            System.out.println("📸 Screenshot captured for failed scenario");
        }

        PlaywrightManager.closeBrowser();
        System.out.println("⏹ Finished: " + scenario.getName()
            + " | Status: " + scenario.getStatus());
    }
}
