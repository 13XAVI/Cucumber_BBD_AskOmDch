package com.tau.Cucumber_AskOmDch;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By cartCount = By.cssSelector(".ast-cart-menu-wrap .count");
    private By cartItems = By.cssSelector("tr.cart_item");

    private By checkoutButton = By.cssSelector("a.checkout-button.button.alt.wc-forward");
    private  By errorMessage =  By.cssSelector(".woocommerce-message");

    public CartPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }



    public void waitForCartCountToBe(int expectedCount) {
        wait.until(driver -> {
            int actual = getCartCount();
            return actual == expectedCount;
        });
    }

    public String getEmptyCartMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-empty.woocommerce-info")))
                .getText()
                .trim();
    }


    public int getHeaderCartCount() {
        return Integer.parseInt(
                wait.until(ExpectedConditions.visibilityOfElementLocated(cartCount))
                        .getText()
                        .trim()
        );
    }

    public void ClickOnCheckoutButton() {
        driver.findElement(checkoutButton).click();
    }


    public int getCartCount() {
        String countText = wait
                .until(ExpectedConditions.visibilityOfElementLocated(cartCount))
                .getText()
                .trim();

        return Integer.parseInt(countText);
    }

    public String getRemovalConfirmationMessage() {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return messageElement.getText().split("Undo\\?")[0].trim();
    }


    public void removeProductNameAndWaitForDecrement(String productName) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItems));

        int previousCount = getHeaderCartCount();

        WebElement itemToRemove = null;

        List<WebElement> items = driver.findElements(cartItems);

        for (WebElement item : items) {
            String name = item.findElement(By.cssSelector(".product-name a"))
                    .getText()
                    .trim();


            if (name.equalsIgnoreCase(productName)) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove == null) {

            for (WebElement item : items) {
                String name = item.findElement(By.cssSelector(".product-name a"))
                        .getText()
                        .trim();
            }
            throw new RuntimeException("Product not found in cart: " + productName);
        }

        WebElement removeButton = itemToRemove.findElement(By.cssSelector("a.remove"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", removeButton);

        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
        wait.until(ExpectedConditions.stalenessOf(itemToRemove));

        int expectedCount = previousCount - 1;
        wait.until(driver -> getHeaderCartCount() == expectedCount);
    }


    public void clearCartIfNotEmpty() {

        while (driver.findElements(cartItems).size() > 0) {

            WebElement firstItem = driver.findElements(cartItems).get(0);

            firstItem.findElement(By.cssSelector("a.remove")).click();

            wait.until(ExpectedConditions.stalenessOf(firstItem));
        }

        waitForCartCountToBe(0);
    }

}

