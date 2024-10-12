package ru.practicum;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.practicum.pageobjects.MainPage;
import ru.practicum.utils.BrowserConfig;
import ru.practicum.utils.ConfigFileReader;

import java.time.Duration;

public class ConstructorTest extends BrowserConfig {
    private WebDriver driver;
    private MainPage mainPage;
    private ConfigFileReader configFileReader = new ConfigFileReader();
    private BrowserConfig browserConfig;

    @Before
    public void init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();


        browserConfig = new BrowserConfig();
        browserConfig.configure();
        driver.get(configFileReader.getApplicationUrl());
        this.mainPage = new MainPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void shutdown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void clickOnBunsSectionButtonAutoScroll() {
        mainPage.clickOnFillingsSectionButton();
        mainPage.clickOnBunsSectionButton();
        boolean isSelected = mainPage.isSectionButtonSelected(mainPage.getBunsSectionButton());
        Assert.assertTrue("Переход к разделу Булки не выполнен", isSelected);
    }

    @Test
    public void clickOnSousesSectionButtonAutoScroll() {
        mainPage.clickOnSousesSectionButton();
        boolean isSelected = mainPage.isSectionButtonSelected(mainPage.getSousesSectionButton());
        Assert.assertTrue("Переход к разделу Соусы не выполнен", isSelected);
    }

    @Test
    public void clickOnFillingsSectionButtonAutoScroll() {
        mainPage.clickOnFillingsSectionButton();
        boolean isSelected = mainPage.isSectionButtonSelected(mainPage.getFillingsSectionButton());
        Assert.assertTrue("Переход к разделу Начинки не выполнен", isSelected);
    }
}
