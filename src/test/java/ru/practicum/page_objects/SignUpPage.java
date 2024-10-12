package ru.practicum.page_objects;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Data
public class SignUpPage {
    WebDriver driver;
    @FindBy(xpath = "//label[text()='Имя']//following-sibling::input")
    WebElement nameField;
    @FindBy(xpath = "//label[text()='Email']//following-sibling::input")
    WebElement emailField;
    @FindBy(xpath = "//label[text()='Пароль']//following-sibling::input")
    WebElement passwordField;
    @FindBy(xpath = "//button[text()='Зарегистрироваться']")
    WebElement signUpButton;
    @FindBy(xpath = "//a[@href='/login']")
    WebElement signInButton;
    @FindBy(xpath = "//p[text()='Некорректный пароль']")
    WebElement passwordErrorMessage;
    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterName(String name) {
        nameField.sendKeys(name);
    }

    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickSignUpButton() {
        signUpButton.click();
    }

    public void clickSignInButton() {
        signInButton.click();
    }
}
