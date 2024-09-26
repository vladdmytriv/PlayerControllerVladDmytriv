package api.DTOs.baseModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerWithIdModel extends PlayerModel {
    @JsonProperty(value = "id")
    protected long id;

    public long getId() {
        return this.id;
    }
}
