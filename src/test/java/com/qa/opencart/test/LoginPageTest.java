package com.qa.opencart.test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.constants.AppError;
import com.qa.opencart.pages.HomePage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("EPIC 100: Design Login Page for OpenCart")
@Story("Design the various features of open cart login page")
@Feature("Feature 50: Login Page Feature")
@Owner("Naveen Automation Labs")
public class LoginPageTest extends BaseTest {

    /* Test class shouldn;t have any selenium codes
     * Page class shouldn't have any assertions, this is anti-pattern
     * Page class shouldn't have driver init methods
     * Ideally, Unit Testing framework, In the Page class shouldn;t have Selenium, Playwright, etc codes
     * https://martinfowler.com/bliki/PageObject.html
     * Here, We can acheive zig-zag pattern -> Login Page, Accounts Page, Search Page etc --> All are linked
     * This is also called Page-Chain approach
     */

    @Step("Checking Login Page Title")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void loginPageTitleTest() {
        String actualTitle = loginPage.getLoginPageTitle();
        Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
    }

    @Step("Checking Login Page URL")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void loginPageUrl() {
        ChainTestListener.log("Verify Login Page URL Test");
        String actualUrl = loginPage.getLoginPageUrl();
        Assert.assertTrue(actualUrl.contains(AppConstants.LOGIN_FRACTION_VALUE), AppError.URL_NOT_FOUND);
    }

    @Step("Checking Forgot Password Link")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void forgotPassLinkExist() {
        boolean flag = loginPage.isForgotPassLinkExist();
        Assert.assertTrue(flag, AppError.FORGOT_PASS_NOT_FOUND);
    }

    @Step("Checking the Logo on the Home Page")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Checking Logo on Home Page")
    public void logoDisplayedTest() {
        Assert.assertTrue(commonSpace.isLogoDisplayed(), AppError.LOGO_NOT_FOUND);
    }

    @DataProvider
    public Object[][] getFooterData() {
        return new Object[][]{
                {"Returns"},
                {"Contact Us"},
                {"Site Map"},
                {"Gift Certificates"}
        };
    }

    @Step("Checking the Page Footers")
    @Severity(SeverityLevel.NORMAL)
    @Test(dataProvider = "getFooterData", description = "Checking Important Footer Links on HomePage")
    public void getFooterLinkTest(String footerValue) {
        commonSpace.checkFooterLink(footerValue);
    }

    @Step("Checking user is able to login to the application")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = Integer.MAX_VALUE)
    public void doLoginTest() {
        HomePage homePage = loginPage.doLogin(properties.getProperty("username"), properties.getProperty("password"));
        Assert.assertEquals(homePage.getHomePageTitle(), "My Account", "==Login is not successful==");
    }
}
