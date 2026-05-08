package com.bookstore.tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookFlowTests extends BaseUiTest {

  @Test
  void booksPageShowsSeededBookCard() {
    ApiHelper.BookData book = ApiHelper.createBook(baseUrl, "Selenium Book " + System.nanoTime(), "12.99");

    open("/course");
    WebElement cardTitle = waitForVisible(By.xpath("//h4[contains(.,'" + book.name + "')]"));
    assertTrue(cardTitle.isDisplayed());
  }

  @Test
  void bookDetailDisplaysSeededBookInfo() {
    ApiHelper.BookData book = ApiHelper.createBook(baseUrl, "Detail Book " + System.nanoTime(), "9.50");

    open("/book/" + book.id);
    WebElement title = waitForVisible(By.cssSelector(".book-info h1"));
    assertEquals(book.name, title.getText().trim());
    WebElement price = waitForVisible(By.xpath("//div[contains(@class,'book-info')]//strong[contains(.,'Price')]"));
    assertTrue(price.getText().contains("Price"));
  }

  @Test
  void buyNowNavigatesToCheckoutWithBookId() {
    ApiHelper.BookData book = ApiHelper.createBook(baseUrl, "Buy Now Book " + System.nanoTime(), "15.00");

    open("/book/" + book.id);
    waitForClickable(By.cssSelector("button.buy-now-button")).click();

    WebElement bookIdInput = waitForVisible(By.id("bookId"));
    assertEquals(book.id, bookIdInput.getAttribute("value"));
    assertTrue(driver.getCurrentUrl().contains("/checkout"));
  }

  @Test
  void checkoutTotalPriceUpdatesWithQuantity() {
    ApiHelper.BookData book = ApiHelper.createBook(baseUrl, "Price Book " + System.nanoTime(), "10");

    open("/checkout?bookId=" + book.id + "&price=" + book.price);
    WebElement quantity = waitForVisible(By.id("quantity"));
    quantity.clear();
    quantity.sendKeys("3");

    WebElement totalPrice = waitForVisible(By.id("totalPrice"));
    double total = Double.parseDouble(totalPrice.getAttribute("value"));
    assertEquals(30.0, total, 0.01);
  }

  @Test
  void checkoutSubmitShowsSuccessMessage() {
    ApiHelper.BookData book = ApiHelper.createBook(baseUrl, "Checkout Book " + System.nanoTime(), "8.25");

    open("/checkout?bookId=" + book.id + "&price=" + book.price);

    driver.findElement(By.id("quantity")).clear();
    driver.findElement(By.id("quantity")).sendKeys("2");
    driver.findElement(By.id("name")).sendKeys("Checkout User");
    driver.findElement(By.id("email")).sendKeys(ApiHelper.uniqueEmail("checkout"));
    driver.findElement(By.id("address")).sendKeys("123 Test St");
    driver.findElement(By.id("city")).sendKeys("Testville");
    driver.findElement(By.id("state")).sendKeys("TS");
    driver.findElement(By.id("zip")).sendKeys("12345");
    driver.findElement(By.id("cardNumber")).sendKeys("4111111111111111");
    driver.findElement(By.id("expiryDate")).sendKeys("12/30");
    driver.findElement(By.id("cvv")).sendKeys("123");

    waitForClickable(By.cssSelector("button.checkout-button")).click();

    WebElement toast = waitForVisible(By.xpath("//*[contains(text(),'Checkout completed successfully')]"));
    assertTrue(toast.isDisplayed());
  }
}
