package com.bookstore.tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthTests extends BaseUiTest {

  @Test
  void signupStoresUserInLocalStorage() {
    open("/signup");
    clearLocalStorage();

    String email = ApiHelper.uniqueEmail("signup");
    driver.findElement(By.id("name")).sendKeys("Test User");
    driver.findElement(By.id("email")).sendKeys(email);
    driver.findElement(By.id("password")).sendKeys("TestPass123!");
    driver.findElement(By.id("confirm-password")).sendKeys("TestPass123!");

    waitForClickable(By.cssSelector("button.submit-button")).click();

    wait.until(d -> getLocalStorageItem("Users") != null);
    String stored = getLocalStorageItem("Users");
    assertNotNull(stored);
  }

  @Test
  void loginShowsLogoutButton() {
    String email = ApiHelper.uniqueEmail("login");
    String password = "TestPass123!";
    ApiHelper.createUser(baseUrl, "Login User", email, password);

    open("/");
    clearLocalStorage();
    openLoginModal();

    driver.findElement(By.cssSelector(".modal-content input[type='email']"))
      .sendKeys(email);
    driver.findElement(By.cssSelector(".modal-content input[type='password']"))
      .sendKeys(password);
    waitForClickable(By.cssSelector(".modal-content button[type='submit']")).click();

    WebElement logoutButton = waitForVisible(By.cssSelector("button.logout-button"));
    assertTrue(logoutButton.isDisplayed());
    closeLoginModalIfOpen();
  }

  @Test
  void logoutRedirectsToSignup() {
    String email = ApiHelper.uniqueEmail("logout");
    String password = "TestPass123!";
    ApiHelper.createUser(baseUrl, "Logout User", email, password);

    open("/");
    clearLocalStorage();
    openLoginModal();

    driver.findElement(By.cssSelector(".modal-content input[type='email']"))
      .sendKeys(email);
    driver.findElement(By.cssSelector(".modal-content input[type='password']"))
      .sendKeys(password);
    waitForClickable(By.cssSelector(".modal-content button[type='submit']")).click();

    closeLoginModalIfOpen();

    waitForClickable(By.cssSelector("button.logout-button")).click();
    WebElement heading = waitForVisible(By.cssSelector(".modal-content h2"));
    assertTrue(driver.getCurrentUrl().contains("/signup"));
    assertTrue(heading.getText().trim().equals("Signup"));
  }

  private void openLoginModal() {
    waitForClickable(By.cssSelector("button.login-button")).click();
    waitForVisible(By.cssSelector(".modal-content"));
  }

  private void closeLoginModalIfOpen() {
    if (!driver.findElements(By.cssSelector(".modal-overlay")).isEmpty()) {
      driver.findElement(By.cssSelector(".modal-overlay")).click();
    }
  }
}
