Feature: US03 Check operations

  Scenario: Get account statement
    Given Stephan opened his account with an initial deposit of 100 euro
    And the following banking operations made by Stephan
      | date       | amount |
      | 2019-02-01 | 10     |
      | 2019-02-02 | -20    |
      | 2019-02-04 | 50     |
    When Stephan asks the account statement
    Then he gets the following statement printing
      | date       | amount | balance |
      | 2019-02-01 | 10     | 110     |
      | 2019-02-02 | -20    | 90      |
      | 2019-02-04 | 50     | 140     |


