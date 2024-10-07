package ru.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.practicum.api_steps.UsersSteps;
import ru.practicum.constants.Browser;
import ru.practicum.page_objects.*;
import ru.practicum.pojos.SignInRequest;
import ru.practicum.pojos.SuccessSignInSignUpResponse;
import ru.practicum.pojos.UserRequest;
import ru.practicum.utils.ConfigFileReader;
import ru.practicum.utils.DriverInitializer;
import ru.practicum.utils.UsersUtils;

import java.time.Duration;

@RunWith(Parameterized.class)
public class LoginTest {
    WebDriver driver;
    MainPage mainPage;
    LoginPage loginPage;
    Browser browserEnum;
    ConfigFileReader configFileReader = new ConfigFileReader();

    UserRequest testUser;
    String accessToken;
    SignInRequest signInRequest;

    public LoginTest(Browser browserEnum) {
        this.browserEnum = browserEnum;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {Browser.CHROME},
                {Browser.YANDEX}
        };
    }

    @Before
    public void init() {
        testUser = UsersUtils.getUniqueUser();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        SuccessSignInSignUpResponse signUpResponse = UsersSteps.createUniqueUser(testUser)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessSignInSignUpResponse.class);
        accessToken = signUpResponse.getAccessToken();

        signInRequest = new SignInRequest(testUser.getEmail(), testUser.getPassword());

        this.driver = DriverInitializer.getDriver(browserEnum);

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
        Assert.assertTrue("Вход в личный кабинет не выполнен", displayed);
    }

    @Test
    @DisplayName("Вход через кнопку Личный кабинет")
    public void signInWithValidDataWithAccountButtonSuccess() {
        mainPage.clickAccountButton();
        loginPage.loginWithCredentials(signInRequest);
        mainPage.clickAccountButton();
        AccountPage accountPage = new AccountPage(driver);

        boolean displayed = accountPage.getProfileButton().isDisplayed();
        Assert.assertTrue("Вход в личный кабинет не выполнен", displayed);
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
        Assert.assertTrue("Вход в личный кабинет не выполнен", displayed);
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
        Assert.assertTrue("Вход в личный кабинет не выполнен", displayed);
    }
}
