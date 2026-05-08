package com.bookstore.tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationTests extends BaseUiTest {

  @Test
  void homeShowsBannerHeading() {
    open("/");
    WebElement heading = waitForVisible(By.cssSelector(".bannerSection h1"));
    assertEquals("Welcome to Hogwarts Bookstore", heading.getText().trim());
  }

  @Test
  void headerShowsNavLinks() {
    open("/");
    waitForVisible(By.cssSelector("header nav"));
    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
      By.cssSelector("header nav a"),
      3
    ));
    List<String> links = driver.findElements(By.cssSelector("header nav a"))
      .stream()
      .map(el -> el.getText().trim())
      .collect(Collectors.toList());
    assertTrue(links.contains("Home"));
    assertTrue(links.contains("Books"));
    assertTrue(links.contains("Contact"));
    assertTrue(links.contains("About Us"));
  }

  @Test
  void booksPageShowsIntroHeading() {
    open("/course");
    WebElement heading = waitForVisible(By.cssSelector(".Courses-top h1"));
    assertTrue(heading.getText().contains("Delighted"));
  }

  @Test
  void aboutPageShowsHero() {
    open("/about");
    WebElement heading = waitForVisible(By.cssSelector(".about-hero h1"));
    assertEquals("About Us", heading.getText().trim());
  }

  @Test
  void aboutContactButtonNavigatesToContact() {
    open("/about");
    waitForClickable(By.xpath("//button[contains(.,'Contact Us')]"))
      .click();
    WebElement heading = waitForVisible(By.cssSelector(".contact-header h1"));
    assertEquals("Contact Us", heading.getText().trim());
    assertTrue(driver.getCurrentUrl().contains("/contact"));
  }

  @Test
  void contactPageShowsFormFields() {
    open("/contact");
    waitForVisible(By.cssSelector("form.contact-form"));
    assertTrue(driver.findElement(By.name("name")).isDisplayed());
    assertTrue(driver.findElement(By.name("email")).isDisplayed());
    assertTrue(driver.findElement(By.name("message")).isDisplayed());
    assertTrue(driver.findElement(By.name("phone")).isDisplayed());
  }

  @Test
  void newsletterSubscribeShowsMessage() {
    open("/");
    WebElement input = waitForVisible(By.cssSelector(".inputWrapper input[type='email']"));
    input.sendKeys(ApiHelper.uniqueEmail("newsletter"));
    waitForClickable(By.xpath("//button[contains(.,'Subscribe')]"))
      .click();
    WebElement message = waitForVisible(By.cssSelector(".inputWrapper p"));
    String text = message.getText().trim();
    assertFalse(text.isEmpty());
    assertFalse(text.toLowerCase().contains("failed"));
  }
}
