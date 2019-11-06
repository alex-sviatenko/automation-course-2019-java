package home.work2;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPropertyTest {

    private static final String BASE_URL = "http://demo.litecart.net/";
    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void OpenBrowser() {
        driver = new ChromeDriver();
        // Run in FF browser
//        driver = new FirefoxDriver();
        // Run in IE browser
//        driver = new InternetExplorerDriver();
        wait = new WebDriverWait(driver, 5);
    }

    @After
    public void CloseBrowser() {
        driver.quit();
    }

    private void openLiteCartStorePage() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".account.dropdown")));
    }

    private boolean isTextGrayAndStrike(WebElement elem) {
        return elem.getCssValue("color").contains("51, 51, 51")
                && elem.getTagName().equals("del");
    }

    private boolean isTextRedAndBold(WebElement elem) {
        return elem.getCssValue("color").contains("204, 0, 0")
                && elem.getCssValue("font-weight").equals("700");
    }

    @Test
    public void shouldVerifyProductProperties() {
        openLiteCartStorePage();

        // Main page elements
        WebElement compaignProductSection = driver.findElement(By.id("box-campaign-products"));
        WebElement firstCompaignProduct = compaignProductSection.findElement(By.cssSelector(".product-column:nth-of-type(1)"));
        WebElement firstCompaignProductName = firstCompaignProduct.findElement(By.cssSelector(".name"));
        WebElement firstProductPrice = firstCompaignProduct.findElement(By.cssSelector(".regular-price"));
        WebElement firstProductDiscount = firstCompaignProduct.findElement(By.cssSelector(".campaign-price"));

        String firstProductNameOnMainPage = firstCompaignProductName.getText();
        String firstProductRegularPriceOnMainPage = firstProductPrice.getText();
        String firstProductDiscountOnMainPage = firstProductDiscount.getText();

        // Verify the text for Prices on Main page
        Assert.assertTrue("First product Regular price is NOT gray and strike on Main page", isTextGrayAndStrike(firstProductPrice));
        Assert.assertTrue("First product Campaigns price is NOT red and bold on Main page", isTextRedAndBold(firstProductDiscount));


        firstCompaignProduct.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[name=add_cart_product]")));

        // Item page elements
        WebElement productName = driver.findElement(By.cssSelector("[id=box-product] .title"));
        WebElement productRegularPrice = driver.findElement(By.cssSelector("[id=box-product] .regular-price"));
        WebElement productDiscount = driver.findElement(By.cssSelector("[id=box-product] .campaign-price"));

        String productNameOnItemPage = productName.getText();
        String productPriceOnItemPage = productRegularPrice.getText();
        String productDiscountOnItemPage = productDiscount.getText();

        // Verify the Product name is the same as on the Main page
        Assert.assertEquals("Product Names are NOT equal on Main and Item pages", firstProductNameOnMainPage, productNameOnItemPage);
        Assert.assertEquals("Product Prices are NOT equal on Main and Item pages", firstProductRegularPriceOnMainPage, productPriceOnItemPage);
        Assert.assertEquals("Product Discounts are NOT equal on Main and Item pages", firstProductDiscountOnMainPage, productDiscountOnItemPage);

        // Verify the text for Prices on Item page
        Assert.assertTrue("Product Regular price is NOT gray and strike on Item page", isTextGrayAndStrike(productRegularPrice));
        Assert.assertTrue("Product Campaigns price is NOT red and bold on Item page", isTextRedAndBold(productDiscount));
    }
}
