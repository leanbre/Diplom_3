import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import page.RegisterPage;

import java.util.concurrent.TimeUnit;

import static model.UserCredentials.getCredentialsFromUser;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegisterTest {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    WebDriver webDriver;
    String defaultUserName;
    String defaultUserEmail;
    String defaultUserPassword;
    private String token;

    @Before
    public void before() {
        RestAssured.baseURI = BASE_URL;
        // Заполняем поля для пользователя которого будем создавать для тестов
        defaultUserName = "Имя" + System.currentTimeMillis();
        defaultUserEmail = "email_" + System.currentTimeMillis() + "@yandex.ru";
        defaultUserPassword = "Password" + System.currentTimeMillis();
        // Создаем существующего пользователя для использования в тестах
        User user = new User(defaultUserName, defaultUserEmail, defaultUserPassword);
        // Регистрируем пользователя
        Response registerResponse = UserClient.registerUser(user);
        // Прихраниваем токен для последующей чистки в конце тестов
        token = registerResponse.path("accessToken");
        // Запуск ChromeDriver
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
    }

    @After
    public void after() {
        // Если у нас остался заполненным token, значит, данные требуют очистки
        if (token != null) {
            Response deleteResponse = UserClient.deleteUser(token);
            // А теперь проверяем код ответа, что удаление прошло успешно
            deleteResponse
                    .then()
                    .assertThat()
                    .body("success", equalTo(true))
                    .and()
                    .body("message", equalTo("User successfully removed"))
                    .and()
                    .statusCode(SC_ACCEPTED);
        }
    }

    @Test
    @DisplayName("Базовый тест регистрации")
    @Description("Проверяем, что стандартная регистрация проходит и успешно работает")
    public void registerBaseScenarioTest() {
        // Создаем объект страницы регистрации
        RegisterPage registerPage = new RegisterPage(webDriver);
        // Открываем страницу
        registerPage.openPage();
        // Заполняем поля для регистрации
        registerPage.inputNameField(defaultUserName);
        registerPage.inputEmailField(defaultUserEmail);
        registerPage.inputPasswordField(defaultUserPassword);
        // Нажимаем кнопку "Зарегистрироваться"
        registerPage.registerButtonClick();
        // Создаем объект пользователя с теми же данными
        User user = new User(defaultUserName, defaultUserEmail, defaultUserPassword);
        // А теперь проверяем, что наш пользователь создался через api
        Response loginResponse = UserClient.loginUser(getCredentialsFromUser(user));
        // Прихраниваем для дальнейшего удаления токен
        token = loginResponse.path("accessToken");
        // Проверяем статус
        assertEquals(SC_OK, loginResponse.statusCode());
    }

    @Test
    @DisplayName("Регистрация с некорректной длиной пароля")
    @Description("Минимальная длина пароля - 6 символов, в этом тесте проверяем, что такая регистрация не удастся")
    public void registerWithShortPasswordTest() {
        // Создаем объект страницы регистрации
        RegisterPage registerPage = new RegisterPage(webDriver);
        // Открываем страницу
        registerPage.openPage();
        // Заполняем поля для регистрации
        registerPage.inputNameField(defaultUserName);
        registerPage.inputEmailField(defaultUserEmail);
        // Делаем пароль меньше положенного, шести символов
        registerPage.inputPasswordField("12345");
        // Нажимаем кнопку "Зарегистрироваться"
        registerPage.registerButtonClick();
        // Проверяем, что получили ошибку
        assertTrue(registerPage.isIncorrectPasswordLabelAppeared());
    }
}
