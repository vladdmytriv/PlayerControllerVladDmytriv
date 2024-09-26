package tests;

import api.DTOs.baseModels.PlayerIdModel;
import api.DTOs.baseModels.PlayerWithCredentialsModel;
import api.DTOs.baseModels.PlayerWithIdModel;
import api.DTOs.requestDTOs.UpdatePlayerRequestDTO;
import com.beust.jcommander.internal.Nullable;
import data.PlayerServiceDataProvider;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.Collectors;

import static constants.ApiConstants.*;
import static java.lang.String.format;
import static org.testng.Assert.*;
import static utils.MathHelper.getDifference;

public class PlayerServiceTests extends BaseTest {

    private PlayerWithCredentialsModel createdPlayer;

    @Test(dataProvider = "playerCreationDataProvider", dataProviderClass = PlayerServiceDataProvider.class)
    @Description("Verify successful creation of a new player")
    public void verifySuccessfulPlayerCreation(int age, String gender, String login, String role, String screenName, @Nullable String password) {
        SoftAssert softAssert = new SoftAssert();

        int playersCountBeforeCreation = playerServiceSteps
                .getAllPlayers()
                .getPlayers()
                .size();

        createdPlayer = playerServiceSteps.createNewPlayer(baseEditor, age, gender, login, role, screenName, password, SC_OK);

        int playersCountAfterCreation = playerServiceSteps
                .getAllPlayers()
                .getPlayers()
                .size();

        int countsDiff = getDifference(playersCountAfterCreation, playersCountBeforeCreation);

        var createdPlayersList = getPlayersByParams(createdPlayer.getAge(), createdPlayer.getGender(), createdPlayer.getRole(), createdPlayer.getScreenName())
                .stream()
                .filter(player -> player.getId() == createdPlayer.getId())
                .collect(Collectors.toList());

        assertEquals(countsDiff, 1, format("Difference between counts before and after player creation should be 1, but was %s", countsDiff));
        assertFalse(createdPlayersList.isEmpty(), "List of created players shouldn't be empty");

        softAssert.assertEquals(createdPlayer.getAge(), age, "Actual created player info should be equal to expected");
        softAssert.assertEquals(createdPlayer.getGender(), gender, "Actual created player info should be equal to expected");
        softAssert.assertEquals(createdPlayer.getRole(), role, "Actual created player info should be equal to expected");
        softAssert.assertEquals(createdPlayer.getScreenName(), screenName, "Actual created player info should be equal to expected");
        softAssert.assertEquals(createdPlayer.getLogin(), login, "Actual created player info should be equal to expected");
        softAssert.assertEquals(createdPlayer.getPassword(), password, "Actual created player info should be equal to expected");

        softAssert.assertAll();
    }

    @Test
    @Description("Verify unsuccessful creation of a new player")
    public void verifyUnsuccessfulPlayerCreation() {
        int playersCountBeforeCreation = playerServiceSteps
                .getAllPlayers()
                .getPlayers()
                .size();

        playerServiceSteps.createNewPlayer(customEditor, age, gender, login, role, screenName, password, SC_FORBIDDEN);

        int playersCountAfterCreation = playerServiceSteps
                .getAllPlayers()
                .getPlayers()
                .size();

        int countsDiff = getDifference(playersCountAfterCreation, playersCountBeforeCreation);
        var createdPlayersList = getPlayersByParams(age, gender, role, screenName);

        assertEquals(countsDiff, 0, format("Difference between counts before and after player creation should be 0, but was %s", countsDiff));
        assertTrue(createdPlayersList.isEmpty(), "List of created players should be empty");
    }

    @Test
    @Description("Verify if responded player is correct")
    public void verifyGetPlayerById() {
        SoftAssert softAssert = new SoftAssert();

        //could be used newly created user
        var expectedPlayer = playerServiceSteps
                .getAllPlayers()
                .getPlayers()
                .stream()
                .findAny()
                .get();

        var expectedId = expectedPlayer.getId();

        var actualPlayer = playerServiceSteps.getPlayerById(new PlayerIdModel(expectedId), SC_OK);

        softAssert.assertEquals(actualPlayer.getAge(), expectedPlayer.getAge(), "Actual player age should be equal to expected");
        softAssert.assertEquals(actualPlayer.getGender(), expectedPlayer.getGender(), "Actual player gender should be equal to expected");
        //In get all players response player do not have a role, seems to be a bug!//
        //softAssert.assertEquals(actualPlayer.getRole(), expectedPlayer.getRole(), "Actual player role should be equal to expected");
        softAssert.assertEquals(actualPlayer.getScreenName(), expectedPlayer.getScreenName(), "Actual player screen name should be equal to expected");
        softAssert.assertEquals(actualPlayer.getId(), expectedId, "Actual player Id should be equal to " + expectedId);

        softAssert.assertAll();
    }

    //possible bug - 201 status code, need to be 204
    @Test
    @Description("Verify if id user can't get player by wrong id")
    public void verifyGetPlayerByIdWithWrongPlayerId() {
        long testPlayerId;

        var allPlayersId = playerServiceSteps
                .getAllPlayers()
                .getPlayers()
                .stream()
                .map(PlayerWithIdModel::getId)
                .collect(Collectors.toList());

        testPlayerId = allPlayersId
                .stream()
                .findAny()
                .get();

        while (!allPlayersId.contains(testPlayerId)) {
            testPlayerId = testPlayerId + 1;
        }

        playerServiceSteps.getPlayerById(new PlayerIdModel(testPlayerId), 204);
    }

    @Test
    @Description("Verify updating of a player")
    public void verifyPlayerWasUpdated() {
        SoftAssert softAssert = new SoftAssert();

        var randomPlayerId = getRandomPlayerId();
        var player = playerServiceSteps.getPlayerById(new PlayerIdModel(randomPlayerId), SC_OK);

        playerServiceSteps.updatePlayerInfo(baseEditor, randomPlayerId,
                new UpdatePlayerRequestDTO(ageForUpdating, genderForUpdating, roleForUpdating, screenNameForUpdating, loginForUpdating, passwordForUpdating), SC_OK);

        var updatedPlayer = playerServiceSteps.getPlayerById(new PlayerIdModel(randomPlayerId), SC_OK);

        assertNotEquals(player, updatedPlayer, "Updated player should be different from old one");
        softAssert.assertEquals(updatedPlayer.getAge(), ageForUpdating, format("Age should be equal to %s", ageForUpdating));
        softAssert.assertEquals(updatedPlayer.getGender(), genderForUpdating, format("Gender should be equal to %s", genderForUpdating));
        softAssert.assertEquals(updatedPlayer.getRole(), roleForUpdating, format("Role should be equal to %s", roleForUpdating));
        softAssert.assertEquals(updatedPlayer.getScreenName(), screenNameForUpdating, format("ScreenName should be equal to %s", screenNameForUpdating));
        softAssert.assertEquals(updatedPlayer.getLogin(), loginForUpdating, format("Login should be equal to %s", loginForUpdating));
        softAssert.assertEquals(updatedPlayer.getPassword(), passwordForUpdating, format("Password should be equal to %s", passwordForUpdating));

        playerServiceSteps.updatePlayerInfo(baseEditor, randomPlayerId, new UpdatePlayerRequestDTO(age, gender, role, screenName, login, password), SC_OK);

        softAssert.assertAll();
    }

    @Test
    @Description("Verify user can't update the player")
    public void verifyUnsuccessfulPlayerUpdating() {
        var randomPlayerId = getRandomPlayerId();
        var player = playerServiceSteps.getPlayerById(new PlayerIdModel(randomPlayerId), SC_OK);

        playerServiceSteps.updatePlayerInfo(customEditor, randomPlayerId,
                new UpdatePlayerRequestDTO(ageForUpdating, genderForUpdating, roleForUpdating, screenNameForUpdating, loginForUpdating, passwordForUpdating), SC_FORBIDDEN);

        var updatedPlayer = playerServiceSteps.getPlayerById(new PlayerIdModel(randomPlayerId), SC_OK);

        assertEquals(updatedPlayer, player, "Player`s info should be equal");
    }

    @Test
    @Description("Verify unsuccessful deletion of the player")
    public void verifyUnsuccessfulDeletionOfPlayer() {
        var playersBeforeDeletion = playerServiceSteps
                .getAllPlayers()
                .getPlayers();

        var actualPlayerId = playersBeforeDeletion
                .stream()
                .findFirst()
                .get()
                .getId();

        playerServiceSteps.deletePlayer(customEditor, actualPlayerId, SC_FORBIDDEN);

        var playersAfterDeletion = playerServiceSteps
                .getAllPlayers()
                .getPlayers();

        var actualPlayersId = playersAfterDeletion
                .stream()
                .map(PlayerWithIdModel::getId)
                .collect(Collectors.toList());

        int countDiff = getDifference(playersBeforeDeletion.size(), playersAfterDeletion.size());

        assertListContainsObject(actualPlayersId, actualPlayerId, "Player should be present in DB");
        assertEquals(countDiff, 0, "Difference between players counts before and after deletion should be 0");
    }

    @Test
    @Description("Test verifies successful deletion of the player")
    public void verifyPlayerWasDeleted() {
        var playerId = playerServiceSteps
                .getAllPlayers()
                .getPlayers()
                .stream()
                .findAny()
                .get()
                .getId();

        playerServiceSteps.deletePlayer(baseEditor, playerId, SC_OK);

        assertTrue(getAllPlayersWithIdList(playerId).isEmpty(), "There should be 0 players with id: " + createdPlayer.getId());
    }

    private List<PlayerWithIdModel> getAllPlayersWithIdList(long id) {
        return playerServiceSteps
                .getAllPlayers()
                .getPlayers()
                .stream()
                .filter(player -> player.getId() == id)
                .collect(Collectors.toList());
    }

    private List<PlayerWithIdModel> getPlayersByParams(int age, String gender, String role, String
            screenName) {
        return playerServiceSteps
                .getAllPlayers()
                .getPlayers()
                .stream()
                .filter(player -> player.getAge() == age)
                .filter(player -> player.getGender().equals(gender))
                .filter(player -> player.getRole().equals(role))
                .filter(player -> player.getScreenName().equals(screenName))
                .collect(Collectors.toList());
    }

    private long getRandomPlayerId() {
        return playerServiceSteps
                .getAllPlayers()
                .getPlayers()
                .stream()
                .findAny()
                .get()
                .getId();
    }
}
