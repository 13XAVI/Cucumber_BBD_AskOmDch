package steps.cart;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import hooks.Hook;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class CartSteps {

    private int initialCartCount;

    @Given("I am on the AskOmDch Store page")
    public void iAmOnTheStorePage() {
        Hook.homePage.clickToStorePage();
    }

    @When("I add {int} products to the cart")
    public void iAddProductsToTheCart(int items) {
        int initialCount = Hook.cartPage.getHeaderCartCount();

        for (int i = 1; i <= items; i++) {
            Hook.storePage.clickToAddToCart();
            Hook.cartPage.waitForCartCountToBe(initialCount + i);
        }
    }

    @When("I should be on the cart page")
    public void iOpenTheCartPage() {
        Hook.storePage.clickToViewCart();
    }

    @Then("I added a product to the cart")
    public void iAddedAProductToTheCart() {
        Hook.storePage.clickToAddToCart();
    }

    @Then("I should see item in the cart")
    public void iShouldSeeItemInTheCart() {
        Hook.storePage.clickToViewCart();
    }

    @Given("I have products in the cart")
    public void iHaveProductsInTheCart(List<String> productNames) {
        Hook.storePage.clickToViewCart();
        Hook.cartPage.clearCartIfNotEmpty();
        Hook.homePage.clickToStorePage();

        for (String productName : productNames) {
            Hook.storePage.addProductToCartByName(productName);
        }

        Hook.storePage.clickToViewCart();

        initialCartCount = Hook.cartPage.getHeaderCartCount();

        assertEquals(initialCartCount, productNames.size(),
                "Initial cart count mismatch. Expected: " + productNames.size() +
                        ", Found: " + initialCartCount);
    }

    @Then("the initial cart count should be {int}")
    public void theInitialCartCountShouldBe(int expectedCount) {
        assertEquals(initialCartCount, expectedCount, "Initial cart count mismatch");
    }

    @When("I remove the product {string}")
    public void iRemoveTheProduct(String productName) {
        Hook.cartPage.removeProductAndWaitForDecrement(productName);
    }

    @Then("the cart count should be {int}")
    public void cartCountShouldBe(int expectedCount) {
        int actualCount = Hook.cartPage.getHeaderCartCount();
        assertEquals(actualCount, expectedCount, "Cart count did not match");
    }

    @Then("I should see the product removed confirmation for {string}")
    public void iShouldSeeProductRemovedConfirmation(String productName) {
        String expectedMessage = "“" + productName + "” removed.";

        String actualMessage = Hook.cartPage.getRemovalConfirmationMessage()
                .replaceAll("\\s+", " ")
                .trim();

        assertEquals(actualMessage, expectedMessage,
                "Product removal confirmation mismatch");
    }

    @Then("I should see the empty cart message when cart is empty")
    public void iShouldSeeEmptyCartMessageWhenCartIsEmpty() {
        int count = Hook.cartPage.getHeaderCartCount();

        if (count == 0) {
            String expectedMessage = "Your cart is currently empty.";
            String actualMessage = Hook.cartPage.getEmptyCartMessage()
                    .replaceAll("\\s+", " ")
                    .trim();

            assertEquals(actualMessage, expectedMessage,
                    "Empty cart message mismatch");
        }
    }
}
