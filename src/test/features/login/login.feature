Feature: User Authentication

  Scenario: Authentication success
    Given a registered user login 'admin'
    And password 'admin'
    When I try to sign in to Mobibuzz system
    Then I have to receive the 'token' and 'user'
    And http status '200'
    # Behavior In frontend
    # And I have to login in system and be able to see the Home screen
