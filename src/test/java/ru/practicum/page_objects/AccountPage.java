package ru.practicum.page_objects;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Data
public class AccountPage {
    WebDriver driver;

    @FindBy
    WebElement editPasswordButton;
    @FindBy(xpath = "//button[text()='Сохранить']")
    WebElement saveProfileDataButton;
    @FindBy(xpath = "//button[text()='Отмена']")
    WebElement cancelProfileEditData;
    @FindBy(xpath = "//a[text()='Профиль']")
    WebElement profileButton;
    @FindBy(xpath = "//a[text()='История заказов']")
    WebElement goToOrderHistoryButton;
    @FindBy(xpath = "//button[text()='Выход']")
    WebElement logoutButton;
    @FindBy(xpath = "//p[text()='Конструктор']")
    WebElement goToConstructorButton;
    @FindBy(xpath = "//div[@class='AppHeader_header__logo__2D0X2']")
    WebElement goToConstructorLogoButton;

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickGoToConstructorButton() {
        goToConstructorButton.click();
    }

    public void clickLogoutButton() {
        logoutButton.click();
    }
}
