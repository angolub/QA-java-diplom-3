package site.nomoreparties.stellarburgers.pageobject;

import site.nomoreparties.stellarburgers.elements.ButtonElement;
import site.nomoreparties.stellarburgers.elements.InputElement;
import site.nomoreparties.stellarburgers.elements.LinkElement;
import site.nomoreparties.stellarburgers.model.UserAuthorization;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {
    private final String PAGE_TITLE_TEXT = "Вход";

    private final InputElement emailInput;
    private final InputElement passwordInput;
    private final ButtonElement loginButton;
    private final LinkElement registerLink;
    private final LinkElement restorePasswordLink;

    public LoginPage() {
        emailInput = new InputElement(".//label[text()='Email']/parent::*/input");
        passwordInput = new InputElement(".//input[@name='Пароль']");
        loginButton = new ButtonElement(".//button[text()='Войти']");
        registerLink = new LinkElement(".//a[@href='/register']");
        restorePasswordLink = new LinkElement(".//a[@href='/forgot-password']");
    }

    @Override
    public void waitForLoaded() {
        String locator = String.format(".//h2[text()='%s']", PAGE_TITLE_TEXT);
        $(byXpath(locator)).shouldBe(visible);
    }

    public MainPage login(UserAuthorization userAuthorization) {
        emailInput.clearAndSetValue(userAuthorization.getEmail());
        passwordInput.clearAndSetValue(userAuthorization.getPassword());
        return (MainPage) PageNavigator.navigate(loginButton, MainPage.class);
    }

    public RegisterPage goToRegisterPage() {
        return (RegisterPage) PageNavigator.navigate(registerLink, RegisterPage.class);
    }

    public RestorePasswordPage goToRestorePasswordPage() {
        return (RestorePasswordPage) PageNavigator.navigate(restorePasswordLink, RestorePasswordPage.class);
    }
}
