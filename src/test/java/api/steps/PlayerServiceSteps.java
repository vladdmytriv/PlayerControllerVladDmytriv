package api.steps;

import api.DTOs.baseModels.PlayerIdModel;
import api.DTOs.baseModels.PlayerWithCredentialsModel;
import api.DTOs.requestDTOs.UpdatePlayerRequestDTO;
import api.DTOs.responseDTOs.GetAllPlayersResponseDTO;
import api.DTOs.responseDTOs.UpdatePlayerResponseDTO;
import io.qameta.allure.Step;

import static constants.ApiConstants.SC_OK;
import static java.lang.System.getProperty;
import static utils.RequestHelper.setPlayerQueryParams;

public class PlayerServiceSteps extends BaseSteps {

    @Step("Create new player with parameters: {0}")
    public PlayerWithCredentialsModel createNewPlayer(String editor, int age, String gender, String login, String role, String screenName, String password, int expectedStatusCode) {

        var response = playerService.sendCreatePlayerRequest(getProperty(editor), setPlayerQueryParams(age, gender, login, role, screenName, password));
        verifyStatusCode(response, expectedStatusCode);

        return response
                .getBody()
                .as(PlayerWithCredentialsModel.class);
    }

    @Step("Delete player with id: {1}")
    public void deletePlayer(String editor, long id, int expectedStatusCode) {
        verifyStatusCode(playerService.sendDeletePlayerRequest(getProperty(editor), new PlayerIdModel(id)), expectedStatusCode);
    }

    public PlayerWithCredentialsModel getPlayerById(PlayerIdModel playerId, int expectedStatusCode) {
        var response = playerService.sendGetPlayerByIdRequest(playerId);
        verifyStatusCode(response, expectedStatusCode);

        return response
                .getBody()
                .as(PlayerWithCredentialsModel.class);
    }

    @Step("Get all players")
    public GetAllPlayersResponseDTO getAllPlayers() {
        var response = playerService.sendGetAllPlayersRequest();
        verifyStatusCode(response, SC_OK);

        return response
                .getBody()
                .as(GetAllPlayersResponseDTO.class);
    }

    @Step("Update player with id: {1}")
    public UpdatePlayerResponseDTO updatePlayerInfo(String editor, long playerId, UpdatePlayerRequestDTO body, int expectedStatusCode) {
        var response = playerService.sendUpdatePlayerRequest(editor, playerId, body);
        verifyStatusCode(response, expectedStatusCode);

        return response
                .getBody()
                .as(UpdatePlayerResponseDTO.class);
    }
}
