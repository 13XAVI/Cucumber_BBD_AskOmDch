package steps.product;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import hooks.Hook;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ProductSteps {

    @Given("I am on the AskOmDch Store page To Browse Product")
    public void iAmOnTheStorePage() {
        Hook.homePage.clickToStorePage();
    }

    @When("I enter a search keyword {string} in the search box")
    public void iEnterASearchKeywordInTheSearchBox(String keyword) {
        Hook.storePage.enterSearch(keyword);
    }

    @And("I click the search button")
    public void iClickTheSearchButton() {
        Hook.storePage.clickSearch();
    }

    @Then("I should see only products that contain {string} in their name or category")
    public void onlyProductsThatContainNameOrDescription(String productName) {
        boolean allProduct = Hook.storePage.doesProductsContainKeyword(productName);
        assertTrue(allProduct, "Some products do not contain keyword: " + productName);
    }


    @Then("I should see a message {string}")
    public void searchForInvalidProduct(String expectedMessage) {
        String actualMessage = Hook.storePage.getSearchResultsMessage();
        assertEquals(actualMessage, expectedMessage, "The displayed message is not as expected.");
    }



}
