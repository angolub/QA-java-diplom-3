package site.nomoreparties.stellarburgers.pageobject;

import site.nomoreparties.stellarburgers.elements.LinkElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class RestorePasswordPage extends BasePage {
    private final String PAGE_TITLE_TEXT = "Восстановление пароля";
    private final LinkElement loginLink;

    public RestorePasswordPage() {
        loginLink = new LinkElement(".//a[@href='/login']");
    }

    @Override
    public void waitForLoaded() {
        String locator = String.format(".//h2[text()='%s']", PAGE_TITLE_TEXT);
        $(byXpath(locator)).shouldBe(visible);
    }

    public LoginPage goToLoginPage() {
        return (LoginPage) PageNavigator.navigate(loginLink, LoginPage.class);
    }
}
