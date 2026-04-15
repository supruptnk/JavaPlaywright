package com.demo.pages.facebook;

import com.demo.hooks.PlaywrightManager;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class FbLoginPage {
    private final Page page;

    private final Locator pageTitle;
    private final Locator inventoryItems;
    private final Locator addToCartButtons;
    private final Locator cartBadge;

    public FbLoginPage() {
        this.page = PlaywrightManager.getPage();

        this.pageTitle       = page.locator(".title");
        this.inventoryItems  = page.locator(".inventory_item");
        this.addToCartButtons = page.locator("button[data-test^='add-to-cart']");
        this.cartBadge       = page.locator(".shopping_cart_badge");
    }
    public static void main(String[] args){
    Playwright pl = Playwright.create();
    Browser browser = pl.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    BrowserContext context = browser.newContext();
    Page page = context.newPage();

    page.navigate("https://www.facebook.com");
    page.locator("text=Create new account").click();
    }
}
