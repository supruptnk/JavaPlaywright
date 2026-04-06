# inventory.feature
Feature: Product inventory
  As a logged-in user
  I want to browse and interact with products
  So that I can add items to my cart

  Background:
    Given I am logged in as "standard_user" with password "secret_sauce"

  Scenario: Inventory page loads with correct product count
    Then I should see 6 products on the inventory page

  Scenario: Add a specific product to cart
    When I add "Sauce Labs Backpack" to the cart
    Then the cart badge should show 1 item

  Scenario: Add first available product to cart
    When I add the first product to the cart
    Then the cart badge should show 1 item
