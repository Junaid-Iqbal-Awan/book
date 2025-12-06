package com.bookstore.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for the Footer component of the BookStore application.
 * Tests: Footer presence on different pages.
 */
public class FooterTest extends BaseTest {

    /**
     * Test Case 22: Verify footer is displayed on homepage
     */
    @Test(priority = 1)
    public void testFooterOnHomepage() {
        navigateToHome();

        // Wait for footer to be present
        WebElement footer = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("footer, .footer")));

        Assert.assertTrue(footer.isDisplayed(), "Footer should be displayed on homepage");
    }

    /**
     * Test Case 23: Verify footer is displayed on About page
     */
    @Test(priority = 2)
    public void testFooterOnAboutPage() {
        navigateTo("/about");

        // Wait for footer to be present
        WebElement footer = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("footer, .footer")));

        Assert.assertTrue(footer.isDisplayed(), "Footer should be displayed on About page");
    }

    /**
     * Test Case 24: Verify footer is displayed on Contact page
     */
    @Test(priority = 3)
    public void testFooterOnContactPage() {
        navigateTo("/contact");

        // Wait for footer to be present
        WebElement footer = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("footer, .footer")));

        Assert.assertTrue(footer.isDisplayed(), "Footer should be displayed on Contact page");
    }
}
