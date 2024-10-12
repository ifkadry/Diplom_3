package ru.practicum.page_objects;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.practicum.pojos.SignInRequest;

@Data
public class LoginPage {
    WebDriver driver;
    @FindBy(xpath = "//input[@name='name']")
    WebElement emailField;
    @FindBy(xpath = "//input[@type='password']")
    WebElement passwordField;
    @FindBy(xpath = "//button[text()='Войти']")
    WebElement signInButton;
    @FindBy(xpath = "//a[@href='/register']")
    WebElement signUpButton;
    @FindBy(xpath = "//a[@href='/forgot-password']")
    WebElement recoverPasswordButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickSignUpButton() {
        signUpButton.click();
    }

    public void clickSignInButton() {
        signInButton.click();
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }

    public void clickRecoverPasswordButton() {
        recoverPasswordButton.click();
    }

    public void loginWithCredentials(SignInRequest signInRequest) {
        enterEmail(signInRequest.getEmail());
        enterPassword(signInRequest.getPassword());
        clickSignInButton();
    }
}
