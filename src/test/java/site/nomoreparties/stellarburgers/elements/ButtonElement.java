package site.nomoreparties.stellarburgers.elements;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;

public class ButtonElement implements IElementClickable {
    SelenideElement buttonElement;

    public ButtonElement (String locator) {
        buttonElement = $(new By.ByXPath(locator));
    }

    public void clickElement() {
        buttonElement.scrollIntoView(true);
        buttonElement.shouldBe(enabled);
        buttonElement.click();
    }

    public boolean isDisplayedButton() {
        return buttonElement.isDisplayed();
    }
}
