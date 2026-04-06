package com.demo.pages;

import com.demo.hooks.PlaywrightManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * InventoryPage — demonstrates Playwright's list/collection handling.
 *
 * Key concept: Locator is lazy — it doesn't query the DOM until you act on it.
 * This means you define locators once in the constructor and reuse them safely.
 * No StaleElementReferenceException — Playwright re-queries on each interaction.
 */
public class InventoryPage {

    private final Page page;

    private final Locator pageTitle;
    private final Locator inventoryItems;
    private final Locator addToCartButtons;
    private final Locator cartBadge;

    public InventoryPage() {
        this.page = PlaywrightManager.getPage();

        this.pageTitle       = page.locator(".title");
        this.inventoryItems  = page.locator(".inventory_item");
        this.addToCartButtons = page.locator("button[data-test^='add-to-cart']");
        this.cartBadge       = page.locator(".shopping_cart_badge");
    }

    // ─── Assertions ──────────────────────────────────────────────────────────

    public void assertOnInventoryPage() {
        // assertThat(locator).hasURL() — checks the page URL
        // SELENIUM: assertEquals(driver.getCurrentUrl(), expectedUrl)
        assertThat(page).hasURL("https://www.saucedemo.com/inventory.html");
        assertThat(pageTitle).hasText("Products");
    }

    public int getInventoryItemCount() {
        // locator.count() — returns number of matching elements
        // SELENIUM: driver.findElements(By.className("inventory_item")).size()
        return inventoryItems.count();
    }

    public void assertInventoryItemCount(int expected) {
        assertThat(inventoryItems).hasCount(expected);
    }

    // ─── Actions ─────────────────────────────────────────────────────────────

    public void addFirstItemToCart() {
        // locator.first() — targets the first matching element
        // SELENIUM: driver.findElements(locator).get(0).click()
        addToCartButtons.first().click();
    }

    public void addItemByName(String productName) {
        // Chaining locators: find the item container, then find the button inside it
        // SELENIUM: complex XPath like .//ancestor::div[@class='inventory_item']
        page.locator(".inventory_item")
            .filter(new Locator.FilterOptions().setHasText(productName))
            .locator("button[data-test^='add-to-cart']")
            .click();
    }

    public void assertCartCount(int expectedCount) {
        assertThat(cartBadge).hasText(String.valueOf(expectedCount));
    }

    public List<String> getAllProductNames() {
        // locator.allTextContents() — gets text from ALL matching elements as a List
        // SELENIUM: driver.findElements().stream().map(e -> e.getText()).collect(...)
        return inventoryItems.locator(".inventory_item_name").allTextContents();
    }
}
