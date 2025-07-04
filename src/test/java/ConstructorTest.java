import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import page.MainPage;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ConstructorTest {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    WebDriver webDriver;
    // Параметр для браузера
    @Parameterized.Parameter
    public String browserName;

    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Object[] data() {
        return new Object[] {
                "chrome",
                "yandex"
        };
    }

    @Before
    public void before() {
        RestAssured.baseURI = BASE_URL;
        // И настройка браузера в зависимости от параметра
        if (browserName.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "/Users/leanbre/Downloads/chromedriver-mac-arm64/chromedriver");
            webDriver = new ChromeDriver();
        } else if (browserName.equals("yandex")) {
            System.setProperty("webdriver.chrome.driver", "/Users/leanbre/Downloads/chromedriver-mac-ya/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
            webDriver = new ChromeDriver(options);
        }
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @After
    public void after() {
        webDriver.quit();
    }

    @Test
    @DisplayName("Открытие вкладки 'Булки'")
    @Description("В рамках теста открываем главную страницу, кликаем на другую вкладку (в тесте - 'Начинки') затем на вкладку 'Булки', а потом сверяем локацию")
    public void bunsTabTest() {
        MainPage mainPage = new MainPage(webDriver);
        // Открываем страницу
        mainPage.openPage();
        // Получаем положение блока ингредиентов
        int expectedLocation = mainPage.getIngredientTitleExpectedLocation();
        // Нажимаем на другую вкладку, чтобы вкладка "Булки" стала кликабельной
        mainPage.fillingsTabClick();
        // А теперь нажимаем на вкладку "Булки"
        mainPage.bunsTabClick();
        // Проверяем, что расположение на странице совпало
        assertEquals(expectedLocation, mainPage.getBunsLocation());
    }


    @Test
    @DisplayName("Открытие вкладки 'Соусы'")
    @Description("В рамках теста открываем главную страницу и переходим на вкладку соусов, потом сверяем локацию")
    public void saucesTabTest() {
        MainPage mainPage = new MainPage(webDriver);
        // Открываем страницу
        mainPage.openPage();
        // Получаем положение блока ингредиентов
        int expectedLocation = mainPage.getIngredientTitleExpectedLocation();
        // Нажимапем на вкладку "Соусы"
        mainPage.saucesTabClick();
        // Проверяем, что расположение на странице совпало
        assertEquals(expectedLocation, mainPage.getSaucesLocation());
    }

    @Test
    @DisplayName("Открытие вкладки 'Начинки'")
    @Description("В рамках теста открываем главную страницу и переходим на вкладку начинок, потом сверяем локацию")
    public void fillingsTabTest() {
        MainPage mainPage = new MainPage(webDriver);
        // Открываем страницу
        mainPage.openPage();
        // Получаем положение блока ингредиентов
        int expectedLocation = mainPage.getIngredientTitleExpectedLocation();
        // Нажимаем на вкладку "Начинки"
        mainPage.fillingsTabClick();
        // Проверяем, что расположение на странице совпало
        assertEquals(expectedLocation, mainPage.getFillingsLocation());
    }
}
