package ru.practicum.utils;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BrowserConfig {

    public WebDriver driver;
    public static final String PROPERTIES = "src/test/resources/application.properties";
    private static String browser;

    @Before
    public void configure() {
        FileInputStream fileInputStream;
        Properties prop = new Properties();
        try {
            fileInputStream = new FileInputStream(PROPERTIES);
            prop.load(fileInputStream);
            browser = prop.getProperty("browser");
        } catch (IOException e) {
            e.printStackTrace();
        }
        selectBrowser();
    }

    public void selectBrowser() {
        if ("chrome".equals(browser))
            setUpChrome();
        else
            setUpYandex();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void setUpChrome() {
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }


    public void setUpYandex() {
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "src/test/resources/yandexdriver.exe");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

}
