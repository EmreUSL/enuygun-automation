package test.com.enuygun.base;

import com.enuygun.utils.DriverFactory;
import com.enuygun.utils.Log;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        Log.info("Starting WebDriver");
        driver = DriverFactory.getDriver();
        Log.info("Navigating to URL");
    }

    @AfterMethod
    public void captureScreenshot(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "reports/screenshots/" + result.getName() + "_" + timestamp + ".png";

            try {
                FileHandler.copy(screenshotFile, new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @AfterClass
    public void tearDown() {
        Log.info("Closing Browser");
        DriverFactory.closeDriver();
    }
}
