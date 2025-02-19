package site.nomoreparties.stellarburgers.pageobject;

import site.nomoreparties.stellarburgers.elements.ButtonElement;
import site.nomoreparties.stellarburgers.elements.LinkElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProfilePage extends BasePage {
    private final String PROFILE_LINK_TEXT = "Профиль";
    private final ButtonElement logoutButton;
    private final LinkElement logoLink;
    private final LinkElement designerLink;
    private final String editableInputLocator = ".//ul[@class='Profile_profileList__3vTor']/li";

    public ProfilePage() {
        logoutButton = new ButtonElement(".//button[text()='Выход']");
        designerLink = new LinkElement(".//p[text()='Конструктор']/parent::a");
        logoLink = new LinkElement(".//div[@class='AppHeader_header__logo__2D0X2']/a");
    }

    @Override
    public void waitForLoaded() {
        $(byLinkText(PROFILE_LINK_TEXT)).shouldBe(visible);
    }

    public LoginPage logout(){
        return (LoginPage) PageNavigator.navigate(logoutButton, LoginPage.class);
    }

    public MainPage goToMainPageByDesignerLink() {
        return (MainPage) PageNavigator.navigate(designerLink, MainPage.class);
    }

    public MainPage goToMainPageByLogo() {
        return (MainPage) PageNavigator.navigate(logoLink, MainPage.class);
    }

    public int getEditableInputCount() {
        return $$(byXpath(editableInputLocator)).size();
    }
}
