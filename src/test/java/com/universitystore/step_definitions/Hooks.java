package com.universitystore.step_definitions;

import com.universitystore.utilities.ConfigurationReader;
import com.universitystore.utilities.DBUtils;
import com.universitystore.utilities.Driver;
import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.SQLException;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setup(Scenario scenario) {
        logger.info(":::: Starting test automation ::::");
        logger.info("Browser type :: {}", ConfigurationReader.getProperty("browser"));
        logger.info("Environment :: {}", ConfigurationReader.getProperty("url"));
        logger.info("Test scenario :: {}", scenario.getName());
        Driver.getDriver().manage().window().maximize();
    }


    @Before("@db")
    public void setupDbConnection(Scenario scenario) {
        logger.info("Setting up the DB Connection :: {}", scenario.getName());
            DBUtils.createConnection();
    }

    @After("@db")
    public void tearDownDbConnection(Scenario scenario) {
        logger.info("Closing DB connection...");
        try {
            DBUtils.destroy();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot takesScreenshot = (TakesScreenshot) Driver.getDriver();
            byte[] image = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            scenario.attach(image, "image/png", scenario.getName());
            logger.error("Test scenario :: {} :: Failed", scenario.getName());
        } else {
            logger.info("Test scenario :: {} :: Passed", scenario.getName());
        }
        Driver.closeDriver();
    }
}
