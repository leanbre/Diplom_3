package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class DriverFactory {

    private static final String TESTS_PROPERTIES_PATH = "src/test/resources/application.properties";
    private static final String ALLURE_PROPERTIES_PATH = "target/allure-results/environment.properties";

    public static WebDriver createDriver() {
        String browser = getBrowserFromProperties();

        switch (browser.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "/Users/leanbre/Downloads/chromedriver-mac-arm64/chromedriver");
                // Создаем файл properties для allure-отчета
                createAllureEnvironmentFileIfNotExisted(browser.toLowerCase());
                return new ChromeDriver();
            case "yandex":
                System.setProperty("webdriver.chrome.driver", "/Users/leanbre/Downloads/chromedriver-mac-ya/chromedriver");
                ChromeOptions options = new ChromeOptions();
                options.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
                // Создаем файл properties для allure-отчета
                createAllureEnvironmentFileIfNotExisted(browser.toLowerCase());
                return new ChromeDriver(options);
            default:
                throw new IllegalArgumentException("Неизвестный браузер: " + browser);
        }
    }

    private static String getBrowserFromProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(TESTS_PROPERTIES_PATH)) {
            properties.load(fis);
            return properties.getProperty("browser", "chrome");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке application.properties", e);
        }
    }

    private static void createAllureEnvironmentFileIfNotExisted(String browser) {
        Properties props = new Properties();
        props.setProperty("browser", browser);

        Path path = Paths.get(ALLURE_PROPERTIES_PATH);

        try {
            // Убедимся, что директория существует
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            // Создаём файл только если он не существует
            if (!Files.exists(path)) {
                try (FileOutputStream fos = new FileOutputStream(path.toFile())) {
                    props.store(fos, "Allure environment variables");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать environment.properties для Allure", e);
        }
    }
}
