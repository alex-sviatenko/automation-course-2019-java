package home.work3;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

public class CreateNewItemTest {

    private static final String LITECART_ADMIN_URL = "http://demo.litecart.net/admin";
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void OpenBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void CloseBrowser() {
        driver.quit();
    }

    private void openAdminPage() {
        driver.get(LITECART_ADMIN_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[type=submit]")));
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void openCatalogMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#box-apps-menu li[data-code='catalog']")));
        WebElement catalogMenu = driver.findElement(By.cssSelector("#box-apps-menu li[data-code='catalog']"));
        catalogMenu.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(.,'Add New Product')]")));
    }

    private void openAddNewProductPage() {
        WebElement addNewProductButton = driver.findElement(By.xpath("//a[contains(.,'Add New Product')]"));
        addNewProductButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content .panel-heading")));
    }

    private void loginToAdmin(String username, String password) {
        // Login page elements
        WebElement usernameField = driver.findElement(By.cssSelector("[name=username]"));
        WebElement passwordField = driver.findElement(By.cssSelector("[name=password]"));
        WebElement loginButton = driver.findElement(By.cssSelector("[type=submit]"));
        // Credentials are not required for Web version
//        usernameField.sendKeys(username);
//        passwordField.sendKeys(password);
        loginButton.click();
    }

    // General page elements

    private void enableStatus() {
        WebElement statusEnabledButton = driver.findElement(By.cssSelector(".form-group .btn:nth-child(1)"));
        statusEnabledButton.click();
    }

    private void selectCategory(String category) {
        WebElement categoryCheckbox = driver.findElement(By.cssSelector("[data-name='" + category + "']"));
        categoryCheckbox.click();
    }

    private void chooseDefaultCategory(String category) {
        Select oSelect = new Select(driver.findElement(By.cssSelector("[name='default_category_id']")));
        oSelect.selectByVisibleText(category);
    }

    private void setDateValidFrom(String date) {
        WebElement dateValidFromField = driver.findElement(By.cssSelector("[name='date_valid_from']"));
        dateValidFromField.sendKeys(date);
    }

    private void setDateValidTo(String date) {
        WebElement dateValidToField = driver.findElement(By.cssSelector("[name='date_valid_to']"));
        dateValidToField.sendKeys(date);
    }

    private void fillNameField(String name) {
        WebElement nameField = driver.findElement(By.cssSelector("[name='name[en]']"));
        // Use the current time in order to generate unique data
        Long id = System.currentTimeMillis();
        nameField.sendKeys(name + id);
    }

    private void fillCodeField(String code) {
        WebElement codeField = driver.findElement(By.cssSelector("[name='code']"));
        // Use the current time in order to generate unique data
        codeField.sendKeys(code + System.currentTimeMillis());
    }

    private void fillTradeNumbers(String unit, String value) {
        WebElement skuField = driver.findElement(By.cssSelector("[name='" + unit + "']"));
        skuField.sendKeys(value);
    }

    private void selectManufacturer(String option) {
        Select oSelect =new Select(driver.findElement(By.cssSelector("[name='manufacturer_id']")));
        oSelect.selectByVisibleText(option);
    }

    private void fillKeywords(String value) {
        WebElement keywordsField = driver.findElement(By.cssSelector("input[name='keywords']"));
        keywordsField.sendKeys(value);
    }

    private void openTab(String tabName) {
        WebElement tab = driver.findElement(By.linkText(tabName));
        tab.click();
    }

    private void uploadFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("iphone.jpg").getFile());
        WebElement fileField = driver.findElement(By.cssSelector("input[name='new_images[]']"));
        fileField.sendKeys(file.getAbsolutePath());
    }

    // Add New Product page steps

    private void saveNewProduct() {
        WebElement saveButton = driver.findElement(By.cssSelector("button[name='save']"));
        saveButton.click();
        // TODO: good to add explicit wait for the element, when the product item is saved successfully
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert .alert-success")));
    }

    // Information page elements

    private void fillShortDescription(String description) {
        WebElement shortDescriptionField = driver.findElement(By.cssSelector("input[name='short_description[en]']"));
        shortDescriptionField.sendKeys(description);
    }

    private void fillDescription(String description) {
        WebElement descriptionField = driver.findElement(By.cssSelector(".trumbowyg-editor"));
        descriptionField.sendKeys(description);
    }

    private void fillTechnicalData(String techData) {
        WebElement techDataField = driver.findElement(By.cssSelector("textarea[name='technical_data[en]']"));
        techDataField.sendKeys(techData);
    }

    private void fillHeadTitle(String title) {
        WebElement titleField = driver.findElement(By.cssSelector("input[name='head_title[en]']"));
        titleField.sendKeys(title);
    }

    private void fillMetaDescription(String description) {
        WebElement metaDescriptionField = driver.findElement(By.cssSelector("input[name='meta_description[en]']"));
        metaDescriptionField.sendKeys(description);
    }

    // Price page elements

    private void fillPurchasePrice(Number price, String currency) {
        WebElement priceField = driver.findElement(By.cssSelector("input[name='purchase_price']"));
        priceField.sendKeys(price.toString());
        Select oSelect = new Select(driver.findElement(By.cssSelector("[name='purchase_price_currency_code']")));
        oSelect.selectByVisibleText(currency);
    }

    @Test
    public void shouldAddNewItem() {
        openAdminPage();
        loginToAdmin("demo", "demo");
        // TODO: sleep for 3 sec need to be fixed
        sleep();
        openCatalogMenu();
        openAddNewProductPage();

        enableStatus();
        selectCategory("Rubber Ducks");
        chooseDefaultCategory("Rubber Ducks");
        setDateValidFrom("11/07/2019");
        setDateValidTo("11/29/2019");
        fillNameField("Iphone");
        fillCodeField("#12345");
        fillTradeNumbers("sku", "45678");
        fillTradeNumbers("mpn", "99");
        fillTradeNumbers("gtin", "N6");
        fillTradeNumbers("taric", "123456 00 45673");
        selectManufacturer("ACME Corp.");
        fillKeywords("product");
        uploadFile();

        // Fill the fields on Information tab
        openTab("Information");
        fillShortDescription("Iphone short description");
        fillDescription("Iphone description");
        fillTechnicalData("Iphone technical data");
        fillHeadTitle("Iphone");
        fillMetaDescription("Meta info");

        // Fill the fields on Information tab
        openTab("Prices");
        fillPurchasePrice(460, "US Dollars");
        saveNewProduct();

        // Verify the product is added in Catalog
        boolean isProductCreated =
                wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("Iphone"))).isDisplayed();
        Assert.assertTrue("Product is not created in Catalog", isProductCreated);
    }
}
