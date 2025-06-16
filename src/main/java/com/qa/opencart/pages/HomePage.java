package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.constants.AppError;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    private ElementUtil elementUtil;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(driver);
    }

    //1. By Locators
    private By logoutLink = By.linkText("Logout");
    private By headers = By.cssSelector("div#content > h2");
    private By search = By.name("search");
    private By searchBtn = By.cssSelector("div#search button");

    //2. Public page actions
    public String getHomePageTitle() {
        String title = driver.getTitle();
        // String title = elementUtil.waitForTitleIs(AppConstants.HOME_PAGE_TITLE, AppConstants.DEFAULT_TIME_OUT);
        System.out.println("Home Page Title --> " + title);
        return title;
    }

    public String getHomePageUrl() {
        String url = driver.getCurrentUrl();
        // String url = elementUtil.waitForUrlContains(AppConstants.HOME_FRACTION_VALUE, AppConstants.DEFAULT_TIME_OUT);
        System.out.println("Home Page Title --> " + url);
        return url;
    }

    public boolean isLogoutLinkExist() {
        return elementUtil.doElementIsDisplayed(logoutLink);
    }

    public List<String> getHeadersList() {
        elementUtil.waitForElementVisible(headers, AppConstants.SHORT_TIME_OUT);
        List<WebElement> headersList = elementUtil.getElements(headers);
        List<String> headersValueList = new ArrayList<>();
        for (WebElement element : headersList) {
            String text = element.getText();
            headersValueList.add(text);
        }
        return headersValueList;
    }

    public SearchResultPage doSearch(String searchKey) {
        System.out.println("Search key : " + searchKey);
        WebElement searchElement = elementUtil.waitForElementVisible(search, AppConstants.DEFAULT_TIME_OUT);
        elementUtil.doSendKeys(searchElement, searchKey);
        elementUtil.doClick(searchBtn);
        return new SearchResultPage(driver);
    }

    public void logout() {
        if (isLogoutLinkExist()) {
            driver.findElement(logoutLink).click();
        } //pending - WIP
    }
}
