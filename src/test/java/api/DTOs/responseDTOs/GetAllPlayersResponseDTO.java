package api.DTOs.responseDTOs;

import api.DTOs.baseModels.PlayerWithIdModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetAllPlayersResponseDTO {

    @JsonProperty("players")
    protected List<PlayerWithIdModel> players;

    public List<PlayerWithIdModel> getPlayers() {
        return this.players;
    }
}
