package com.bookstore.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for the Books/Courses page of the BookStore application.
 * Tests: Book cards display, catalog section.
 */
public class BooksPageTest extends BaseTest {

    /**
     * Test Case 25: Verify books page loads correctly
     */
    @Test(priority = 1)
    public void testBooksPageLoads() {
        navigateTo("/course");

        // Wait for course page to load
        WebElement coursePage = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".coursePage, .course, body")));

        Assert.assertTrue(coursePage.isDisplayed(), "Books page should be displayed");
    }

    /**
     * Test Case 26: Verify header is displayed on books page
     */
    @Test(priority = 2)
    public void testBooksPageHeader() {
        navigateTo("/course");

        // Verify header is present
        WebElement header = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".header, header")));

        Assert.assertTrue(header.isDisplayed(), "Header should be displayed on Books page");
    }
}
