package com.qa.opencart.test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class ProductInfoPageTest extends BaseTest {


    @BeforeClass
    public void productInfoSetup() {
        homePage = loginPage.doLogin(properties.getProperty("username"), " R@ghumh3017");
    }

    @Test(dataProvider = "getProductSearchData")
    public void productSearchHeaderTest(String searchProdutKey, String ProdcutName) {
        searchResultPage = homePage.doSearch(searchProdutKey);
        productInfoPage = searchResultPage.selectProduct(ProdcutName);
        String actualProductHeader = productInfoPage.getProductHeader();
        Assert.assertEquals(actualProductHeader, ProdcutName);
    }

    @DataProvider
    public Object[][] getProductSearchData() {
        return new Object[][]{
                {"macbook", "MacBook Pro"},
                {"macbook", "MacBook Air"},
                {"imac", "iMac"},
                {"samsung", "Samsung SyncMaster 941BW"},
                {"samsung", "Samsung Galaxy Tab 10.1"}
        };
    }

    @DataProvider
    public Object[][] getProductImageSheetData() {
        return ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
    }

    @Test(dataProvider = "getProductImageData")
    public void productImagesCountTest(String searchProductKey, String productName, String expectedImageCount) {
        searchResultPage = homePage.doSearch(searchProductKey);
        productInfoPage = searchResultPage.selectProduct(productName);
        int actualProductImagesCount = productInfoPage.getProductImagesCount();
        Assert.assertEquals(actualProductImagesCount, Integer.parseInt(expectedImageCount));
    }

    @DataProvider
    public Object[][] getProductImageData() {
        return new Object[][]{
                {"macbook", "MacBook Pro", "4"},
                {"macbook", "MacBook Air", "4"},
                {"imac", "iMac", "3"},
                {"samsung", "Samsung SyncMaster 941BW", "1"},
                {"samsung", "Samsung Galaxy Tab 10.1", "7"}
        };
    }

    @Test
    public void productInformationTest() {
        searchResultPage = homePage.doSearch("macbook");
        productInfoPage = searchResultPage.selectProduct("MacBook Pro");
        Map<String, String> produtcInfoMap = productInfoPage.getCompleteProductInfo();
        produtcInfoMap.forEach((K, V) -> System.out.println(K + " : " + V));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(produtcInfoMap.get("Brand"), "Apple");
        softAssert.assertEquals(produtcInfoMap.get("Availability"), "Out Of Stock");
        softAssert.assertEquals(produtcInfoMap.get("Product Code"), "Product 18");
        softAssert.assertEquals(produtcInfoMap.get("HeaderKey"), "MacBook Pro");
        softAssert.assertEquals(produtcInfoMap.get("Reward Points"), "800");
        softAssert.assertEquals(produtcInfoMap.get("price"), "$2,000.00");
        softAssert.assertEquals(produtcInfoMap.get("extTax"), "$2,000.00");
        softAssert.assertEquals(produtcInfoMap.get("ImagesCount"), "4");
        softAssert.assertAll();
    }

    @Test
    public void addTocartTest() {
        searchResultPage = homePage.doSearch("macbook");
        productInfoPage = searchResultPage.selectProduct("MacBook Pro");
        productInfoPage.enterQuantity();
        String actualCartMessage = productInfoPage.addToCartAndVerifyCartMessage().trim();
        System.out.println(actualCartMessage);
        Assert.assertEquals(actualCartMessage, "Success: You have added MacBook Pro to your shopping cart!\n" +
                "×", "Add to Cart Message is not displayed");
    }

    @Test(priority = Integer.MAX_VALUE)
    public void logoutFromApplication() {
        searchResultPage = homePage.doSearch("macbook");
        productInfoPage = searchResultPage.selectProduct("MacBook Pro");
        String actualProductHeader = productInfoPage.getProductHeader();
        Assert.assertEquals(actualProductHeader, "MacBook Pro");
        logoutPage = productInfoPage.getMyAccountListAndLogout();
       // Assert.assertTrue(logoutPage.accountLogoutDisplayed());
    }

}
