@checkout @cart
Feature: Checkout
  As a customer
  I want to complete my purchase
  So that I can place an order successfully

  Background:
    Given I have a product in the cart
    And I am on the checkout page

  @smoke @positive
  Scenario: Place order on checkout page
    When I complete checkout with valid billing details:
      | field          | value            |
      | First name     | Tresor           |
      | Last name      | Xavier           |
      | Company        | Gasabo           |
      | Street         | Kigali           |
      | City           | Kigali           |
      | Postcode / ZIP | 90210            |
      | Phone          | +250780000000    |
      | Email          | tresor@gmail.com |
    Then the order should be placed successfully

  @regression @negative @validation
  Scenario Outline: Required billing fields validation
    When I attempt to place an order without "<field>"
    Then I should see the validation message "<message>"

    Examples:
      | field          | message                                     |
      | First name     | Billing First name is a required field.     |
      | Last name      | Billing Last name is a required field.      |
      | Street address | Billing Street address is a required field. |
      | Town / City    | Billing Town / City is a required field.    |
      | State          | Billing State / County is a required field. |
      | Postcode / ZIP | Billing Postcode / ZIP is a required field. |
      | Email          | Billing Email address is a required field.  |

  @regression @positive
  Scenario: View order summary on checkout page
    Then I should see the order summary
    And the order total should be correct
