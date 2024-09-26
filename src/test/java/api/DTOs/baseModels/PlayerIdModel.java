package api.DTOs.baseModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerIdModel {
    @JsonProperty("playerId")
    protected long playerId;

    public PlayerIdModel(long playerId) {
        this.playerId = playerId;
    }
}
