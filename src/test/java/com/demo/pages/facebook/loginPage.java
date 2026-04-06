package com.demo.pages.facebook;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class loginPage {

    public static void main(String[] args){
    Playwright pl = Playwright.create();
    Browser browser = pl.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    BrowserContext context = browser.newContext();
    Page page = context.newPage();

    page.navigate("https://www.facebook.com");
    page.locator("text=Create new account").click();
    }
}
