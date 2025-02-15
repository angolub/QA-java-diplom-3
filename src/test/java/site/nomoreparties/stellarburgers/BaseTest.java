package site.nomoreparties.stellarburgers;

import org.junit.After;
import org.junit.Before;
import site.nomoreparties.stellarburgers.browser.Browser;
import site.nomoreparties.stellarburgers.pageobject.MainPage;
import site.nomoreparties.stellarburgers.model.UserAuthorization;
import site.nomoreparties.stellarburgers.services.APIUserService;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.open;

public class BaseTest {
    protected UserAuthorization user;
    protected MainPage startMainPage;

    @Before
    public void startUp() throws IOException {
        Browser.initBrowser();
        startMainPage = open("https://stellarburgers.nomoreparties.site/", MainPage.class);
        startMainPage.waitForLoaded();
    }

    @After
    public void tearDown() {
        String token = APIUserService.getInstance().tryLoginAndGetAccessToken(user);
        if ( token != null && !token.isBlank()) {
            user.setAccessToken(token);
            APIUserService.getInstance().deleteUser(user);
        }
    }
}
