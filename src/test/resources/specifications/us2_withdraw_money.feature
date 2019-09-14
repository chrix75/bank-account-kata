Feature: US02 Withdraw money from an account

  Background:
    Given Stephan opened his account with an initial deposit of 100 euro

  Scenario: Withdraw some money from an account
    When Stephan makes a withdrawal from his account of 50 euro at 2019-09-14
    Then the new account balance is 50 euro

  Scenario: Withdraw all money from an account
    When Stephan makes a withdrawal from his account of 100 euro at 2019-09-14
    Then the new account balance is 0 euro

  Scenario: Withdraw too munch money from an account
    When Stephan makes a withdrawal from his account of 101 euro at 2019-09-14
    Then the withdraw is refused

  Scenario: Withdraw money from an account in other currency
    Given 1 US dollar is worth 0.8 euro
    When Stephan makes a withdrawal from his account of 50 US dollars at 2019-09-14
    Then the new account balance is 60 euro

  Scenario: Try to make a withdrawal of 0 euro
    When Stephan makes a withdrawal from his account of 0 euro at 2019-09-14
    Then the withdraw is refused







