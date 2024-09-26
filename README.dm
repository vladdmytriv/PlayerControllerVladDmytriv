Automated Test Framework For Player Controller

Technologies:
Maven, Java 11, RestAssured, TestNG, Allure Report;

Project was created for testing "Player Controller" functionality, including all operations with player object.

Operations:
- Create player;
- Get all players;
- Get player by id;
- Update player;
- Delete player;

Verify different positive and negative test cases on base level. Product have some bugs that can be found over the project.

To make allure report need to invoke in terminal, use such command:
- mvn clean test
- mvn allure:serve