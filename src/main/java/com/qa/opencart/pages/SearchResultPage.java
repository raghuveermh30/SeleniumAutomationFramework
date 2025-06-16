package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultPage {

    private WebDriver driver;
    private ElementUtil elementUtil;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(driver);
    }

    //1. By Locators
    private By productResults = By.cssSelector("div.product-thumb");
  //  private By macBook_pro = By.linkText("MacBook Pro");


    //2. Search page actions
    public String getSearchPageTitle() {
        String title = driver.getTitle();
        System.out.println("Home Page Title --> " + title);
        return title;
    }

    public String getSearchPageUrl() {
        String url = driver.getCurrentUrl();
        System.out.println("Search Page Title --> " + url);
        return url;
    }

    public int getProductResultsCount() {
        int count = elementUtil.waitForElementsToVisible(productResults, AppConstants.DEFAULT_TIME_OUT).size();
        // int count =  driver.findElements(productResults).size();
        System.out.println("Product Results count is : " + count);
        return count;
    }

    public ProductInfoPage selectProduct(String productName) {
        System.out.println("Product Name is : " + productName);
        elementUtil.waitForElementVisible(By.linkText(productName), AppConstants.DEFAULT_TIME_OUT);
        elementUtil.doClick(By.linkText(productName));
        return new ProductInfoPage(driver);
    }
}
