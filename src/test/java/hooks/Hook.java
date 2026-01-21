package hooks;

import com.tau.Cucumber_AskOmDch.*;
import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hook {

    private WebDriver driver;

    public static HomePage homePage;
    public static AccountPage accountPage;
    public static StorePage storePage;
    public static CartPage cartPage;
    public static CheckoutPage checkoutPage;

    @Before
    public void before() {
        driver = DriverFactory.initializeDriver("chrome");
        homePage = new HomePage(driver);
        accountPage = new AccountPage(driver);
        storePage = new StorePage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }


    @After
    public void after() {
        if (driver != null) {
            driver.quit();
        }
    }
}
