Feature: UI Acceptance Tests

  Scenario: Search for an Invalid Item
    When I search for an Item called "aaabbcc"
    Then the error message should consist of "Sorry we couldn’t find anything"

  Scenario: Search for a Valid Item
    When I search for an Item called "business-card"
    Then the search result should return more than one occurance of "business-card"

  Scenario: Add item to the cart
    Given we go to Envelopes
    When add the first item to the cart
    Then the customer should be directed to the Cart
    And the cart should have 1 item

  Scenario: Design a sticker
    Given we go to Round Stickers
    When we upload a design
    Then the alert message "Careful" should be displayed on successful file upload
