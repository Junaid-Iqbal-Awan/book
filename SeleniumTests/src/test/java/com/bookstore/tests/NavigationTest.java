package com.bookstore.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for navigation functionality in the BookStore application.
 * Tests: Navigation to Books, Contact, and About pages via header links.
 */
public class NavigationTest extends BaseTest {
    
    /**
     * Test Case 6: Verify navigation to Books page
     */
    @Test(priority = 1)
    public void testNavigationToBooks() {
        navigateToHome();
        
        // Find and click the Books link
        WebElement booksLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Books') or @href='/course']")));
        booksLink.click();
        
        // Wait for navigation and verify URL
        wait.until(ExpectedConditions.urlContains("/course"));
        
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after clicking Books: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("/course"), 
                "URL should contain '/course' after clicking Books link");
    }
    
    /**
     * Test Case 7: Verify navigation to Contact page
     */
    @Test(priority = 2)
    public void testNavigationToContact() {
        navigateToHome();
        
        // Find and click the Contact link
        WebElement contactLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Contact') or @href='/contact']")));
        contactLink.click();
        
        // Wait for navigation and verify URL
        wait.until(ExpectedConditions.urlContains("/contact"));
        
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after clicking Contact: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("/contact"), 
                "URL should contain '/contact' after clicking Contact link");
    }
    
    /**
     * Test Case 8: Verify navigation to About Us page
     */
    @Test(priority = 3)
    public void testNavigationToAbout() {
        navigateToHome();
        
        // Find and click the About Us link
        WebElement aboutLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'About') or @href='/about']")));
        aboutLink.click();
        
        // Wait for navigation and verify URL
        wait.until(ExpectedConditions.urlContains("/about"));
        
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after clicking About Us: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("/about"), 
                "URL should contain '/about' after clicking About Us link");
    }
    
    /**
     * Test Case 9: Verify Home link navigates back to homepage
     */
    @Test(priority = 4)
    public void testNavigationToHome() {
        // Start from About page
        navigateTo("/about");
        
        // Find and click the Home link
        WebElement homeLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Home') or @href='/']")));
        homeLink.click();
        
        // Wait a moment for navigation
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after clicking Home: " + currentUrl);
        
        // Check that we're at the home page - URL should not contain any specific page path
        boolean isHomePage = !currentUrl.contains("/about") && 
                            !currentUrl.contains("/course") && 
                            !currentUrl.contains("/contact") &&
                            !currentUrl.contains("/signup");
        
        Assert.assertTrue(isHomePage, 
                "URL should be the base URL after clicking Home link. Actual: " + currentUrl);
    }
}
