import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FirstTest {
    WebDriver driver;

    @Before
    public void openBrowser() {
        ChromeOptions ch = new ChromeOptions();
        ch.setCapability("unexpectedAlertBehaviour", "ignore");
        driver = new ChromeDriver(ch);
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }

    @Test
    public void firstGoogleTest() {
        driver.get("http://google.com");
        System.out.println(((HasCapabilities)driver).getCapabilities());
    }
}
