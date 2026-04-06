package com.demo.steps;

import com.demo.pages.InventoryPage;
import com.demo.pages.LoginPage;
import io.cucumber.java.en.*;

/**
 * LoginSteps — step definitions. Same structure as your existing Cucumber steps.
 *
 * Notice: no driver/page object passed in — fetched from PlaywrightManager
 * via the page objects themselves. Same pattern you'd use with a Selenium
 * ThreadLocal driver manager.
 */
public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final InventoryPage inventoryPage = new InventoryPage();

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage.navigate();
    }

    @When("I enter username {string} and password {string}")
    public void iEnterUsernameAndPassword(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        loginPage.clickLogin();
    }

    @Then("I should be on the inventory page")
    public void iShouldBeOnTheInventoryPage() {
        inventoryPage.assertOnInventoryPage();
    }

    @Then("I should see error message {string}")
    public void iShouldSeeErrorMessage(String message) {
        loginPage.assertErrorMessage(message);
    }
}
