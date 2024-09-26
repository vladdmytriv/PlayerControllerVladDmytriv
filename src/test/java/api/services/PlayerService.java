package api.services;

import api.DTOs.baseModels.PlayerIdModel;
import api.DTOs.requestDTOs.UpdatePlayerRequestDTO;
import io.restassured.response.Response;

import java.util.Map;

import static java.lang.String.format;
import static java.lang.System.getProperty;

public class PlayerService extends BaseApiService {

    // Possible bug - content-type "", should be JSON
    public Response sendCreatePlayerRequest(String editor, Map<String, Object> params) {
        return api.sendGetWithQueryParams(format("/player/create/%s", editor), params);
    }

    // Possible bug - content-type "", should be JSON
    public Response sendDeletePlayerRequest(String editor, PlayerIdModel playerId) {
        return api.sendDelete(format("/player/delete/%s", editor), playerId);
    }

    // Possible bug - content-type "", should be JSON
    public Response sendGetPlayerByIdRequest(PlayerIdModel playerId) {
        return api.sendPost("/player/get", playerId);
    }

    public Response sendGetAllPlayersRequest() {
        return api.sendGet("/player/get/all");
    }

    // Possible bug - content-type "", should be JSON
    public Response sendUpdatePlayerRequest(String editor, long playerId, UpdatePlayerRequestDTO body) {
        return api.sendPatch(format("/player/update/%s/%s", getProperty(editor), playerId), body);
    }
}
