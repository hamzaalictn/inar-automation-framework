package com.universitystore.utilities;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;


/**
 * A utility class containing helper methods for common browser related operations.
 */

public class BrowserUtils {


    /**
     * Takes a screenshot of the current web page and returns the file path of the screenshot.
     *
     * @param name The name to be given to the screenshot file.
     * @return The file path of the screenshot.
     */

    public static String getScreenshot(String name) {
        // Adding date and time to the screenshot name to make it unique
        name = new Date().toString().replace(" ", "_").replace(":", "-") + "_" + name;
        String path;
        // Determining the file path based on the operating system
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            path = System.getProperty("user.dir") + "/test-output/screenshots/" + name + ".png";
        } else {
            path = System.getProperty("user.dir") + "\\test-output\\screenshots\\" + name + ".png";
        }
        TakesScreenshot screenshot = (TakesScreenshot) Driver.getDriver();
        File source = screenshot.getScreenshotAs(OutputType.FILE);
        File destination = new File(path);
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }


    /**
     * Verifies if the given web element is displayed on the page.
     * If the element is not displayed, the test will fail with the given message.
     *
     * @param element The web element to check for visibility.
     * @param message The message to be displayed in case the element is not visible.
     */
    public static void verifyElementDisplayed(WebElement element, String message) {
        try {
            assertTrue(message, element.isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.fail(message);
        }
    }


    /**
     * Performs a double click on the given web element.
     *
     * @param element The web element to be double-clicked.
     */
    public static void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }

    /**
     * Performs a drag and drop operation from the source element to the target element.
     *
     * @param source The source element to drag from.
     * @param target The target element to drop onto.
     */
    public static void dragAndDrop(WebElement source, WebElement target) {
        Actions actions = new Actions(Driver.getDriver());
        actions.dragAndDrop(source, target).build().perform();
    }

    /**
     * Performs a hover over action on the specified web element.
     *
     * @param element The web element to hover over.
     */
    public static void hoverOver(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).build().perform();
    }

    /**
     * Moves the mouse pointer to the specified web element.
     *
     * @param element The web element to move the mouse pointer to.
     */
    public static void moveToElement(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).build().perform();
    }


    /**
     * Pauses the current thread for a specified number of seconds.
     *
     * @param secs The number of seconds to pause the thread for.
     */
    public static void wait(int secs) {
        try {
            Thread.sleep(1000 * secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Pauses the current thread for a specified number of seconds.
     *
     * @param seconds The number of seconds to pause the thread for.
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    public static void waitSeconds(int seconds) throws InterruptedException {
        long millis = seconds * 1000L;
        long startTime = System.currentTimeMillis();
        long remainingTime = millis;

        while (remainingTime > 0) {
            try {
                Thread.sleep(remainingTime);
            } catch (InterruptedException e) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                remainingTime = millis - elapsedTime;
                // Re-interrupt the thread to propagate the interruption
                Thread.currentThread().interrupt();
                throw e;
            }
            remainingTime = millis - (System.currentTimeMillis() - startTime);
        }
    }

    /**
     * Navigates to a target window based on its title
     *
     * @param targetTitle the title of the target window
     */
    public static void navigateToWindow(String targetTitle) {
        String currentWindow = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(currentWindow);
    }


    /**
     * Waits for the specified element to become visible
     *
     * @param element         - The WebElement to wait for
     * @param timeToWaitInSec - The time to wait in seconds
     * @return The visible WebElement
     */
    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec) );
        return wait.until(ExpectedConditions.visibilityOf(element));
    }


    /**
     * Waits for an element to be visible
     *
     * @param locator - element locator
     * @param timeToWaitInSec - time to wait in seconds
     * @return WebElement - visible element
     */
    public static WebElement waitForVisibility(By locator, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    /**
     * Waits for element to be clickable
     *
     * @param element - WebElement to be clicked
     * @param timeToWaitInSec - timeout in seconds
     * @return WebElement
     */
    public static WebElement waitForClickability(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits for an element to be clickable
     *
     * @param locator
     * @param timeToWaitInSec
     * @return WebElement
     */
    public static WebElement waitForClickable(By locator, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Switches to a specified frame by its name or ID.
     *
     * @param frameNameOrId the name or ID of the frame to switch to
     */
    public static void switchToFrame(String frameNameOrId) {
        Driver.getDriver().switchTo().frame(frameNameOrId);
    }


    /**
     * Clicks on a dynamic element that starts with the specified text.
     *
     * @param searchText the text to search for at the start of the element
     */
    public static void clickDynamicElementStartsWith(String searchText) {
        String xpath = "//*[starts-with(text(),'" + searchText + "')]";
        WebElement element = Driver.getDriver().findElement(By.xpath(xpath));
        element.click();
    }

    /**
     * Clicks on a dynamic element that contains the specified attribute value.
     *
     * @param attributeName the name of the attribute to search for
     * @param attributeValue the value of the attribute to search for
     */
    public static void clickDynamicElementByAttribute(String attributeName, String attributeValue) {
        String xpath = "//*[@"+attributeName+"='"+attributeValue+"']";
        WebElement element = Driver.getDriver().findElement(By.xpath(xpath));
        element.click();
    }

    /**
     * Accepts an alert and returns its text.
     *
     * @return the text of the alert
     */
    public static String handleAlertAcceptByReturningMessage() {
        Alert alert = Driver.getDriver().switchTo().alert();
        String text = alert.getText();
        alert.accept();
        return text;
    }

    /**
     * Accepts an alert
     *
     * @return the text of the alert
     */
    public static void handleAlertAccept() {
        Alert alert = Driver.getDriver().switchTo().alert();
        alert.accept();
    }

    /**
     * Dismisses an alert and returns its text.
     *
     * @return the text of the alert
     */
    public static String handleAlertDismissByReturningMessage() {
        Alert alert = Driver.getDriver().switchTo().alert();
        String text = alert.getText();
        alert.dismiss();
        return text;
    }

    /**
     * Dismisses an alert and returns its text.
     *
     * @return the text of the alert
     */
    public  static void handleAlertDismiss() {
        Alert alert = Driver.getDriver().switchTo().alert();
        alert.dismiss();
    }


    /**
     * Switches to the browser tab with the specified title.
     *
     * @param title The title of the browser tab to switch to.
     */
    public static void switchToTab(String title) {
        WebDriver driver = Driver.getDriver();

        // Get a list of all open window handles
        for (String windowHandle : driver.getWindowHandles()) {
            // Switch to each window handle and check its title
            driver.switchTo().window(windowHandle);
            if (driver.getTitle().equals(title)) {
                // If the title matches, break out of the loop
                break;
            }
        }
    }


}


