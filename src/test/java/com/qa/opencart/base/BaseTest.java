package com.qa.opencart.base;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.*;
import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.Properties;

//@Listeners(ChainTestListener.class)
public class BaseTest {

    WebDriver driver;
    DriverFactory driverFactory;
    protected Properties properties;
    protected LoginPage loginPage;
    protected HomePage homePage;
    protected SearchResultPage searchResultPage;
    protected ProductInfoPage productInfoPage;
    protected LogoutPage logoutPage;
    protected CommonSpace commonSpace;

    @Parameters({"browser"})
    @BeforeTest(description = "Initialise the driver and properties")
    public void setup(String browserName) {
        driverFactory = new DriverFactory();
        properties = driverFactory.initProp();
        if (browserName != null) {
            properties.setProperty("browser", browserName);
        }
        driver = driverFactory.initDriver(properties);
        loginPage = new LoginPage(driver);
        commonSpace = new CommonSpace(driver);

//        ChainPluginService.getInstance().addSystemInfo("Build#", "1.0");
//        ChainPluginService.getInstance().addSystemInfo("Headless#", properties.getProperty("headless"));
//        ChainPluginService.getInstance().addSystemInfo("Incognito#", properties.getProperty("incognito"));
//        ChainPluginService.getInstance().addSystemInfo("Author", "Naveen Automation Labs");
    }

    @AfterMethod(description = "Taking the screenshot only when test case failed")
    public void attachScreenShot(ITestResult result) {
        if (!result.isSuccess()) {
            //  ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
            //  ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
            //   ChainTestListener.embed(DriverFactory.getScreenshotByte(), "image/png");
            ChainTestListener.embed(DriverFactory.getScreenshotBase64(), "image/png");
        }
    }

    @AfterTest(description = "tearDown: Closing the browser")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
