package site.nomoreparties.stellarburgers.pageobject;

import com.codeborne.selenide.SelenideElement;
import site.nomoreparties.stellarburgers.elements.ButtonElement;
import site.nomoreparties.stellarburgers.elements.InputElement;
import site.nomoreparties.stellarburgers.elements.LinkElement;
import site.nomoreparties.stellarburgers.model.UserAuthorization;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage extends BasePage {
    private final String PAGE_TITLE_TEXT = "Регистрация";

    private final InputElement nameInput;
    private final InputElement emailInput;
    private final InputElement passwordInput;
    private final ButtonElement registerButton;
    private final LinkElement loginLink;
    private final SelenideElement passwordDiv;
    private final String passwordErrorMessageLocator = ".//input[@name='Пароль']/parent::div/parent::div[@class='input__container']/p[contains(@class, 'input__error')]";

    public RegisterPage() {
        nameInput = new InputElement(".//label[text()='Имя']/parent::*/input");
        emailInput = new InputElement(".//label[text()='Email']/parent::*/input");
        passwordInput = new InputElement(".//input[@name='Пароль']");
        registerButton = new ButtonElement(".//button[text()='Зарегистрироваться']");
        loginLink = new LinkElement(".//a[@href='/login']");
        passwordDiv = $(byXpath(".//input[@name='Пароль']/parent::div"));
    }

    @Override
    public void waitForLoaded() {
        String locator = String.format(".//h2[text()='%s']", PAGE_TITLE_TEXT);
        $(byXpath(locator)).shouldBe(visible);
    }

    public LoginPage register(UserAuthorization userAuthorization) {
        nameInput.clearAndSetValue(userAuthorization.getName());
        emailInput.clearAndSetValue(userAuthorization.getEmail());
        passwordInput.clearAndSetValue(userAuthorization.getPassword());
        return (LoginPage) PageNavigator.navigate(registerButton, LoginPage.class);
    }

    public void invalidRegister(UserAuthorization userAuthorization) {
        nameInput.clearAndSetValue(userAuthorization.getName());
        emailInput.clearAndSetValue(userAuthorization.getEmail());
        passwordInput.clearAndSetValue(userAuthorization.getPassword());
        registerButton.clickElement();
    }

    public LoginPage goToLoginPage() {
        return (LoginPage) PageNavigator.navigate(loginLink, LoginPage.class);
    }

    public boolean hasPasswordInvalidCSS() {
        String className = passwordDiv.getAttribute("class");
        return className != null && className.contains("input_status_error");
    }

    public String getPasswordInvalidMessage() {
        return $(byXpath(passwordErrorMessageLocator)).getText();
    }
}
