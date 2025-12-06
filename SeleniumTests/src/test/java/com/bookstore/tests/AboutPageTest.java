package com.bookstore.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for the About page of the BookStore application.
 * Tests: Page content, Contact Us button, experience section.
 */
public class AboutPageTest extends BaseTest {

    /**
     * Test Case 18: Verify About page hero section is displayed
     */
    @Test(priority = 1)
    public void testAboutPageHeroSection() {
        navigateTo("/about");

        // Wait for about hero section
        WebElement heroSection = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".about-hero, .container h1")));

        String heroText = heroSection.getText().toLowerCase();
        System.out.println("About page hero text: " + heroText);

        Assert.assertTrue(heroText.contains("about"),
                "About page hero should contain 'About'");
    }

    /**
     * Test Case 19: Verify About page main content section
     */
    @Test(priority = 2)
    public void testAboutPageContent() {
        navigateTo("/about");

        // Wait for about section
        WebElement aboutSection = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".about-section, .about-content")));

        Assert.assertTrue(aboutSection.isDisplayed(), "About content section should be displayed");

        // Check for main heading
        WebElement heading = driver.findElement(By.cssSelector(".about-text h2, h2"));
        String headingText = heading.getText().toLowerCase();
        System.out.println("About section heading: " + headingText);

        Assert.assertTrue(headingText.contains("bookstore") || headingText.contains("learners"),
                "About section should have relevant heading");
    }

    /**
     * Test Case 20: Verify Contact Us button on About page
     */
    @Test(priority = 3)
    public void testContactUsButton() {
        navigateTo("/about");

        // Wait for Contact Us button
        WebElement contactButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Contact')]")));

        Assert.assertTrue(contactButton.isDisplayed(), "Contact Us button should be displayed");
        Assert.assertTrue(contactButton.isEnabled(), "Contact Us button should be enabled");
    }

    /**
     * Test Case 21: Verify experience section on About page
     */
    @Test(priority = 4)
    public void testExperienceSection() {
        navigateTo("/about");

        // Wait for experience section
        WebElement experienceSection = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".experience, .about-images")));

        Assert.assertTrue(experienceSection.isDisplayed(), "Experience section should be displayed");

        String experienceText = experienceSection.getText();
        System.out.println("Experience section text: " + experienceText);

        Assert.assertTrue(experienceText.contains("50+") || experienceText.contains("Years"),
                "Experience section should show years of experience");
    }
}
