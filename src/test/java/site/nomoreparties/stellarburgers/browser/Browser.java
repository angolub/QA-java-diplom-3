package site.nomoreparties.stellarburgers.browser;

import com.codeborne.selenide.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Browser {
    public static void initBrowser() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/test/resources/browser.properties"));
        String testBrowser = properties.getProperty("testBrowser");
        BrowserType browserType = BrowserType.valueOf(testBrowser);
        switch(browserType) {
            case CHROME:
                Configuration.browser = "CHROME";
                break;
            case YANDEX:
                String yandexDriverPath = properties.getProperty("yandexDriver.path");
                System.setProperty("webdriver.chrome.driver", yandexDriverPath);
                Configuration.browser = "CHROME";
                break;
            default:
                throw new RuntimeException("Browser undefined");
        }
    }
}
