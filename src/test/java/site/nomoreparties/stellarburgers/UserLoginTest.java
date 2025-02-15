package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.pageobject.LoginPage;
import site.nomoreparties.stellarburgers.pageobject.MainPage;
import site.nomoreparties.stellarburgers.services.APIUserService;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.localStorage;

public class UserLoginTest extends BaseTest {
    private static final String ACCESS_TOKEN_LOCAL_STORAGE_PARAM = "accessToken";

    @Override
    @Before
    public void startUp() throws IOException {
        super.startUp();
        user = APIUserService.getInstance().registerUser();
    }

    @Test
    public void loginByEnterToAccountButtonTest() {
        MainPage mainPageAfterLogin = startMainPage.goToLoginPageByEnterToAccountButton()
                .login(user);
        checkUserLogin(mainPageAfterLogin);
    }

    @Test
    public void loginByProfilePageTest() {
        MainPage mainPageAfterLogin = startMainPage.goToLoginPageByProfileLink()
                .login(user);
        checkUserLogin(mainPageAfterLogin);
    }

    @Test
    public void loginByRegistrationPageTest() {
        MainPage mainPageAfterLogin = startMainPage.goToLoginPageByEnterToAccountButton()
                .goToRegisterPage()
                .goToLoginPage()
                .login(user);
        checkUserLogin(mainPageAfterLogin);
    }

    @Test
    public void loginByRestorePasswordPageTest() {
        MainPage mainPageAfterLogin = startMainPage.goToLoginPageByEnterToAccountButton()
                .goToRestorePasswordPage()
                .goToLoginPage()
                .login(user);
        checkUserLogin(mainPageAfterLogin);
    }

    @Step("Check correct user login")
    public void checkUserLogin(MainPage mainPageAfterLogin) {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(mainPageAfterLogin.isDisplayedMakeOrderButton())
                    .as("Проверяем кнопку Оформить заказ").isTrue();

            softAssertions.assertThat(localStorage().getItem(ACCESS_TOKEN_LOCAL_STORAGE_PARAM))
                    .as("Проверяем accessToken в локальном хранилище").isNotBlank();

        });
    }

}
