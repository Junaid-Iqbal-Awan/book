package com.bookstore.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for the Signup page of the BookStore application.
 * Tests: Form fields, form validation.
 */
public class SignupPageTest extends BaseTest {

    /**
     * Test Case 14: Verify signup form has all required fields
     */
    @Test(priority = 1)
    public void testSignupFormFields() {
        navigateTo("/signup");

        // Wait for signup form to load
        WebElement signupForm = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".signup-form, form")));

        Assert.assertTrue(signupForm.isDisplayed(), "Signup form should be displayed");

        // Verify name field exists
        WebElement nameField = driver.findElement(By.cssSelector("input#name, input[placeholder*='name']"));
        Assert.assertTrue(nameField.isDisplayed(), "Name field should be displayed");

        // Verify email field exists
        WebElement emailField = driver.findElement(By.cssSelector("input#email, input[type='email']"));
        Assert.assertTrue(emailField.isDisplayed(), "Email field should be displayed");

        // Verify password field exists
        WebElement passwordField = driver.findElement(By.cssSelector("input#password, input[type='password']"));
        Assert.assertTrue(passwordField.isDisplayed(), "Password field should be displayed");
    }

    /**
     * Test Case 15: Verify signup page header is displayed
     */
    @Test(priority = 2)
    public void testSignupPageHeader() {
        navigateTo("/signup");

        // Wait for page header
        WebElement header = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("h2")));

        String headerText = header.getText().toLowerCase();
        System.out.println("Signup page header: " + headerText);

        Assert.assertTrue(headerText.contains("signup") || headerText.contains("sign up"),
                "Signup page header should contain 'Signup'");
    }

    /**
     * Test Case 16: Verify signup submit button exists
     */
    @Test(priority = 3)
    public void testSignupSubmitButton() {
        navigateTo("/signup");

        // Wait for signup button
        WebElement signupButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[type='submit'], .submit-button")));

        Assert.assertTrue(signupButton.isDisplayed(), "Signup button should be displayed");
        Assert.assertTrue(signupButton.isEnabled(), "Signup button should be enabled");
    }

    /**
     * Test Case 17: Verify close button exists on signup form
     */
    @Test(priority = 4)
    public void testSignupCloseButton() {
        navigateTo("/signup");

        // Wait for close button
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".close-button, button[type='button']")));

        Assert.assertTrue(closeButton.isDisplayed(), "Close button should be displayed");
    }
}
