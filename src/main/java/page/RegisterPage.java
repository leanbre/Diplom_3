package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
    // Ссылка на основную страницу
    private static final String REGISTER_PAGE_URL = "https://stellarburgers.nomoreparties.site/register";

    // Селекторы полей регистрации
    // Поле "Имя"
    private final By nameField = By.xpath(".//label[text()='Имя']/../input");
    private final By emailField = By.xpath(".//label[text()='Email']/../input");
    private final By passwordField = By.xpath(".//label[text()='Пароль']/../input");
    // Селекторы кнопок
    // Кнопка "Зарегистрироваться"
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    // Кнопка "Войти"
    private final By loginButton = By.xpath(".//a[text()='Войти']");
    // Информационная надпись "Некорректный пароль"
    private final By loginError = By.xpath(".//p[text()='Некорректный пароль']");

    private final WebDriver webDriver;

    public RegisterPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    // Открытие страницы регистрации
    public void openPage() {
        webDriver.get(REGISTER_PAGE_URL);
    }

    // Заполнение полей для регистрации
    // Внесение данных в поле "Email"
    public void inputEmailField(String email) {
        webDriver.findElement(emailField).sendKeys(email);
    }

    // Внесение данных в поле "Имя"
    public void inputNameField(String name) {
        webDriver.findElement(nameField).sendKeys(name);
    }

    // Внесение данных в поле "Пароль"
    public void inputPasswordField(String password) {
        webDriver.findElement(passwordField).sendKeys(password);
    }

    // Нажатие на кнопку "Зарегистрироваться"
    public void registerButtonClick() {
        webDriver.findElement(registerButton).click();
    }

    // Нажатие на кнопку "Войти"
    public void logInButtonClick() {
        webDriver.findElement(loginButton).click();
    }

    // Метод проверки, отобразилась ли надпись "Некорректный пароль"
    public boolean isIncorrectPasswordLabelAppeared() {
        return webDriver.findElement(loginError).isDisplayed();
    }
}
