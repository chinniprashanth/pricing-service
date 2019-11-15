#Author: Pooja
#Date: 14th Oct 2019
Feature: Pricing service

@COC-182
Scenario: customer/guest want to be able to view price for the items added in the cart so that he/she can makepurchase decision
    Given user cart is not empty and should contain items in cart
    When user goes to cart page/ views cart
    Then price should be displayed for each item in cart and the total amount of the cart
   
 
@COC-185
Scenario: Display an estimated shipping charge based on the chosen fulfillment method on the Cart page / checkout pages
    When user clicks on checkout on shipping page
    Then User should be able to see the applied prices for shipping
    
