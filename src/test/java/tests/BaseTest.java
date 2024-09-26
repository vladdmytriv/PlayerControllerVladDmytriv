package tests;

import api.DTOs.baseModels.PlayerWithIdModel;
import api.steps.PlayerServiceSteps;
import org.testng.annotations.BeforeClass;

import java.util.stream.Collectors;

import static constants.ApiConstants.*;

public class BaseTest {
    protected PlayerServiceSteps playerServiceSteps;

    @BeforeClass
    public void setUp() {
        playerServiceSteps = new PlayerServiceSteps();

        var allPlayersWithDefaultScreenName = playerServiceSteps
                .getAllPlayers()
                .getPlayers()
                .stream()
                .filter(player -> player.getScreenName().equals(defaultPlayerScreenName))
                .collect(Collectors.toList());

        if (!allPlayersWithDefaultScreenName.isEmpty()) {
            for (PlayerWithIdModel player : allPlayersWithDefaultScreenName) {
                playerServiceSteps.deletePlayer(baseEditor, player.getId(), SC_OK);
            }
        }
    }
}
