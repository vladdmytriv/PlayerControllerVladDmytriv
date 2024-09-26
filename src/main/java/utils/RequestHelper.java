package utils;

import java.util.HashMap;

public class RequestHelper {
    public static HashMap<String, Object> setPlayerQueryParams(int age, String gender, String login, String role, String screenName, String password) {
        HashMap<String, Object> playerParams = new HashMap<>();
        playerParams.put("age", age);
        playerParams.put("gender", gender);
        playerParams.put("login", login);
        playerParams.put("role", role);
        playerParams.put("screenName", screenName);
        playerParams.put("password", password);

        return playerParams;
    }
}
