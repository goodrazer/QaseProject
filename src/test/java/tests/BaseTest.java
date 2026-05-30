package tests;

import com.codeborne.selenide.*;
import jdk.jfr.Description;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.PropertyReader;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {

    protected String validUser = System.getProperty("User", PropertyReader.getProperty("user"));
    protected String validPassword = System.getProperty("Password", PropertyReader.getProperty("password"));

    @BeforeMethod
    @Description("Открытие браузера")
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.timeout = 20000;
        Configuration.baseUrl = "https://app.qase.io";
        Configuration.clickViaJs = true;
        Configuration.headless = false;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.setCapability("unhandledPromptBehavior", UnexpectedAlertBehaviour.ACCEPT);
        options.addArguments("--headless");
        Configuration.browserCapabilities = options;
    }

    @AfterMethod(alwaysRun = true, description = "Закрытие браузера")
    @Description("Закрытие браузера")
    public void tearDown() {
        closeWebDriver();
    }
}