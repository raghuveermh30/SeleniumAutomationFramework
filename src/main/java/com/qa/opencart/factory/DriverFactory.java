package com.qa.opencart.factory;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.exceptions.FrameworkException;
import io.qameta.allure.Description;
import io.qameta.allure.Step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class DriverFactory {

    WebDriver driver;
    Properties properties;
    OptionsManager optionsManager;
    public static String highlight;
    public static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);

    //Thread Local means when ever we are generating the thread, we will be having the local copy of the driver.

    /* Advantage of Thread Local
     * When we have 300-400 test cases in parallel mode then there is high chance of getting driver clash error. This will avoid
     * When report is not coming properly and there are high chances of getting overridden when its in parallel mode execution
     * ThreadLocal is the generic, we can use it for anything, Here we are using for WebDriver
     * thread-count = 3, Here every thread will get the WebDriver local copy. Every thread are running independent
     * Here, we won't get DeadLock exception
     * We need to implement when we test cases will be executed in the parallel mode execution
     */

    @Step("Initialise the driver using properties: {0}")
    public WebDriver initDriver(Properties properties) {
        String browserName = properties.getProperty("browser");
        highlight = properties.getProperty("highlight");
        //System.out.println("Browser Name is : " + browserName);
        log.info("Browser Name is : {}", browserName); //Information on the console with the timestamp
        optionsManager = new OptionsManager(properties);
        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                driverThreadLocal.set(new ChromeDriver(optionsManager.getChromeOptions()));
                optionsManager.getChromeOptions();
                //driver = new ChromeDriver(optionsManager.getChromeOptions());
                break;
            case "firefox":
                driverThreadLocal.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
                //driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
                break;
            case "edge":
                driverThreadLocal.set(new EdgeDriver(optionsManager.getEdgeOptions()));
                //driver = new EdgeDriver(optionsManager.getEdgeOptions());
                break;
            case "safari":
                driverThreadLocal.set(new SafariDriver());
                //driver = new SafariDriver();
                break;
            default:
                // System.out.println("Please Pass the right browser.." + browserName);
                log.error("Please Pass the right browser..{}", browserName);
                throw new FrameworkException("===Invalid Browser Name===");
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().get(properties.getProperty("url"));
        return getDriver();
    }

    /* Getting the Driver using the thread local
     *
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /* This method is used to read the properties from Properties.file
     *
     *
     * @return
     */
    //supply the environment name using maven command
    //mvn clean install -Denv="stage"
    public Properties initProp() {
        String envName = System.getProperty("env");
        // System.out.println("running test suite on env: " + envName);
        log.info("running test suite on env: {}", envName);
        FileInputStream ip = null;
        properties = new Properties();

        try {
            if (envName == null) {
                // System.out.println("no env is passed, hence running test suite on qa env..");
                log.warn("no env is passed, hence running test suite on qa env..");
                ip = new FileInputStream(AppConstants.QA_CONFIG_PROP_FILE_PATH);
            } else {
                switch (envName.trim().toLowerCase()) {
                    case "qa":
                        log.debug("Running the test cases in the QA Environment");
                        ip = new FileInputStream(AppConstants.QA_CONFIG_PROP_FILE_PATH);
                        break;
                    case "dev":
                        log.debug("Running the test cases in the DEV Environment");
                        ip = new FileInputStream(AppConstants.DEV_CONFIG_PROP_FILE_PATH);
                        break;
                    case "stage":
                        log.debug("Running the test cases in the STAGE Environment");
                        ip = new FileInputStream(AppConstants.STAGE_CONFIG_PROP_FILE_PATH);
                        break;
                    case "uat":
                        log.debug("Running the test cases in the UAT Environment");
                        ip = new FileInputStream(AppConstants.UAT_CONFIG_PROP_FILE_PATH);
                        break;
                    default:
                        // System.out.println("plz pass the right env name..." + envName);
                        log.error("plz pass the right env name...{}", envName);
                        throw new FrameworkException("===INVALID ENV===");
                }
            }

            properties.load(ip);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


    /*
     * takescreenshot
     */
    @Step("getScreenshot")
    public static String getScreenshot() {
        log.info(">>>getScreenshot()");
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
        String path = System.getProperty("user.dir") + "/screenshot/" + "_" + System.currentTimeMillis() + ".png";
        File destination = new File(path);

        try {
            FileHandler.copy(srcFile, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    @Step("getScreenshotFile")
    public static File getScreenshotFile() {
        log.info(">>>getScreenshotFile()");
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
        return srcFile;
    }

    @Step("getScreenshotByte")
    public static byte[] getScreenshotByte() {
        log.info(">>>getScreenshotByte()");
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

    }

    @Step("getScreenshotBase64")
    public static String getScreenshotBase64() {
        log.info(">>>getScreenshotBase64()");
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

    }
}
