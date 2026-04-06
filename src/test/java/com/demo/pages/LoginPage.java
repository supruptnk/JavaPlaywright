package com.demo.pages;

import com.demo.hooks.PlaywrightManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * LoginPage — Page Object Model (same pattern you use with Selenium).
 *
 * KEY DIFFERENCE: No explicit waits anywhere in this file.
 * Playwright auto-waits for every action:
 *   - element to be visible
 *   - element to be enabled
 *   - element to stop animating
 * before performing click/fill/etc.
 *
 * Default timeout: 30 seconds. Configurable globally or per-action.
 */
public class LoginPage {

    private final Page page;

    // ─── Locators ────────────────────────────────────────────────────────────
    //
    // SELENIUM:  By.id("user-name")
    // PLAYWRIGHT: page.locator("#username")  ← CSS selector
    //
    // Playwright supports: CSS, XPath, text, role, label, placeholder, testId
    // Best practice: prefer role/label locators — they're resilient to HTML changes

    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator loginButton;
    private final Locator errorMessage;

    public LoginPage() {
        this.page = PlaywrightManager.getPage();

        // getByPlaceholder — finds input by its placeholder text
        // More resilient than By.id() — survives id/name attribute changes
        this.usernameField = page.getByPlaceholder("Username");
        this.passwordField = page.getByPlaceholder("Password");
       

        // getByRole — finds by ARIA role + accessible name
        // SELENIUM: By.cssSelector("input[type='submit']")
        // PLAYWRIGHT: page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"))
        this.loginButton   = page.getByRole(AriaRole.BUTTON,
                                 new Page.GetByRoleOptions().setName("Login"));

        this.errorMessage  = page.locator("[data-test='error']");
    }

    // ─── Actions ─────────────────────────────────────────────────────────────

    public LoginPage navigate() {
        page.navigate("https://www.saucedemo.com");
        return this;
    }

    public LoginPage enterUsername(String username) {
        // SELENIUM: driver.findElement(By.id("user-name")).sendKeys(username)
        // PLAYWRIGHT: locator.fill(value)
        // fill() clears the field first — cleaner than sendKeys() which appends
        usernameField.fill(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordField.fill(password);
        return this;
    }

    public void clickLogin() {
        loginButton.click();
        // No explicit wait needed — Playwright waits for navigation automatically
    }

    public void login(String username, String password) {
        navigate()
            .enterUsername(username)
            .enterPassword(password)
            .clickLogin();
    }

    // ─── Assertions ──────────────────────────────────────────────────────────

    public void assertErrorMessage(String expectedText) {
        // PlaywrightAssertions.assertThat() — built-in assertion with auto-retry
        // Retries the assertion for up to 5 seconds before failing
        // SELENIUM: you needed explicit wait + getText() + assertEquals()
        assertThat(errorMessage).isVisible();
        assertThat(errorMessage).containsText(expectedText);
    }

    public String getErrorMessage() {
        return errorMessage.textContent();
    }
}
