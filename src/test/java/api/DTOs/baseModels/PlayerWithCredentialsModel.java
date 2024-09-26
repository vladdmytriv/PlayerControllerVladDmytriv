package api.DTOs.baseModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerWithCredentialsModel extends PlayerWithIdModel {
    @JsonProperty("login")
    protected String login;

    @JsonProperty("password")
    protected String password;

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }
}
