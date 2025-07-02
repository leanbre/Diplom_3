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
import page.ForgotPasswordPage;
import page.LoginPage;
import page.MainPage;
import page.RegisterPage;

import java.util.concurrent.TimeUnit;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class LogInTest {
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
        // И закрываем браузер
        webDriver.quit();
    }

    @Test
    @DisplayName("Авторизация пользователя через кнопку 'Войти в аккаунт' на главной странице")
    @Description("Предварительно созданным пользователем проверяем возможность входа по кнопке 'Войти в аккаунт'")
    public void logInButtonMainPageTest() {
        // Создаем объекты страниц
        MainPage mainPage = new MainPage(webDriver);
        LoginPage loginPage = new LoginPage(webDriver);
        // Открываем главную страницу
        mainPage.openPage();
        // Нажимаем кнопку 'Войти в аккаунт'
        mainPage.logInButtonClick();
        // Вводим данные
        loginPage.inputEmailField(defaultUserEmail);
        loginPage.inputPasswordField(defaultUserPassword);
        // Нажимаем кнопку "Войти"
        loginPage.loginButtonClick();
        // Проверяем, что после успешного входа увидели надпись "Оформить заказ"
        assertEquals("Оформить заказ", mainPage.getBasketButtonText());
    }

    @Test
    @DisplayName("Авторизация пользователя через кнопку 'Личный кабинет' на главной странице")
    @Description("Предварительно созданным пользователем проверяем возможность входа по кнопке 'Личный кабинет'")
    public void logInViaPersonalCabinetButtonMainPageTest() {
        // Создаем объекты страниц
        MainPage mainPage = new MainPage(webDriver);
        LoginPage loginPage = new LoginPage(webDriver);
        // Открываем главную страницу
        mainPage.openPage();
        // Нажимаем кнопку 'Личный кабинет'
        mainPage.personalCabinetButtonClick();
        // Вводим данные
        loginPage.inputEmailField(defaultUserEmail);
        loginPage.inputPasswordField(defaultUserPassword);
        // Нажимаем кнопку "Войти"
        loginPage.loginButtonClick();
        // Проверяем, что после успешного входа увидели надпись "Оформить заказ"
        assertEquals("Оформить заказ", mainPage.getBasketButtonText());
    }

    @Test
    @DisplayName("Авторизация пользователя через кнопку 'Войти' на странице регистрации")
    @Description("Предварительно созданным пользователем проверяем возможность входа по кнопке 'Войти' на странице регистрации пользователя")
    public void logInViaLoginButtonRegisterPageTest() {
        // Создаем объекты страниц
        MainPage mainPage = new MainPage(webDriver);
        RegisterPage registerPage = new RegisterPage(webDriver);
        LoginPage loginPage = new LoginPage(webDriver);
        // Открываем страницу регистрации
        registerPage.openPage();
        // Нажимаем кнопку 'Войти'
        registerPage.logInButtonClick();
        // Вводим данные
        loginPage.inputEmailField(defaultUserEmail);
        loginPage.inputPasswordField(defaultUserPassword);
        // Нажимаем кнопку "Войти"
        loginPage.loginButtonClick();
        // Проверяем, что после успешного входа увидели надпись "Оформить заказ"
        assertEquals("Оформить заказ", mainPage.getBasketButtonText());
    }

    @Test
    @DisplayName("Авторизация пользователя через кнопку 'Войти' на странице восстановления пароля")
    @Description("Предварительно созданным пользователем проверяем возможность входа по кнопке 'Войти' на странице восстановления пароля пользователя")
    public void logInViaLoginButtonForgotPasswordPageTest() {
        // Создаем объекты страниц
        MainPage mainPage = new MainPage(webDriver);
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(webDriver);
        LoginPage loginPage = new LoginPage(webDriver);
        // Открываем страницу восстановления пароля
        forgotPasswordPage.openPage();
        // Нажимаем кнопку 'Войти'
        forgotPasswordPage.loginButtonClick();
        // Вводим данные
        loginPage.inputEmailField(defaultUserEmail);
        loginPage.inputPasswordField(defaultUserPassword);
        // Нажимаем кнопку "Войти"
        loginPage.loginButtonClick();
        // Проверяем, что после успешного входа увидели надпись "Оформить заказ"
        assertEquals("Оформить заказ", mainPage.getBasketButtonText());
    }
}
