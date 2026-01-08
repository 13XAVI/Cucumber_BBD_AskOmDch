package steps;

import com.tau.Cucumber_AskOmDch.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Hook {

    public static WebDriver driver;
    public static HomePage homePage;
    public static AccountPage accountPage;
    public  static StorePage storePage;
    public  static CartPage cartPage;
    public static CheckoutPage checkoutPage;

    @Before
    public void setUp() {
        System.setProperty(
                "webdriver.chrome.driver",
                "resources/chromedriver-win64/chromedriver.exe"
        );

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://askomdch.com");

        homePage = new HomePage(driver);
        accountPage = new AccountPage(driver);
        storePage = new StorePage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);

    }

    @After
    public void closeBrowser() {
        driver.quit();
    }
}
