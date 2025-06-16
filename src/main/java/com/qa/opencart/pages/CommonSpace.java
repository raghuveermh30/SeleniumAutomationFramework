package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CommonSpace {

    //Here Logo and Footer Links need to be provided

    private WebDriver driver;
    private ElementUtil elementUtil;

    public CommonSpace(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(driver);
    }

    private By logo = By.className("img-responsive");
    private By footerLinks = By.xpath("//footer//a");

    public boolean isLogoDisplayed() {
        return elementUtil.isElementDisplayed(logo);
    }

    public List<String> getFootersList() {
        List<WebElement> footerList = elementUtil.waitForElementsToBePresent(footerLinks, AppConstants.DEFAULT_TIME_OUT);
        System.out.println("Total Number of footer Links : " + footerList.size());
        List<String> footers = new ArrayList<>();

        for (WebElement element : footerList) {
            String text = element.getText();
            footers.add(text);
        }
        return footers;
    }

    public boolean checkFooterLink(String footerLink) {
        return getFootersList().contains(footerLink);
    }

}
