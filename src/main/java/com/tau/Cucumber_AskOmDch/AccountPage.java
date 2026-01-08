package com.tau.Cucumber_AskOmDch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private By accountHeader = By.cssSelector(".wp-block-cover__inner-container h1");

    private By username = By.id("username");
    private By password = By.id("password");
    private By loginButton = By.name("login");

    private By regUsername = By.id("reg_username");
    private By regEmail = By.id("reg_email");
    private By regPassword = By.id("reg_password");
    private By registerButton = By.name("register");

    private By welcomeText = By.xpath(
            "//p[contains(text(),'Hello')]");
    private By errorMessage = By.cssSelector(".woocommerce-error li");
    public AccountPage enterLoginUsername(String value) {
        driver.findElement(username).sendKeys(value);
        return this;
    }

    public AccountPage enterLoginPassword(String value) {
        driver.findElement(password).sendKeys(value);
        return this;
    }

    public AccountPage clickLogin() {
        driver.findElement(loginButton).click();
        return this;
    }

    public AccountPage enterRegUsername(String value) {
        driver.findElement(regUsername).sendKeys(value);
        return this;
    }


    public AccountPage enterRegEmail(String value) {
        driver.findElement(regEmail).sendKeys(value);
        return this;
    }

    public AccountPage enterRegPassword(String value) {
        driver.findElement(regPassword).sendKeys(value);
        return this;
    }

    public AccountPage clickRegister() {
        driver.findElement(registerButton).click();
        return this;
    }


    public String getWelcomeText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeText)).getText();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public String getAccountHeader() {
        return driver.findElement(accountHeader).getText();
    }


}

