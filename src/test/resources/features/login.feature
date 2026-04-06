# login.feature
# Same Gherkin you write today — nothing changes here
# Test site: https://www.saucedemo.com (free demo site, no signup needed)
# Valid credentials:  standard_user / secret_sauce
# Locked user:        locked_out_user / secret_sauce

Feature: Login functionality
  As a user of the SauceDemo application
  I want to log in with valid credentials
  So that I can access the product inventory

  Background:
    Given I am on the login page

  Scenario: Successful login with valid credentials
    When I enter username "standard_user" and password "secret_sauce"
    And I click the login button
    Then I should be on the inventory page

  Scenario: Login fails with locked out user
    When I enter username "locked_out_user" and password "secret_sauce"
    And I click the login button
    Then I should see error message "Sorry, this user has been locked out"

  Scenario: Login fails with wrong password
    When I enter username "standard_user" and password "wrong_password"
    And I click the login button
    Then I should see error message "Username and password do not match"

  Scenario: Login fails with empty credentials
    When I enter username "" and password ""
    And I click the login button
    Then I should see error message "Username is required"
