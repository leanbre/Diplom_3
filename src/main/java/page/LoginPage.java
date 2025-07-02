package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    // Ссылка на страницу авторизации
    private static final String LOGIN_URL = "https://stellarburgers.nomoreparties.site/login";
    // Селектор поля "Email"
    private final By EMAIL = By.xpath(".//input[@type='text']");
    // Селектор поля "Пароль"
    private final By PASSWORD = By.xpath(".//input[@type='password']");
    // Селектор кнопки "Войти"
    private final By LOGIN_BUTTON = By.xpath(".//button[text()='Войти']");

    // Добавляем драйвер для загрузки страницы
    private final WebDriver webDriver;

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    // Действия на странице
    // Открытие основной страницы
    public void openPage() {
        webDriver.get(LOGIN_URL);
        // Дожидаемся появления кнопки с "Войти в аккаунт"
        new WebDriverWait(webDriver, 5)
                .until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
    }

    // Внесение данных в поле "Email"
    public void inputEmailField(String email) {
        webDriver.findElement(EMAIL).sendKeys(email);
    }

    // Внесение данных в поле "Пароль"
    public void inputPasswordField(String password) {
        webDriver.findElement(PASSWORD).sendKeys(password);
    }

    // Нажатие кнопки "Войти"
    public void loginButtonClick() {
        webDriver.findElement(LOGIN_BUTTON).click();
    }
}
