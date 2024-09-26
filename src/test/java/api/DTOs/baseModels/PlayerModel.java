package api.DTOs.baseModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerModel {
    @JsonProperty(value = "age")
    protected int age;

    @JsonProperty(value = "gender")
    protected String gender;

    //in get all players response, player do not have a role, seems to be a bug
    @JsonProperty(value = "role")
    protected String role;

    @JsonProperty(value = "screenName")
    protected String screenName;

    public int getAge() {
        return this.age;
    }

    public String getGender() {
        return this.gender;
    }

    public String getRole() {
        return this.role;
    }

    public String getScreenName() {
        return this.screenName;
    }
}

