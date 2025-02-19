package site.nomoreparties.stellarburgers.pageobject;

import site.nomoreparties.stellarburgers.elements.IElementClickable;

import static com.codeborne.selenide.Selenide.page;

public class PageNavigator {

    public static BasePage navigate(IElementClickable navigateButton, Class basePageClass) {
        navigateButton.clickElement();
        BasePage basePage = (BasePage) page(basePageClass);
        basePage.waitForLoaded();
        return basePage;
    }
}
