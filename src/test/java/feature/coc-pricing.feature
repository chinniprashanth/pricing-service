#Author: Pooja
#Date: 14th Oct 2019
Feature: Pricing service

@COC-182
Scenario: customer/guest want to be able to view price for the items added in the cart so that he/she can makepurchase decision
    Given user cart is not empty and should contain items in cart
    When user goes to cart page/ views cart
    Then price should be displayed for each item in cart and the total amount of the cart
    And user selects home delivery 
    Then shipping details should be displayed
 
@COC-185
Scenario: Display an estimated shipping charge based on the chosen fulfillment method on the Cart page / checkout pages
    Given user cart is not empty
    When user cart should contain items in cart
    Then delivery options should be displayed for each item in cart
    And user selects reserve Online or pay in-store and adds the address accordingly 
    Then User should be able to see the applied prices for shiping
    
@COC-215
Scenario:  System should be able to calculate shipping charge based on quantity of items
    Given user cart contains item SKU
    When the user updates the quantity of an item to 2
    Then user cart should contain items in cart
    When use checks the shipping price
    Then shipping price should be updated as per the quantity, if it is applicable

@COC-216
Scenario Outline: system be able to calculate shipping charge based on quantity of items
 	Given user cart contains item SKU
    When the user updates the quantity of an item to 2
    Then user cart should contain items in cart
    When use checks the shipping price
    Then shipping price should be updated as per the quantity, if it is applicable

Examples:
|SKU|
|SKU123|
|SKU345|
|SKU543|
|SKU678|
|SKU567|
|SKU892|
|SKU353|

@COC-226
Scenario: User should be  able to view prices order Summary on the Cart and checkout pages for the Product(s) added to My Cart
    Given user cart contains item SKU
    When goes to the cart page or order summary page
    Then user cart should be able to see all the calculated prices
        
@COC-239
Scenario: Applicable surcharge should be calculated and displayed to the customer based on the products
 	Given user cart contains item SKU
    When the user goes to the cart page
    Then Quantity should be greater than 0
    And user cart should contain item SKU
    Then user should be able to see the calculated surchares if applicable on any of the products
       
@COC-255
Scenario: User should be notified/displayed a custom message if the price of any of the item in cart has changed.
 	Given user cart contains item SKU
    When user visits the cart after sometime
    Then cart should be populated with prices
    And prices should be visible
    Then user should be notified via a display message if the prices change

