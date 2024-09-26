package api.steps;

import api.services.PlayerService;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class BaseSteps {
    protected PlayerService playerService;

    public BaseSteps() {
        this.playerService = new PlayerService();
    }

    @Step("Verify status code")
    public void verifyStatusCode(Response response, int expectedStatusCode) {
        playerService.getBaseApi().verifyStatusCode(response, expectedStatusCode);
    }
}


