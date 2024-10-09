package ru.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.practicum.apiSteps.UsersSteps;
import ru.practicum.constants.Browser;
import ru.practicum.pageObjects.AccountPage;
import ru.practicum.pageObjects.LoginPage;
import ru.practicum.pageObjects.MainPage;
import ru.practicum.pojos.SignInRequest;
import ru.practicum.pojos.SuccessSignInSignUpResponse;
import ru.practicum.pojos.UserRequest;
import ru.practicum.utils.ConfigFileReader;
import ru.practicum.utils.DriverInitializer;
import ru.practicum.utils.UsersUtils;

import java.time.Duration;

@RunWith(Parameterized.class)
public class GoToConstructorTest {
    WebDriver driver;
    MainPage mainPage;
    LoginPage loginPage;
    AccountPage accountPage;
    Browser browserEnum;

    public GoToConstructorTest(Browser browserEnum) {
        this.browserEnum = browserEnum;
    }

    @Before
    public void init() {
        driver = DriverInitializer.getDriver(browserEnum);

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
        driver.get(new ConfigFileReader().getApplicationUrl());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void shutdown() {
        driver.quit();
    }

    @Test
    @DisplayName("Успешный переход в конструктор из аккаунта")
    public void goToConstructorFromAccountSuccess() {
        UserRequest user = UsersUtils.getUniqueUser();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        SuccessSignInSignUpResponse signUpResponse = UsersSteps.createUniqueUser(user)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessSignInSignUpResponse.class);

        mainPage.clickAccountButton();
        loginPage.loginWithCredentials(new SignInRequest(user.getEmail(), user.getPassword()));
        mainPage.clickAccountButton();
        accountPage.clickGoToConstructorButton();

        boolean displayed = mainPage.getBurgerConstructorHeader().isDisplayed();
        Assert.assertTrue("Конструктор не открыт",displayed);

        UsersSteps.deleteUser(signUpResponse.getAccessToken());
    }
}
