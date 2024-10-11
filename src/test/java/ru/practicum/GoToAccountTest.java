package ru.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.practicum.utils.BrowserConfig;
import ru.practicum.utils.ConfigFileReader;
import ru.practicum.utils.UsersUtils;
import ru.practicum.pojos.SignInRequest;
import ru.practicum.pojos.SuccessSignInSignUpResponse;
import ru.practicum.pojos.UserRequest;
import ru.practicum.pageobjects.AccountPage;
import ru.practicum.pageobjects.LoginPage;
import ru.practicum.pageobjects.MainPage;
import ru.practicum.api.UsersSteps;

import java.time.Duration;

public class GoToAccountTest extends BrowserConfig {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private AccountPage accountPage;
    private ConfigFileReader configFileReader = new ConfigFileReader();
    private BrowserConfig browserConfig = new BrowserConfig();

    @Before
    public void init() {
        browserConfig.configure();
        driver = browserConfig.driver;
        driver.get(configFileReader.getApplicationUrl());
        mainPage = new MainPage(driver);
        accountPage = new AccountPage(driver);
        loginPage = new LoginPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void shutdown() {
        driver.quit();
    }

    @Test
    @DisplayName("Успешный переход по клику на Личный кабинет")
    public void goToAccountWithAccountButtonSuccess() {
        UserRequest user = UsersUtils.getUniqueUser();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        String accessToken = UsersSteps.createUniqueUser(user)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessSignInSignUpResponse.class)
                .getAccessToken();

        mainPage.clickAccountButton();
        loginPage.loginWithCredentials(new SignInRequest(user.getEmail(), user.getPassword()));
        mainPage.clickAccountButton();

        boolean displayed = accountPage.getProfileButton().isDisplayed();
        Assert.assertTrue("Личный кабинет не открыт", displayed);

        UsersSteps.deleteUser(accessToken);
    }
}
