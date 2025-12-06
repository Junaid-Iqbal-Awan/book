package com.bookstore.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for the Homepage of the BookStore application.
 * Tests: Page title, logo, welcome banner, newsletter subscription.
 */
public class HomepageTest extends BaseTest {
    
    /**
     * Test Case 1: Verify page title is present
     */
    @Test(priority = 1)
    public void testPageTitle() {
        navigateToHome();
        String title = driver.getTitle();
        System.out.println("Page title: " + title);
        // Accept common application titles
        Assert.assertTrue(
                title.toLowerCase().contains("hogwarts") || 
                title.toLowerCase().contains("bookstore") ||
                title.toLowerCase().contains("vite") ||
                title.toLowerCase().contains("react"),
                "Page title should contain app name. Actual: " + title);
    }
    
    /**
     * Test Case 2: Verify logo is displayed on the homepage
     */
    @Test(priority = 2)
    public void testLogoPresence() {
        navigateToHome();
        
        // Wait for header to load and check for logo image
        WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".header .logo img, header .logo img, .logo img")));
        
        Assert.assertTrue(logo.isDisplayed(), "Logo should be displayed on the homepage");
    }
    
    /**
     * Test Case 3: Verify welcome banner text is displayed
     */
    @Test(priority = 3)
    public void testWelcomeBannerText() {
        navigateToHome();
        
        // Wait for banner section to load
        WebElement banner = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".bannerSection, .leftsection, .banner")));
        
        String bannerText = banner.getText().toLowerCase();
        System.out.println("Banner text: " + bannerText);
        
        Assert.assertTrue(bannerText.contains("welcome") || bannerText.contains("hogwarts"),
                "Banner should contain welcome message");
    }
    
    /**
     * Test Case 4: Verify newsletter subscription input exists
     */
    @Test(priority = 4)
    public void testNewsletterInputExists() {
        navigateToHome();
        
        // Wait for newsletter input field
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".inputWrapper input[type='email'], input[type='email']")));
        
        Assert.assertTrue(emailInput.isDisplayed(), "Newsletter email input should be displayed");
        Assert.assertTrue(emailInput.isEnabled(), "Newsletter email input should be enabled");
    }
    
    /**
     * Test Case 5: Verify subscribe button exists and is clickable
     */
    @Test(priority = 5)
    public void testSubscribeButtonExists() {
        navigateToHome();
        
        // Wait for subscribe button
        WebElement subscribeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Subscribe')]")));
        
        Assert.assertTrue(subscribeButton.isDisplayed(), "Subscribe button should be displayed");
        Assert.assertTrue(subscribeButton.isEnabled(), "Subscribe button should be enabled");
    }
}
