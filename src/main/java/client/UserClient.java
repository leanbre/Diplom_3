
package client;


import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.User;
import model.UserCredentials;

import static io.restassured.RestAssured.given;

public class UserClient {

    private static String token;

    // Пути до ручек API для пользователя
    private static final String AUTH_BASE_URL = "/api/auth";
    private static final String USER_URL = AUTH_BASE_URL + "/user";
    private static final String REGISTER_USER_URL = AUTH_BASE_URL + "/register";
    private static final String LOG_IN_USER_URL = AUTH_BASE_URL + "/login";

    @Step("Создание/регистрация пользователя")
    public static Response registerUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(REGISTER_USER_URL);
    }

    @Step("Удаление пользователя")
    public static Response deleteUser(String token) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .header("authorization", token)
                .when()
                .delete(USER_URL);
    }

    @Step("Авторизация пользователя с помощью логина и пароля")
    public static Response loginUser(UserCredentials userCredentials) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userCredentials)
                .when()
                .post(LOG_IN_USER_URL);
    }
}
