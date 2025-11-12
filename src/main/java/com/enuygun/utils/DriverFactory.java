package com.enuygun.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverFactory {
    public static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver(){
        if (driver.get() == null) {
            String browser = System.getProperty("browser");
            if (browser == null || browser.isEmpty()) {
                browser = ConfigReader.get("browser");
            }

            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver.set(new FirefoxDriver());
                    break;
                default:
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver());
                    break;
            }

            long implicit = Long.parseLong(ConfigReader.get("implicitWait"));
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicit));
            driver.get().manage().window().maximize();

            String baseUrl = ConfigReader.get("baseUrl");
            driver.get().get(baseUrl);
        }
        return driver.get();
    }

    public static void closeDriver(){
        if(driver.get() != null) {
            try {
                driver.get().quit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                driver.remove();
            }
        }
    }

}
