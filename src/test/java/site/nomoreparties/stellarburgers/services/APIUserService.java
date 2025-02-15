package site.nomoreparties.stellarburgers.services;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import site.nomoreparties.stellarburgers.model.UserAuthorization;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class APIUserService {
    private static final String REGISTER_URL = "/api/auth/register";
    private static final String USER_URL = "/api/auth/user";
    private final static String LOGIN_URL = "/api/auth/login";
    private static final String ACCESS_TOKEN_PREFIX = "Bearer ";
    private static final String ACCESS_TOKEN_RESPONSE_PARAM = "accessToken";
    private static APIUserService instance;

    private APIUserService() {
        RestAssured.baseURI= "https://stellarburgers.nomoreparties.site/";
    }

    private String cutHeaderFromAccessToken(String accessToken){
        if (accessToken == null)
            return "";

        return accessToken.substring(ACCESS_TOKEN_PREFIX.length());
    }

    public static APIUserService getInstance() {
        if (instance == null) {
            instance = new APIUserService();
        }
        return instance;
    }

    public UserAuthorization generateUserAuthorization(int lengthPassword) {
        Faker faker = new Faker(new Locale("ru-RU"));
        String email = faker.bothify("??????##@yandex.ru");
        String password = faker.regexify(String.format("[a-z1-9]{%d}", lengthPassword));
        String name = faker.name().fullName();

        return new UserAuthorization(email, password, name, null);
    }

    @Step("Send POST request to register user")
    public UserAuthorization registerUser() {
        UserAuthorization user = generateUserAuthorization(8);
        Response response = given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(REGISTER_URL);

        response.then().statusCode(HttpStatus.SC_OK);
        user.setAccessToken(response.jsonPath().get(ACCESS_TOKEN_RESPONSE_PARAM));
        return user;
    }

    @Step("Send DELETE request to delete user")
    public void deleteUser(UserAuthorization user) {
        String token = cutHeaderFromAccessToken(user.getAccessToken());

        UserAuthorization deletedUser = UserAuthorization.builder()
                .email(user.getEmail())
                .build();

        given()
                .header("Content-type", "application/json")
                .auth().oauth2(token)
                .and()
                .body(deletedUser)
                .when()
                .delete(USER_URL)
                .then().statusCode(HttpStatus.SC_ACCEPTED);
    }

    @Step("Send POST request to try to login user and get access toket")
    public String tryLoginAndGetAccessToken(UserAuthorization user) {
        if (user == null)
            return null;

        UserAuthorization authUser = UserAuthorization.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        Response response =  given()
                .header("Content-type", "application/json")
                .body(authUser)
                .when()
                .post(LOGIN_URL);

        int statusCode = response.getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            return response.jsonPath().get("accessToken");
        }

        return null;
    }
}
