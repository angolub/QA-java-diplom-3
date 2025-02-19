package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.pageobject.MainPage;
import site.nomoreparties.stellarburgers.pageobject.ProfilePage;
import site.nomoreparties.stellarburgers.services.APIUserService;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.localStorage;
import static org.assertj.core.api.Assertions.assertThat;

public class UserProfileTest extends BaseTest {
    private final int EXPECTED_EDITABLE_FIELDS = 3;
    private final String EXPECTED_TITLE_MAIN_PAGE = "Соберите бургер";

    @Override
    @Before
    public void startUp() throws IOException {
        super.startUp();
        user = APIUserService.getInstance().registerUser();
    }

    @Test
    public void goToProfilePageTest() {
        ProfilePage profilePage = startMainPage.goToLoginPageByEnterToAccountButton()
                .login(user)
                .goToProfilePage();
        checkProfilePage(profilePage);
    }

    @Test
    public void logoutTest() {
        startMainPage.goToLoginPageByEnterToAccountButton()
                .login(user)
                .goToProfilePage()
                .logout();
        checkLogout();
    }

    @Test
    public void goToMainPageByDesignerLinkTest() {
        MainPage mainPage = startMainPage.goToLoginPageByEnterToAccountButton()
                .login(user)
                .goToProfilePage()
                .goToMainPageByDesignerLink();
        checkGoToMainPage(mainPage);
    }

    @Test
    public void goToMainPageByLogoTest() {
        MainPage mainPage = startMainPage.goToLoginPageByEnterToAccountButton()
                .login(user)
                .goToProfilePage()
                .goToMainPageByLogo();
        checkGoToMainPage(mainPage);
    }

    @Step("Check amount of editable fields")
    public void checkProfilePage(ProfilePage profilePage) {
        assertThat(profilePage.getEditableInputCount()).isEqualTo(EXPECTED_EDITABLE_FIELDS);
    }

    @Step("Check logout")
    public void checkLogout() {
        assertThat(localStorage().getItem("accessToken")).isBlank();
    }

    @Step("Check to go to Main page")
    public void checkGoToMainPage(MainPage mainPage) {
        assertThat(mainPage.getTitlePage()).isEqualTo(EXPECTED_TITLE_MAIN_PAGE);
    }
}
