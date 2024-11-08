package com.testform;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.hamcrest.CoreMatchers.containsString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LoginFormTests {
    private static final String INPUT_ID_USER_NAME = "//input[@id='user-name']";
    private static final String INPUT_ID_PASSWORD = "//input[@id='password']";
    private static final String INPUT_ID_LOGIN_BUTTON = "//input[@id='login-button']";
    private static final String DATA_TEST_ERROR = "//h3[@data-test='error']";
    private static final String DIV_CLASS_LOGIN_CREDENTIALS = "//div[@class='login_credentials']";

    private WebDriver driver;
    private WebElement usernameField;
    private WebElement passwordField;
    private WebElement loginButton;
    private WebElement loginCredentials;

    private static final Logger logger = LogManager.getLogger(LoginFormTests.class);
    private static final String webLink = "https://www.saucedemo.com/";
    private BrowserType browser;

    public LoginFormTests(BrowserType browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.stream(BrowserType.values())
                     .map(browser -> new Object[]{browser})
                     .toList();
    }

    @Before
    public void setUp() {
        driver = WebDriverFactory.createWebDriver(browser, webLink);
        usernameField = getWebElement(INPUT_ID_USER_NAME);
        passwordField = getWebElement(INPUT_ID_PASSWORD);
        loginButton = getWebElement(INPUT_ID_LOGIN_BUTTON);
        loginCredentials = getWebElement(DIV_CLASS_LOGIN_CREDENTIALS);
    }

    private WebElement getWebElement(String xPathExspression) {
        return driver.findElement(By.xpath(xPathExspression));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginWithEmptyCredentials() {
        
        logger.info("Starting testLoginWithEmptyCredentials on " + browser);

        setDefaultLoginFields();
        clearFieldText(usernameField);
        clearFieldText(passwordField);
        loginButton.click();

        assertErrorMessage("Username is required");
        logger.info("Completed testLoginWithEmptyCredentials on " + browser);
    }

    @Test
    public void testLoginWithEmptyPassword() {
        
        logger.info("Starting testLoginWithEmptyPassword on " + browser);

        setDefaultLoginFields();
        clearFieldText(passwordField);
        loginButton.click();

        assertErrorMessage("Password is required");
        logger.info("Completed testLoginWithEmptyCredentials on " + browser);
    }


    @Test
    public void testLoginWithValidCredentials() {
        
        logger.info("Starting testLoginWithValidCredentials on " + browser);

        usernameField.sendKeys(loginCredentials.getText().split("\n")[1]);
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        assertTitle("Swag Labs");
        logger.info("Completed testLoginWithValidCredentials on " + browser);
    }

    private void assertTitle(String title) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs(title));
        Assert.assertEquals(title, driver.getTitle());
    }
     
    private void assertErrorMessage(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DATA_TEST_ERROR)));
        Assert.assertThat(errorMessage.getText(), containsString(expectedMessage));
    }

    private void clearFieldText(WebElement passwordField) {
        passwordField.sendKeys(Keys.CONTROL + "a");
        passwordField.sendKeys(Keys.DELETE);
    }

    private void setDefaultLoginFields() {
        usernameField.sendKeys("anyUsername");
        passwordField.sendKeys("anyPassword");
    }
}