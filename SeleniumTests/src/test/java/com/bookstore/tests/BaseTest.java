package com.bookstore.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

/**
 * Base test class that provides common setup and teardown for all Selenium
 * tests.
 * Configures headless Chrome for CI/CD pipeline compatibility.
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static String BASE_URL;

    @BeforeClass
    public void setUpClass() {
        // Get base URL from system property or use default
        BASE_URL = System.getProperty("base.url", "http://localhost:80");
        System.out.println("Using base URL: " + BASE_URL);

        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        // Headless mode for CI/CD pipeline
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Navigate to a specific path relative to the base URL
     */
    protected void navigateTo(String path) {
        String url = BASE_URL + path;
        driver.get(url);
    }

    /**
     * Navigate to the home page
     */
    protected void navigateToHome() {
        driver.get(BASE_URL);
    }
}
