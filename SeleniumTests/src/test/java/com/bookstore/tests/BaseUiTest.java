package com.bookstore.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseUiTest {
  protected WebDriver driver;
  protected WebDriverWait wait;
  protected String baseUrl;

  @BeforeEach
  void setUp() {
    baseUrl = TestConfig.getBaseUrl();
    ApiHelper.waitForAppReady(baseUrl);

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless=new");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--window-size=1280,800");

    driver = new ChromeDriver(options);
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @AfterEach
  void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  protected void open(String path) {
    driver.get(baseUrl + path);
  }

  protected WebElement waitForVisible(By by) {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
  }

  protected WebElement waitForClickable(By by) {
    return wait.until(ExpectedConditions.elementToBeClickable(by));
  }

  protected void clearLocalStorage() {
    ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
  }

  protected String getLocalStorageItem(String key) {
    return (String) ((JavascriptExecutor) driver)
      .executeScript("return window.localStorage.getItem(arguments[0]);", key);
  }
}
