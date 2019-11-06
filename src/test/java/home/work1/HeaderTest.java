package home.work1;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HeaderTest {

    private static final String BASE_URL = "http://demo.litecart.net/admin/";
    private WebDriver driver;

    @Before
    public void OpenBrowser() {
        driver = new ChromeDriver();
    }

    @After
    public void CloseBrowser() {
        driver.quit();
    }

    private void login() {
        driver.findElement(By.cssSelector("button[name=login]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".name")));
    }

    private boolean areElementPresents(List<WebElement> elements) {
        return elements.size() > 0;
    }

    private boolean isHeaderPresent() {
        List<WebElement> headers = driver.findElements(By.cssSelector("div.panel-heading"));
        return areElementPresents(headers);
    }

    private List<WebElement> getMenuItems() {
        return driver.findElements(By.cssSelector("li.app"));
    }

    private List<WebElement> getSubMenuItems() {
        return driver.findElements(By.cssSelector("li.doc"));
    }

    @Test
    public void shouldVerifyHeaders() {
        driver.get(BASE_URL);
        login();

        Assert.assertTrue("Header is not present", isHeaderPresent());

        for (int i = 0; i < getMenuItems().size(); i++) {
            getMenuItems().get(i).click();
            Assert.assertTrue("Header is not present", isHeaderPresent());
            if (getSubMenuItems().size() > 0) {
                for (int j = 0; j < getSubMenuItems().size(); j++) {
                    getSubMenuItems().get(j).click();
                    Assert.assertTrue("Header is not present", isHeaderPresent());
                }
            }
        }
    }
}
