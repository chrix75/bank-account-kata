Feature: US01 Deposit money in an account

  Background:
    Given Stephan opened his account with an initial deposit of 100 euro

  Scenario: Make a deposit on an account
    When Stephan makes a deposit in his account of 70 euro at 2019-09-14
    Then the new account balance is 170 euro

  Scenario: Try to make a deposit of 0 euro
    When Stephan makes a deposit in his account of 0 euro at 2019-09-14
    Then the withdraw is refused







