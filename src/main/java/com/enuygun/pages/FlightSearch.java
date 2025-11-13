package com.enuygun.pages;

import com.enuygun.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class FlightSearch extends BasePage {

    public FlightSearch(WebDriver driver) {
        super(driver);
    }

    private final By dismissButton = By.xpath("//div[contains(text(),'Bu tarihler için otelleri de listele')]");
    private final By flightFrom = By.xpath("//div[contains(text(), 'Nereden')]");
    private final By inputFlightFrom = By.xpath("//input[@name=\"origin\"]");
    private final By flightTo = By.xpath("//div[contains(text(), 'Nereye')]");
    private final By inputFlightTo = By.xpath("//input[@name=\"destination\"]");
    private final By departureDateInput = By.cssSelector("input[data-testid='enuygun-homepage-flight-departureDate-datepicker-input']");
    private final By departureDateForwardButton = By.xpath("//button[@data-testid=\"enuygun-homepage-flight-departureDate-month-forward-button\"]");
    private final By returnDateForwardButton = By.xpath("//button[@data-testid=\"enuygun-homepage-flight-returnDate-month-forward-button\"]");
    private final By returnDateInput = By.xpath("//div[contains(text(), 'Dönüş Ekle')]");
    private final By searchButton = By.xpath("//button[@data-testid=\"enuygun-homepage-flight-submitButton\"]");

    public void setFlightFrom(String whereFrom) {
        click(flightFrom);
        set(inputFlightFrom, whereFrom);
        clickEnter(inputFlightFrom);
    }

    public void setFlightTo(String whereTo) {
        click(flightTo);
        set(inputFlightTo, whereTo);
        waitforSecond();
        clickEnter(inputFlightTo);
    }

    public void setDepartureDate(String date) {
        click(departureDateInput);

        String[] parts = date.split(" ");
        String day = parts[0];
        String monthAndYear = parts[1] + " " + parts[2];

        String first = driver.findElement(By.xpath("(//h3[@data-testid='enuygun-homepage-flight-departureDate-month-name-and-year'])[1]")).getText();
        String second = driver.findElement(By.xpath("(//h3[@data-testid='enuygun-homepage-flight-departureDate-month-name-and-year'])[2]")).getText();

        while(!monthAndYear.equals(first) && !monthAndYear.equals(second)){
            click(departureDateForwardButton);

            first = driver.findElement(By.xpath("(//h3[@data-testid='enuygun-homepage-flight-departureDate-month-name-and-year'])[1]")).getText();
            second = driver.findElement(By.xpath("(//h3[@data-testid='enuygun-homepage-flight-departureDate-month-name-and-year'])[2]")).getText();
        }

        if(monthAndYear.equals(first)) {
            WebElement dayButton = driver.findElement(By.xpath("(//button[@data-day='" + day + "'])[1]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style','display:block');" +
                            "arguments[0].click();",
                    dayButton
            );
        } else {
            WebElement dayButton = driver.findElement(By.xpath("(//button[@data-day='" + day + "'])[2]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style','display:block');" +
                            "arguments[0].click();",
                    dayButton
            );
        }

    }

    public void setReturnDate(String date) throws InterruptedException {
        click(returnDateInput);

        String[] parts = date.split(" ");
        String dayReturn = parts[0];
        String monthAndYearReturn = parts[1] + " " + parts[2];

        String third = driver.findElement(By.xpath("(//h3[@data-testid='enuygun-homepage-flight-returnDate-month-name-and-year'])[1]")).getText();
        String fourth = driver.findElement(By.xpath("(//h3[@data-testid='enuygun-homepage-flight-returnDate-month-name-and-year'])[2]")).getText();

        while(!monthAndYearReturn.equals(third) && !monthAndYearReturn.equals(fourth)){
            click(returnDateForwardButton);

            third = driver.findElement(By.xpath("(//h3[@data-testid='enuygun-homepage-flight-returnDate-month-name-and-year'])[1]")).getText();
            fourth = driver.findElement(By.xpath("(//h3[@data-testid='enuygun-homepage-flight-returnDate-month-name-and-year'])[2]")).getText();
        }

        if(monthAndYearReturn.equals(third)) {
            WebElement dayButton = driver.findElement(By.xpath("(//button[@data-day='" + dayReturn + "'])[1]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style','display:block');" +
                            "arguments[0].click();",
                    dayButton);
        } else {
            WebElement dayButton = driver.findElement(By.xpath("(//button[@data-day='" + dayReturn + "'])[2]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style','display:block');" +
                            "arguments[0].click();",
                    dayButton);
        }
    }

    public void clickSearchButton() {
        click(searchButton);
    }

    public void clickDismissButton() {
        waitforSecond();
        click(dismissButton);
    }




}
