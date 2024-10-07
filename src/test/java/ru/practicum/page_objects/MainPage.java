package ru.practicum.page_objects;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Data
public class MainPage {
    WebDriver driver;
    @FindBy(xpath = "//h1[text()='Соберите бургер']")
    WebElement burgerConstructorHeader;
    @FindBy(xpath = "//button[text()='Войти в аккаунт']")
    WebElement homeSignInButton;
    @FindBy(xpath = "//p[text()='Личный Кабинет']")
    WebElement homeAccountButton;
    @FindBy(xpath = "//span[text()='Булки']/parent::div")
    WebElement bunsSectionButton;
    @FindBy(xpath = "//span[text()='Соусы']/parent::div")
    WebElement sousesSectionButton;
    @FindBy(xpath = "//span[text()='Начинки']/parent::div")
    WebElement fillingsSectionButton;
    @FindBy(xpath = "//h2[text()='Соусы']")
    WebElement sousesSectionHeader;
    @FindBy(xpath = "//h2[text()='Булки']")
    WebElement bunsSectionHeader;
    @FindBy(xpath = "//h2[text()='Начинки']")
    WebElement fillingsSectionHeader;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickAccountButton() {
        homeAccountButton.click();
    }

    public void clickSignInButton() {
        homeSignInButton.click();
    }

    public void clickOnBunsSectionButton() {
        bunsSectionButton.click();
    }

    public void clickOnSousesSectionButton() {
        sousesSectionButton.click();
    }

    public void clickOnFillingsSectionButton() {
        fillingsSectionButton.click();
    }
    public boolean isSectionButtonSelected(WebElement sectionButton) {
        return sectionButton.getAttribute("class").contains("tab_tab_type_current__2BEPc");
    }
}
