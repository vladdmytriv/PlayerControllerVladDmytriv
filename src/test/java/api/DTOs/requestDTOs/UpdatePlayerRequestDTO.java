package api.DTOs.requestDTOs;

import api.DTOs.baseModels.PlayerModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdatePlayerRequestDTO extends PlayerModel {
    @JsonProperty("login")
    protected String login;

    @JsonProperty("password")
    protected String password;

    public UpdatePlayerRequestDTO(int age, String gender, String role, String screenName, String login, String password) {
        this.age = age;
        this.gender = gender;
        this.role = role;
        this.screenName = screenName;
        this.login = login;
        this.password = password;
    }
}
