package com.qa.opencart.test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.constants.AppError;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class HomePageTest extends BaseTest {

    @BeforeClass
    public void homePageSetup() {
        homePage = loginPage.doLogin("raghuveermh30@gmail.com", " R@ghumh3017");
    }

    @Test
    public void homePageTitleTest() {
        String actualTitle = homePage.getHomePageTitle();
        Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
    }

    @Test
    public void homePageUrl() {
        String actualUrl = homePage.getHomePageUrl();
        Assert.assertTrue(actualUrl.contains(AppConstants.HOME_FRACTION_VALUE), AppError.URL_NOT_FOUND);
    }

    @Test
    public void logoutLinkExistTest() {
        Assert.assertTrue(homePage.isLogoutLinkExist(), AppError.ELEMENT_NOT_FOUND);
    }

    @Test
    public void homePageHeadersTest() {
        List<String> actualHeaders = homePage.getHeadersList();
        System.out.println("Home Page headers are : " + actualHeaders);
    }

    @DataProvider
    public Object[][] getSearchData() {
        return new Object[][]{
                {"macbook", 3},
                {"imac", 1},
                {"samsung", 2},
                {"canon", 1},
                {"airtel", 0}
        };
    }

    @Test(dataProvider = "getSearchData")
    public void searchTest(String searchKey, int resultCount) {
        searchResultPage = homePage.doSearch(searchKey);
        Assert.assertEquals(searchResultPage.getProductResultsCount(), resultCount);
    }

    @Test(description = "Checking Logo on Home Page", enabled = true)
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

    @Test(dataProvider = "getFooterData", description = "Checking Important Footer Links on HomePage", enabled = true)
    public void getFooterLinkTest(String footerValue) {
        commonSpace.checkFooterLink(footerValue);
    }

}
