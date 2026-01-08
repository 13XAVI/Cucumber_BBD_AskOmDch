package steps.account;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import steps.Hook;
import utils.UniqueFields;


import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RegistrationSteps {

    @Given("I am on the AskOmDch Account page")
    public void iAmOnTheAskOmDchAccountPage() {
        Hook.homePage.clickAccount();
    }



    @When("I register with valid credentials")
    public void iRegisterWithValidCredentials(DataTable table) {

        for (Map<String, String> user : table.asMaps(String.class, String.class)) {
            String username = UniqueFields.generateUniqueUsername(user.get("username"));
            String email = UniqueFields.generateRandomEmail(user.get("email"));
            Hook.accountPage
                    .enterRegUsername(username)
                    .enterRegPassword(user.get("password"))
                    .enterRegEmail(email)
                    .clickRegister();
        }
    }


    @Then("my account should be created successfully")
    public void myAccountShouldBeCreatedSuccessfully() {
        assertTrue(
                Hook.accountPage.getWelcomeText().contains("Hello"),
                "Account was not created successfully"
        );
    }

    @Then("I should see a welcome message")
    public void iShouldSeeAWelcomeMessage() {
        assertTrue(
                Hook.accountPage.getWelcomeText().contains("Log out"),
                "Welcome message not displayed"
        );
    }


    @When("I register with existing email {string}")
    public void iRegisterWithExistingEmail(String email) {
        Hook.accountPage
                .enterRegUsername("TestUser")
                .enterRegPassword("Test@123")
                .enterRegEmail(email)
                .clickRegister();
    }


    @When("I submit the registration form without {string}")
    public void iSubmitTheRegistrationFormWithout(String field) {

        if (!field.equals("username")) {
            Hook.accountPage.enterRegUsername(
                    UniqueFields.generateUniqueUsername("TestUser")
            );
        }

        if (!field.equals("email")) {
            Hook.accountPage.enterRegEmail(
                    UniqueFields.generateRandomEmail("tester@gmail.com")
            );
        }

        if (!field.equals("password")) {
            Hook.accountPage.enterRegPassword("Test@123");
        }

        Hook.accountPage.clickRegister();
    }


    /** Invalid Email Format */

    @When("I register with invalid email format {string}")
    public void iRegisterWithInvalidEmailFormat(String email) {
        Hook.accountPage
                .enterRegUsername("TestUser")
                .enterRegPassword("Test@123")
                .enterRegEmail(email)
                .clickRegister();
    }

    /** Common Assertions */

    @Then("I should see the error message {string}")
    public void iShouldSeeTheErrorMessage(String expectedMessage) {
        String actualMessage = Hook.accountPage.getErrorMessage().trim();
        assertEquals(actualMessage, expectedMessage);
    }

    @Then("I should remain on the account page")
    public void iShouldRemainOnTheAccountPage() {
        assertTrue(
                Hook.accountPage.getAccountHeader().contains("Account"),
                "User navigated away from account page"
        );
    }
}
