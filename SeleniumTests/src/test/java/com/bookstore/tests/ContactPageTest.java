package com.bookstore.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for the Contact page of the BookStore application.
 * Tests: Form presence, form fields validation.
 */
public class ContactPageTest extends BaseTest {

    /**
     * Test Case 10: Verify contact form is displayed with all fields
     */
    @Test(priority = 1)
    public void testContactFormPresence() {
        navigateTo("/contact");

        // Wait for contact form to load
        WebElement contactForm = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".contact-form, form")));

        Assert.assertTrue(contactForm.isDisplayed(), "Contact form should be displayed");

        // Verify name field exists
        WebElement nameField = driver.findElement(By.cssSelector("input[name='name']"));
        Assert.assertTrue(nameField.isDisplayed(), "Name field should be displayed");

        // Verify email field exists
        WebElement emailField = driver.findElement(By.cssSelector("input[name='email']"));
        Assert.assertTrue(emailField.isDisplayed(), "Email field should be displayed");

        // Verify message field exists
        WebElement messageField = driver.findElement(By.cssSelector("textarea[name='message']"));
        Assert.assertTrue(messageField.isDisplayed(), "Message field should be displayed");
    }

    /**
     * Test Case 11: Verify contact page header is displayed
     */
    @Test(priority = 2)
    public void testContactPageHeader() {
        navigateTo("/contact");

        // Wait for page header
        WebElement header = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".contact-header h1, h1")));

        String headerText = header.getText().toLowerCase();
        System.out.println("Contact page header: " + headerText);

        Assert.assertTrue(headerText.contains("contact"),
                "Contact page header should contain 'contact'");
    }

    /**
     * Test Case 12: Verify submit button exists on contact form
     */
    @Test(priority = 3)
    public void testContactSubmitButton() {
        navigateTo("/contact");

        // Wait for submit button
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[type='submit'], .submit-button")));

        Assert.assertTrue(submitButton.isDisplayed(), "Submit button should be displayed");
        Assert.assertTrue(submitButton.isEnabled(), "Submit button should be enabled");

        String buttonText = submitButton.getText().toLowerCase();
        Assert.assertTrue(buttonText.contains("submit"),
                "Submit button should contain text 'Submit'");
    }

    /**
     * Test Case 13: Verify phone field exists on contact form
     */
    @Test(priority = 4)
    public void testContactPhoneField() {
        navigateTo("/contact");

        // Wait for phone field
        WebElement phoneField = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[name='phone']")));

        Assert.assertTrue(phoneField.isDisplayed(), "Phone field should be displayed");
        Assert.assertTrue(phoneField.isEnabled(), "Phone field should be enabled");
    }
}
