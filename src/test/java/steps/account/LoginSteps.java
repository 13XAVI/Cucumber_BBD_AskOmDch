package steps.account;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import steps.Hook;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginSteps {

    @Given("I am on the AskOmDch Account page on login")
    public void iAmOnTheAskOmDchAccountPage() {
        Hook.homePage.clickAccount();
    }


    /** Valid login scenario */
    @When("I login with username {string} and password {string}")
    public void loginWithUsernameAndPassword(String username, String password) {


        Hook.accountPage.enterLoginUsername(username)
                .enterLoginPassword(password)
                .clickLogin();
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {

        String welcomeText = Hook.accountPage.getWelcomeText().trim();
        assertTrue(welcomeText.contains("Welcome") || welcomeText.contains("Hello"),
                "User was not logged in successfully. Actual message: " + welcomeText);


        String logoutText = Hook.accountPage.getWelcomeText().trim();
        assertTrue(logoutText.contains("Log out"),
                "Logout link not visible after login. Actual text: " + logoutText);

    }

    @Then("I should see {string} and {string}")
    public void iShouldSeeWelcomeAndLogout(String expectedWelcome, String expectedLogout) {
        String actualWelcome = Hook.accountPage.getWelcomeText().trim();
        String actualLogout = Hook.accountPage.getWelcomeText().trim();

        assertTrue(actualWelcome.contains(expectedWelcome),
                "Expected welcome text: " + expectedWelcome + ", but got: " + actualWelcome);
        assertTrue(actualLogout.contains(expectedLogout),
                "Expected logout text: " + expectedLogout + ", but got: " + actualLogout);
    }

    @When("I attempt to login with username {string} and password {string}")
    public void loginWithInvalidCredentials(String username, String password) {

        if (username != null && !username.isEmpty()) {
            Hook.accountPage.enterLoginUsername(username);
        }

        if (password != null && !password.isEmpty()) {
            Hook.accountPage.enterLoginPassword(password);
        }
        Hook.accountPage.clickLogin();
    }

    @Then("I should see the login error message {string}")
    public void iShouldSeeErrorMessage(String expectedMessage) {
        String actualMessage = Hook.accountPage.getErrorMessage().trim();
        assertEquals(actualMessage, expectedMessage,
                "Expected error message: " + expectedMessage + ", but got: " + actualMessage);
    }
}
