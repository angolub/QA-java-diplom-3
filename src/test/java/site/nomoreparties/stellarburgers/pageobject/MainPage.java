package site.nomoreparties.stellarburgers.pageobject;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import site.nomoreparties.stellarburgers.elements.ButtonElement;
import site.nomoreparties.stellarburgers.elements.LinkElement;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class MainPage extends BasePage {
    private final String SELECTED_TAB_CSS = "tab_tab_type_current__2BEPc";

    private final ButtonElement enterToAccountButton;
    private final ButtonElement makeOrderButton;
    private final LinkElement profileLink;
    private final ElementsCollection designerTabElementsArray;

    private void waitChangeCSSClassTab(int index) {
        int i = 0;
        for (SelenideElement tab : designerTabElementsArray) {
            if (index == i) {
                tab.shouldHave(cssClass(SELECTED_TAB_CSS));
            } else {
                tab.shouldNotHave(cssClass(SELECTED_TAB_CSS));
            }
            i++;
        }
    }
    public MainPage() {
        enterToAccountButton = new ButtonElement(".//button[text()='Войти в аккаунт']");
        makeOrderButton = new ButtonElement(".//button[text()='Оформить заказ']");
        profileLink = new LinkElement(".//a[@href='/account']");
        designerTabElementsArray = $$(byXpath(".//div[contains(@class, 'tab_tab__1SPyG')]"));
    }

    @Override
    public void waitForLoaded() {
        $(byClassName("BurgerConstructor_basket__list__l9dp_")).shouldBe(visible);
    }

    public LoginPage goToLoginPageByEnterToAccountButton() {
        return (LoginPage) PageNavigator.navigate(enterToAccountButton, LoginPage.class);
    }

    public LoginPage goToLoginPageByProfileLink() {
        return (LoginPage) PageNavigator.navigate(profileLink, LoginPage.class);
    }

    public ProfilePage goToProfilePage() {
        return (ProfilePage) PageNavigator.navigate(profileLink, ProfilePage.class);
    }

    public boolean isDisplayedMakeOrderButton() {
        return makeOrderButton.isDisplayedButton();
    }

    public String getTitlePage() {
        return $(byTagName("h1")).getText();
    }

    public void designerTabClick(int index) {
        SelenideElement selectedTab = designerTabElementsArray.get(index);
        selectedTab.click();

        waitChangeCSSClassTab(index);
    }

    public boolean isDesignerTabSelected(int index) {
        waitChangeCSSClassTab(index);
        return true;
    }

    public long getDesignerContainerScrollPosition() {
        Long scrollPosition = executeJavaScript("return document.querySelector(\".BurgerIngredients_ingredients__menuContainer__Xu3Mo\").scrollTop;");
        return (scrollPosition != null) ? scrollPosition : 0;
    }
}
