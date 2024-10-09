package ru.practicum;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.practicum.constants.Browser;
import ru.practicum.pageObjects.MainPage;
import ru.practicum.utils.ConfigFileReader;
import ru.practicum.utils.DriverInitializer;

import java.time.Duration;

public class ConstructorTest {
    WebDriver driver;
    MainPage mainPage;
    ConfigFileReader configFileReader = new ConfigFileReader();
    Browser browserEnum;

    @Before
    public void init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        browserEnum = getBrowserFromConfig();
        this.driver = DriverInitializer.getDriver(browserEnum);
        driver.get(configFileReader.getApplicationUrl());
        this.mainPage = new MainPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void shutdown() {
        driver.quit();
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

    private Browser getBrowserFromConfig() {
        String browserName = configFileReader.getProperty("test.browser");
        return Browser.valueOf(browserName.toUpperCase());
    }
}
