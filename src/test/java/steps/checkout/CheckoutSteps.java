package steps.checkout;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import steps.Hook;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CheckoutSteps {
    @Given("I have a product in the cart")
    public void iAmHaveProductInCart() {
        Hook.homePage.clickToStorePage();
        Hook.storePage.clickToAddToCart();
    }

    @And("I am on the checkout page")
    public void iAmOnTheCheckoutPage() {
        Hook.storePage.clickToViewCart();
        Hook.cartPage.ClickOnCheckoutButton();

    }

    @When("I complete checkout with valid billing details:")
    public void iCompleteCheckoutWithValidBillingDetails(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        Hook.checkoutPage
                .enterFirstName(data.get("First name"))
                .enterLastName(data.get("Last name"))
                .enterCompany(data.get("Company"))
                .enterAddress(data.get("Street"))
                .enterCity(data.get("City"))
                .enterZip(data.get("Postcode / ZIP"))
                .enterPhone(data.get("Phone"))
                .enterEmail(data.get("Email")).placeOrder();
    }

    @When("I attempt to place an order without {string}")
    public void iAttemptToPlaceAnOrderWithout(String field) {

        Hook.checkoutPage
                .enterFirstName("Tresor")
                .enterLastName("Xavier")
                .selectCountry("Rwanda")
                .enterAddress("Gasabo")
                .enterCity("Kigali")
                .selectState("Kigali City")
                .enterZip("000")
                .enterEmail("test@gmail.com");

        switch (field.toLowerCase()) {
            case "billing zip":
                Hook.checkoutPage.clearZip();
                break;
            case "first name":
                Hook.checkoutPage.clearFirstName();
                break;
            case "last name":
                Hook.checkoutPage.clearLastName();
                break;
            case "street address":
                Hook.checkoutPage.clearAddress();
                break;
            case "town / city":
                Hook.checkoutPage.clearCity();
                break;
            case "state":
                Hook.checkoutPage.clearState();
                break;
            case "country":
                Hook.checkoutPage.clearCountry();
                break;
            case "postcode / zip":
                Hook.checkoutPage.clearPostCode();
                break;

            case "email":
                Hook.checkoutPage.clearEmail();
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + field);
        }

        Hook.checkoutPage.placeOrder();
    }

    @Then("I should see the validation message {string}")
    public void iShouldSeeTheValidationMessage(String expectedMessage) {
        List<WebElement> errors = Hook.checkoutPage.getCheckoutErrorMessages();

        boolean found = errors.stream().anyMatch(e -> e.getText().trim().contains(expectedMessage));
        assertTrue(found, "Expected validation message not found: " + expectedMessage);
    }

    @Then("I should see the order summary")
    public void iShouldSeeTheOrderSummary() {
        List<String> summary = Hook.checkoutPage.getOrderSummary();
        assertTrue(summary.size() > 0, "Order summary is empty.");
    }

    @Then("the order total should be correct")
    public void theOrderTotalShouldBeCorrect() {
        List<String> summary = Hook.checkoutPage.getOrderSummary();
        String total = String.valueOf(summary.stream()
                .filter(line -> line.toLowerCase().startsWith("total"))
                .findFirst());
        assertTrue(total.contains("$") && total.matches(".*\\d.*"),
                "Order total seems incorrect: " + total);
    }



    @Then("the order should be placed successfully")
    public void orderShouldBePlacedSuccessfully() {
        assertTrue(Hook.checkoutPage.isConfirmationPageDisplayed());
        assertEquals(
                Hook.checkoutPage.getOrderSuccessMessage(),
                "Thank you. Your order has been received."
        );
    }


}
