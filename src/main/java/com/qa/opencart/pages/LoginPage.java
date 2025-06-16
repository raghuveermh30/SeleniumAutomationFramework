package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;
    private ElementUtil elementUtil;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(driver);
    }

    //1. By Locators --> Page Locators --> Object Repository
    private By emailId = By.id("input-email");
    private By password = By.id("input-password");
    private By loginBtn = By.xpath("//input[@value='Login']");
    private By forgotPwd = By.linkText("Forgotten Password");

    //2. Public Page actions ---> Methods (features)
    @Step("getLoginPageTitle")
    public String getLoginPageTitle() {
        //String title = driver.getTitle(); --> No need this line
        String title = elementUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_TIME_OUT);
        System.out.println("Login Page Title --> " + title);
        ChainTestListener.log("Verifying Login Page Title Test " + title);
        return title;
    }

    @Step("getLoginPageUrl")
    public String getLoginPageUrl() {
        // String url = driver.getCurrentUrl();
        String url = elementUtil.waitForUrlContains(AppConstants.LOGIN_FRACTION_VALUE, AppConstants.DEFAULT_TIME_OUT);
        System.out.println("Login Page Title --> " + url);
        ChainTestListener.log("Verifying Login URL Title Test " + url);
        return url;
    }

    @Step("checking forgot password link is displayed")
    public boolean isForgotPassLinkExist() {
        return elementUtil.doElementIsDisplayed(forgotPwd);
    }

    @Step("Login with username: {0} and password: {1}")
    public HomePage doLogin(String userName, String pass) {
        System.out.println("App cred's are ==> " + userName + " : " + password);
        elementUtil.waitForElementVisible(emailId, AppConstants.SHORT_TIME_OUT);
        elementUtil.doSendKeys(emailId, userName);
        elementUtil.doSendKeys(password, pass);
        elementUtil.doClick(loginBtn);
        ChainTestListener.log("Login has successfully done for username  " + userName);
        return new HomePage(driver);
    }

    public void getAllFooterLinks() {

    }
}
