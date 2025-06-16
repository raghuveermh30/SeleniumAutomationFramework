package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogoutPage {

    private WebDriver driver;

    //1. By Locators
    private By accountLogout = By.xpath("//h1[text()='Account Logout']");
    private By loginBtn = By.linkText("Login");

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getLogoutPageTitle() {
        String title = driver.getTitle();
        System.out.println("Home Page Title --> " + title);
        return title;
    }

    public String getLogoutPageUrl() {
        String url = driver.getCurrentUrl();
        System.out.println("Home Page Title --> " + url);
        return url;
    }

    public boolean accountLogoutDisplayed() {
        return driver.findElement(accountLogout).isDisplayed();
    }

    public boolean loginBtnDisplayed() {
        return driver.findElement(loginBtn).isDisplayed();
    }

}
