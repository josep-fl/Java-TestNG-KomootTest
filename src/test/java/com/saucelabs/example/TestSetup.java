package com.saucelabs.example;

import com.saucelabs.example.util.ResultReporter;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;

/**
 * Created by josepforonda on 06/03/2017.
 */
public class TestSetup {

    private AndroidDriver driver;
    private ResultReporter reporter;

    @BeforeMethod
    public void setUp() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("testobject_api_key", System.getenv("TESTOBJECT_API_KEY"));
        capabilities.setCapability("testobject_device", System.getenv("TESTOBJECT_DEVICE"));

        driver = new AndroidDriver(new URL("http://appium.testobject.com/wd/hub"), capabilities);
        reporter = new ResultReporter();

    }


    @Test
    public void emailLoginTest() {

        String invalidEmail = "invalid.email";
        String invalidPassword = "123";
        String expectedErrorMessage = "Email address invalid.";

//    public void twoPlusTwoOperation() throws MalformedURLException {

        /* Get the elements. */
//        MobileElement buttonTwo = (MobileElement)(driver.findElement(By.id("net.ludeke.calculator:id/digit2")));
//        MobileElement buttonPlus = (MobileElement)(driver.findElement(By.id("net.ludeke.calculator:id/plus")));
//        MobileElement buttonEquals = (MobileElement)(driver.findElement(By.id("net.ludeke.calculator:id/equal")));
//        MobileElement resultField = (MobileElement)(driver.findElement(By.xpath("//android.widget.EditText[1]")));

        /* Add two and two. */
//        buttonTwo.click();
//        buttonPlus.click();
//        buttonTwo.click();
//        buttonEquals.click();

        /* Check if within given time the correct result appears in the designated field. */
//        (new WebDriverWait(driver, 30)).until(ExpectedConditions.textToBePresentInElement(resultField, "4"));


        MobileElement loginLink = (MobileElement) driver.findElement(By.id("de.komoot.android:id/textview_login"));
        loginLink.click();

        MobileElement emailField = (MobileElement) (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.id("de.komoot.android:id/edittext_email")));
        emailField.sendKeys(invalidEmail);

        MobileElement passwordField = (MobileElement) driver.findElement(By.id("de.komoot.android:id/edittext_password"));
        passwordField.sendKeys(invalidPassword);

        MobileElement loginButton = (MobileElement) driver.findElement(By.id("de.komoot.android:id/button_login"));
        loginButton.click();

        MobileElement errorMessage = (MobileElement) (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.id("android:id/message")));
        assertEquals(errorMessage.getText(), expectedErrorMessage);

    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        boolean success = result.isSuccess();
        String sessionId = driver.getSessionId().toString();

        reporter.saveTestStatus(sessionId, success);
        driver.quit();
    }

}
