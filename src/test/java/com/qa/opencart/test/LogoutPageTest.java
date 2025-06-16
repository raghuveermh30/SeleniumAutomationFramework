package com.qa.opencart.test;

import com.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LogoutPageTest extends BaseTest {

    @BeforeClass
    public void productInfoSetup() {
        homePage =  loginPage.doLogin(properties.getProperty("username"), " R@ghumh3017");
        searchResultPage = homePage.doSearch("macbook");
        productInfoPage = searchResultPage.selectProduct("MacBook Pro");
        String actualProductHeader = productInfoPage.getProductHeader();
        Assert.assertEquals(actualProductHeader, "MacBook Pro");
        logoutPage = productInfoPage.getMyAccountListAndLogout();
        Assert.assertTrue(logoutPage.accountLogoutDisplayed());
    }

    @Test
    public void logoutPageTitleTest() {
        String logoutPageTitle = logoutPage.getLogoutPageTitle();
        Assert.assertEquals(logoutPageTitle, "Account Logout");
    }

    @Test
    public void logoutPageUrlTest() {
        String logoutPageUrl = logoutPage.getLogoutPageUrl();
        Assert.assertTrue(logoutPageUrl.contains("route=account/logout"));
    }

    @Test
    public void logoutPageTest() {
        boolean flag = logoutPage.accountLogoutDisplayed();
        Assert.assertTrue(flag);
    }

    @Test
    public void loginBtnDisplayed() {
        boolean flag = logoutPage.loginBtnDisplayed();
        Assert.assertTrue(flag);
    }

}
