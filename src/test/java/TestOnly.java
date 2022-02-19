
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.elements.interfaces.IElementFactory;
import aquality.selenium.elements.interfaces.ITextBox;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestOnly {
    public static void main(String[] args) {
        Browser browser = AqualityServices.getBrowser();

        browser.maximize();
        browser.goTo("https://wikipedia.org");
        browser.waitForPageToLoad();

        IElementFactory elementFactory = AqualityServices.getElementFactory();
        ITextBox txbSearch = elementFactory.getTextBox(By.id("searchInput"), "Search");
        txbSearch.type("quality assurance");
        txbSearch.submit();
        browser.waitForPageToLoad();

        browser.quit();


    }
}
