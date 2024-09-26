package data;

import org.testng.annotations.DataProvider;

import static constants.ApiConstants.*;

public class PlayerServiceDataProvider {
    @DataProvider(name = "playerCreationDataProvider")
    public Object[][] playerCreationDataProvider() {
        return new Object[][]{
                {age, gender, login, role, screenName, password},
                {age, gender, login, role, screenName, null},
        };
    }
}
