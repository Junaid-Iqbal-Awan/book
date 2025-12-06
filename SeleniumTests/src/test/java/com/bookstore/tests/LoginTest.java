package com.bookstore.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for the Login functionality of the BookStore application.
 * Tests: Login button, login modal, form fields.
 */
public class LoginTest extends BaseTest {
    
    /**
     * Test Case 27: Verify login button is displayed in header
     */
    @Test(priority = 1)
    public void testLoginButtonDisplayed() {
        navigateToHome();
        
        // Wait for login button in header
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".login-button, button")));
        
        Assert.assertTrue(loginButton.isDisplayed(), "Login button should be displayed in header");
    }
    
    /**
     * Test Case 28: Verify clicking login button opens login modal
     */
    @Test(priority = 2)
    public void testLoginModalOpens() {
        navigateToHome();
        
        // Click login button
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".login-button")));
        loginButton.click();
        
        // Wait for login modal to appear
        WebElement loginModal = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".modal-overlay, .modal-content")));
        
        Assert.assertTrue(loginModal.isDisplayed(), "Login modal should be displayed after clicking login button");
    }
    
    /**
     * Test Case 29: Verify login form has email and password fields
     */
    @Test(priority = 3)
    public void testLoginFormFields() {
        navigateToHome();
        
        // Click login button
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".login-button")));
        loginButton.click();
        
        // Wait for modal and check for email field
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".modal-content input[type='email']")));
        Assert.assertTrue(emailField.isDisplayed(), "Email field should be displayed in login modal");
        
        // Check for password field
        WebElement passwordField = driver.findElement(By.cssSelector(".modal-content input[type='password']"));
        Assert.assertTrue(passwordField.isDisplayed(), "Password field should be displayed in login modal");
    }
    
    /**
     * Test Case 30: Verify login form has submit button
     */
    @Test(priority = 4)
    public void testLoginSubmitButton() {
        navigateToHome();
        
        // Click login button
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".login-button")));
        loginButton.click();
        
        // Wait for submit button in modal
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".modal-content button[type='submit']")));
        
        Assert.assertTrue(submitButton.isDisplayed(), "Submit button should be displayed in login modal");
        Assert.assertTrue(submitButton.isEnabled(), "Submit button should be enabled");
    }
}
