package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import site.nomoreparties.stellarburgers.pageobject.RegisterPage;
import site.nomoreparties.stellarburgers.services.APIUserService;


import static org.assertj.core.api.Assertions.assertThat;

public class UserRegistrationTest extends  BaseTest {
    private final String EXPECTED_INVALID_PASSWORD_MESSAGE = "Некорректный пароль";

    @Test
    public void registerUserTest() {
        user = APIUserService.getInstance().generateUserAuthorization(8);

        startMainPage.goToLoginPageByEnterToAccountButton()
                .goToRegisterPage()
                .register(user);

        checkUserRegistration();
    }



    @Test
    public void registerUserWithShortPasswordTest() {
        user = APIUserService.getInstance().generateUserAuthorization(5);

        RegisterPage registerPage = startMainPage.goToLoginPageByEnterToAccountButton()
                .goToRegisterPage();

        registerPage.invalidRegister(user);
        checkInvalidPasswordUserRegistration(registerPage);
    }

    @Step("Check user registration (positive)")
    public void checkUserRegistration() {
        String accessToken = APIUserService.getInstance().tryLoginAndGetAccessToken(user);
        assertThat(accessToken).isNotBlank();
    }

    @Step("Check invalid user registration")
    public void checkInvalidPasswordUserRegistration(RegisterPage registerPage) {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(registerPage.hasPasswordInvalidCSS())
                    .as("Проверяем кнопку стили поля Пароль").isTrue();

            softAssertions.assertThat(registerPage.getPasswordInvalidMessage())
                    .as("Проверяем сообщение об ошибке поля Пароль").isEqualTo(EXPECTED_INVALID_PASSWORD_MESSAGE);

        });
    }
}
