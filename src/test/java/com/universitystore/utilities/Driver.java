package com.universitystore.utilities;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Driver {

    private static final ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    private Driver() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    /**
     * Returns a new instance of a WebDriver based on the browser specified in the configuration properties file.
     * If an instance of the WebDriver already exists, that instance is returned.
     *
     * @return a new instance of a WebDriver
     * @throws RuntimeException if an invalid browser is specified in the configuration properties file
     */
    public static WebDriver getDriver() {
        if (driverPool.get() == null) {
            String browserName = com.universitystore.utilities.ConfigurationReader.getProperty("browser").toLowerCase();
            WebDriver driver;

            switch (browserName) {
                case "chrome":
                    driver = createChromeDriver();
                    break;
                case "firefox":
                    driver = createFirefoxDriver();
                    break;
                case "edge":
                    driver = createEdgeDriver();
                    break;
                default:
                    throw new RuntimeException("Invalid browser name specified in configuration properties file: " + browserName);
            }

            driverPool.set(driver);
        }

        return driverPool.get();
    }

    private static ChromeDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        return new ChromeDriver(options);
    }

    private static EdgeDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized"); // Maximize browser window on startup
        return new EdgeDriver(options);
    }

    private static FirefoxDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }

    /**
     * Closes the WebDriver instance and removes it from the thread pool.
     */
    public static void closeDriver() {
        WebDriver driver = driverPool.get();
        if (driver != null) {
            driver.quit();
            driverPool.remove();
        }
    }
}
