package api.DTOs.responseDTOs;

import api.DTOs.baseModels.PlayerWithIdModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdatePlayerResponseDTO extends PlayerWithIdModel {

    @JsonProperty("login")
    private String login;

    public String getLogin() {
        return this.login;
    }
}
