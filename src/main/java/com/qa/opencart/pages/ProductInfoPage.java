package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class ProductInfoPage {

    private WebDriver driver;
    private ElementUtil elementUtil;
    private Map<String, String> productMap;


    public ProductInfoPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(driver);
    }

    //1. By Locators
    private By productHeader = By.tagName("h1");
    private By myAccount = By.xpath("//span[text() = 'My Account']");
    private By myAccountDropDown = By.xpath("//ul[@class = 'dropdown-menu dropdown-menu-right']/li");
    private By productImages = By.cssSelector("ul.thumbnails li");
    private By productMetaData = By.xpath("(//div[@class='col-sm-4']//ul[@class= 'list-unstyled'])[1]/li");
    private By productPriceData = By.xpath("(//div[@class='col-sm-4']//ul[@class= 'list-unstyled'])[2]/li");
    private By productQuantity = By.id("input-quantity");
    private By addToCart = By.xpath("//button[@id = 'button-cart']");
    private By cartMessage = By.xpath("//*[contains(text(), 'Success: You have added')]");
    private By shoppingCartBtn = By.linkText("shopping cart");

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

    public String getProductHeader() {
        // String productHeaderText = driver.findElement(productHeader).getText();
        String productHeaderText = elementUtil.getElementText(productHeader);
        System.out.println("Product Header Page is : " + productHeaderText);
        return productHeaderText;
    }

    public LogoutPage getMyAccountListAndLogout() {
        driver.findElement(myAccount).click();
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<WebElement> myAccountList = driver.findElements(myAccountDropDown);
        for (WebElement element : myAccountList) {
            String text = element.getText();
            if (text.contains("Logout")) {
                element.click();
                break;
            }
        }
        return new LogoutPage(driver);
    }

    public int getProductImagesCount() {
        int imagesCount = elementUtil.waitForElementsToBePresent(productImages, AppConstants.SHORT_TIME_OUT).size();
        System.out.println(getProductHeader() + " Images Count " + imagesCount);
        return imagesCount;
    }

    /* Get full Product Information : Header, Images Count, Metadata and Price Data
     *
     * @return
     */
    public Map<String, String> getCompleteProductInfo() {
        productMap = new HashMap<String, String>();
        //productMap = new LinkedHashMap<>(); //It will maintain the Indexing order
        // productMap = new TreeMap<>(); //All the keys should be in the sorted/alphabetical order
        System.out.println("*****************");
        productMap.put("HeaderKey", getProductHeader());
        productMap.put("ImagesCount", getProductImagesCount() + "");
        getProductMetadata();
        getProductPriceData();
        System.out.println("*****************");
        return productMap;
    }

//    Brand: Apple
//    Product Code: Product 18
//    Reward Points: 800
//    Availability: Out Of Stock

    private void getProductMetadata() {
        List<WebElement> productMetadataList = elementUtil.waitForElementsToBePresent(productMetaData, AppConstants.DEFAULT_TIME_OUT);
        for (WebElement element : productMetadataList) {
            String productText = element.getText();
            String[] metaArray = productText.split(":");
            String metaKey = metaArray[0].trim();
            String metaValue = metaArray[1].trim();
            productMap.put(metaKey, metaValue);
        }
    }

    //    $2,000.00
    //  Ex Tax: $2,000.00
    private void getProductPriceData() {
        List<WebElement> productPriceList = elementUtil.waitForElementsToBePresent(productPriceData, AppConstants.DEFAULT_TIME_OUT);
        String productPrice = productPriceList.get(0).getText().trim();
        String productExtTaxPrice = productPriceList.get(1).getText().split(":")[1].trim();
        productMap.put("price", productPrice);
        productMap.put("extTax", productExtTaxPrice);
    }

    public void enterQuantity() {
        elementUtil.waitForElementVisible(productQuantity, AppConstants.DEFAULT_TIME_OUT);
        elementUtil.doSendKeys(productQuantity, "1");
    }

    public String addToCartAndVerifyCartMessage() {
        elementUtil.waitForElementVisible(addToCart, AppConstants.DEFAULT_TIME_OUT);
        elementUtil.doClick(addToCart);
        return elementUtil.getElementText(cartMessage);
    }

    public ShoppingCartPage clickShoppingCart() {
        elementUtil.doClick(shoppingCartBtn);
        return new ShoppingCartPage(driver);
    }
}
