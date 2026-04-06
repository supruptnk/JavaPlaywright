package com.demo.steps;

import com.demo.pages.InventoryPage;
import com.demo.pages.LoginPage;
import io.cucumber.java.en.*;

public class InventorySteps {

    private final LoginPage loginPage     = new LoginPage();
    private final InventoryPage inventoryPage = new InventoryPage();

    @Given("I am logged in as {string} with password {string}")
    public void iAmLoggedInAs(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("I should see {int} products on the inventory page")
    public void iShouldSeeProductsOnTheInventoryPage(int count) {
        inventoryPage.assertInventoryItemCount(count);
    }

    @When("I add {string} to the cart")
    public void iAddProductToCart(String productName) {
        inventoryPage.addItemByName(productName);
    }

    @When("I add the first product to the cart")
    public void iAddFirstProductToCart() {
        inventoryPage.addFirstItemToCart();
    }

    @Then("the cart badge should show {int} item")
    public void theCartBadgeShouldShow(int count) {
        inventoryPage.assertCartCount(count);
    }
}
