package ru.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.*;
import ru.practicum.api.UsersSteps;
import ru.practicum.utils.BrowserConfig;
import ru.practicum.pageobjects.*;
import ru.practicum.pojos.SignInRequest;
import ru.practicum.pojos.SuccessSignInSignUpResponse;
import ru.practicum.pojos.UserRequest;
import ru.practicum.utils.ConfigFileReader;
import ru.practicum.utils.UsersUtils;
import ru.practicum.api.UsersSteps;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;




public class LoginTest extends BrowserConfig {
    private MainPage mainPage;
    private LoginPage loginPage;
    private ConfigFileReader configFileReader = new ConfigFileReader();

    private UserRequest testUser;
    private String accessToken;
    private SignInRequest signInRequest;

    @Before
    public void init() {
        super.configure();
        testUser = UsersUtils.getUniqueUser();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        SuccessSignInSignUpResponse signUpResponse = UsersSteps.createUniqueUser(testUser)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessSignInSignUpResponse.class);
        accessToken = signUpResponse.getAccessToken();

        signInRequest = new SignInRequest(testUser.getEmail(), testUser.getPassword());

        driver.get(configFileReader.getApplicationUrl());
        this.mainPage = new MainPage(driver);
        this.loginPage = new LoginPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void closeDriver() {
        driver.quit();
        UsersSteps.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Вход по кнопке Войти в аккаунт на главной")
    public void signInWithValidDataWithSignInButtonSuccess() {
        mainPage.clickSignInButton();
        loginPage.loginWithCredentials(signInRequest);
        mainPage.clickAccountButton();
        AccountPage accountPage = new AccountPage(driver);

        boolean displayed = accountPage.getProfileButton().isDisplayed();
        assertTrue("Вход в личный кабинет не выполнен", displayed);
    }

    @Test
    @DisplayName("Вход через кнопку Личный кабинет")
    public void signInWithValidDataWithAccountButtonSuccess() {
        mainPage.clickAccountButton();
        loginPage.loginWithCredentials(signInRequest);
        mainPage.clickAccountButton();
        AccountPage accountPage = new AccountPage(driver);

        boolean displayed = accountPage.getProfileButton().isDisplayed();
        assertTrue("Вход в личный кабинет не выполнен", displayed);
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void signInWithValidDataFromSignUpFormSuccess() {
        mainPage.clickSignInButton();
        loginPage.clickSignUpButton();
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.clickSignInButton();
        loginPage.loginWithCredentials(signInRequest);
        mainPage.clickAccountButton();
        AccountPage accountPage = new AccountPage(driver);

        boolean displayed = accountPage.getProfileButton().isDisplayed();
        assertTrue("Вход в личный кабинет не выполнен", displayed);
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void signInWithValidDataFromPasswordRecoverFormSuccess() {
        mainPage.clickSignInButton();
        loginPage.clickRecoverPasswordButton();
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.clickSignInButton();
        loginPage.loginWithCredentials(signInRequest);
        mainPage.clickAccountButton();
        AccountPage accountPage = new AccountPage(driver);

        boolean displayed = accountPage.getProfileButton().isDisplayed();
        assertTrue("Вход в личный кабинет не выполнен", displayed);
    }
}
