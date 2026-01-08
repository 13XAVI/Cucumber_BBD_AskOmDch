package com.tau.Cucumber_AskOmDch;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;
    // Billing
    private By firstName = By.id("billing_first_name");
    private By lastName = By.id("billing_last_name");
    private By company = By.id("billing_company");
    private By country = By.id("billing_country");
    private By address = By.id("billing_address_1");
    private By city = By.id("billing_city");
    private By state = By.id("billing_state");
    private By zip = By.id("billing_postcode");
    private By phone = By.id("billing_phone");
    private By email = By.id("billing_email");

    // Validation errors
    private By checkoutErrors = By.cssSelector(".woocommerce-error li");

    // Payment methods
    private By bacsPayment = By.id("payment_method_bacs");
    private By codPayment = By.id("payment_method_cod");

    // Place order
    private By placeOrderButton = By.id("place_order");
    private By bacsPaymentLoader = By.cssSelector(".wc_payment_method.payment_method_bacs .loading");


    // Success message
    private By orderSuccessMessage = By.cssSelector(".woocommerce-notice--success");

    // Order summary table
    private By orderSummaryRows = By.cssSelector(".shop_table.order_details tbody tr");
    private By selectedPaymentMethod = By.cssSelector("input[name='payment_method']:checked");




    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public CheckoutPage enterFirstName(String user_firstName) {
        driver.findElement(firstName).sendKeys(user_firstName);
        return this;
    }
    public CheckoutPage enterLastName(String user_lastName) {
        driver.findElement(lastName).sendKeys(user_lastName);
        return this;
    }
    public CheckoutPage enterCompany(String user_lastName) {
        driver.findElement(company).sendKeys(user_lastName);
        return this;
    }
    public CheckoutPage enterCountry(String user_country) {
        driver.findElement(country).sendKeys(user_country);
        return this;
    }
    public CheckoutPage selectCountry(String countryName) {
        selectFromDropdown(country, countryName);
        return this;
    }
    public CheckoutPage enterAddress(String user_address) {
        driver.findElement(address).sendKeys(user_address);
        return this;
    }
    public CheckoutPage enterCity(String user_city) {
        driver.findElement(city).sendKeys(user_city);
        return this;
    }
    public CheckoutPage selectState(String stateName) {
        selectFromDropdown(state, stateName);
        return this;
    }

    public CheckoutPage enterZip(String user_zip) {
        driver.findElement(zip).sendKeys(user_zip);
        return this;
    }
    public CheckoutPage enterPhone(String user_phone) {
        driver.findElement(phone).sendKeys(user_phone);
        return this;
    }
    public CheckoutPage enterEmail(String user_email) {
        driver.findElement(email).sendKeys(user_email);
        return this;
    }

    private void selectFromDropdown(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions
                .presenceOfElementLocated(locator));

        String tagName = element.getTagName();

        if (tagName.equalsIgnoreCase("select")) {
            new Select(element).selectByVisibleText(value);
        } else {
            element.click();
            element.sendKeys(value);
            element.sendKeys(Keys.ENTER);
        }
    }



    public CheckoutPage placeOrder() {
        waitForLoaderToDisappear();
        waitingClick(placeOrderButton);

        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage),
                ExpectedConditions.visibilityOfElementLocated(checkoutErrors)
        ));

        return this;
    }

    public String getOrderSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage)).getText();
    }


    private void waitingClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        try { Thread.sleep(200); } catch (InterruptedException ignored) {}

        element.click();
    }

    private void waitForLoaderToDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".blockUI.blockOverlay")
        ));
    }


    public void clearFirstName() {
        driver.findElement(firstName).clear();
    }

    public void clearLastName() {
        driver.findElement(lastName).clear();
    }

    public void clearAddress() {
        driver.findElement(address).clear();
    }

    public void clearCity() {
        driver.findElement(city).clear();
    }

    public void clearZip() {
        driver.findElement(zip).clear();
    }

    public void clearCountry() {
        driver.findElement(country).clear();
    }

    public void clearState() {
        driver.findElement(state).clear();
    }
    public void clearEmail() {
        driver.findElement(email).clear();
    }
    public void clearPostCode(){
        driver.findElement(zip).clear();
    }

    public List<WebElement> getCheckoutErrorMessages() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(checkoutErrors));
    }


    public List<String> getOrderSummary() {
        return driver.findElements(orderSummaryRows).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }


    public CheckoutPage fillValidBillingDetails() {
        enterFirstName("Tresor");
        enterLastName("Xavier");
        selectCountry("Rwanda");
        enterAddress("Gasabo");
        enterCity("Kigali");
        selectState("Kigali Province");
        enterZip("000");
        enterPhone("+250781605346");
        enterEmail("daid@gmail.com");
        return this;
    }

    public boolean isConfirmationPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage)).isDisplayed();
    }


}

