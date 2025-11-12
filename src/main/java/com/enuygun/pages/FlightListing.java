package com.enuygun.pages;

import com.enuygun.base.BasePage;
import com.enuygun.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlightListing extends BasePage {

    public FlightListing(WebDriver driver) {
        super(driver);
    }

    private final By flightListContainer = By.xpath("(//span[@class='filter-title'])[2]");

    private final By departureTimeFilterDropdown = By.xpath("//div[@class='ctx-filter-departure-return-time card-header']");
    private final By leftSlider = By.xpath("(//div[@class='rc-slider-handle rc-slider-handle-1'])[1]");
    private final By rightSlider = By.xpath("(//div[@class='rc-slider-handle rc-slider-handle-2'])[1]");
    private final By slider = By.xpath("//div[@class='rc-slider']");
    private final By airlineFlightFilterDropdown = By.xpath("//div[@class='ctx-filter-airline card-header']");
    private final By selectTHYButton = By.xpath("//span[contains(text(),'Türk Hava Yolları')]");
    private final By flightList = By.xpath("//div[@class='search-result search-result-departure-only']");
    private final By priceElements = By.xpath("//span[@class='money-int']");
    private final By flightsDepartureTimes = By.xpath("//div[@class='flight-departure-time']");
    private final By whereFrom = By.xpath("//div[contains(text(),'İstanbul')]");
    private final By whereTo = By.xpath("//div[contains(text(),'Ankara')]");
    private final By marketingAirlines = By.xpath("//div[@class='summary-marketing-airlines ']");



    public void filterDepartureTime() throws InterruptedException {
        click(departureTimeFilterDropdown);
        WebElement leftHandle = find(leftSlider);
        WebElement rightHandle = find(rightSlider);
        WebElement sliderHandle = find(slider);

        int sliderWidth = sliderHandle.getSize().getWidth();

        int totalMinutes = 1440;
        int startMinutes = 600;
        int endMinutes = 1080;

        double leftPercent = (double) startMinutes / totalMinutes;
        double rightPercent = (double) endMinutes / totalMinutes;

        int leftOffset = (int) (sliderWidth * leftPercent);
        int rightOffset = (int) (sliderWidth * (1 - rightPercent));

        Actions actions = new Actions(driver);

        actions.clickAndHold(leftHandle).moveByOffset(leftOffset, 0).release().perform();
        Thread.sleep(1000);
        actions.clickAndHold(rightHandle).moveByOffset(-rightOffset - 5, 0).release().perform();

        waitforSecond();
    }

    public void filterAirlineFlight() {
        click(airlineFlightFilterDropdown);
        waitForClickable(selectTHYButton);
        click(selectTHYButton);
        waitforSecond();
    }

    public void waitFlightListLoading() {
        waitForVisible(flightListContainer);
    }

    public boolean areDepartureTimesInRange() {
        List<WebElement> flights = findElements(flightsDepartureTimes);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // 24 saat format
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        for (WebElement flight : flights) {
            String timeText = flight.getText();
            LocalTime flightTime = LocalTime.parse(timeText, formatter);
            if (flightTime.isBefore(startTime) || flightTime.isAfter(endTime)) {
                return false;
            }
        }
        return true;
    }

    public boolean areFlightListDisplayed() {
        WebElement flight = find(flightList);
        return flight.isDisplayed();
    }

    public boolean areSelectedRouteTrue() {
        WebElement where = find(whereFrom);
        WebElement to = find(whereTo);

        String fromCity = where.getText().split(",")[1].split("\\|")[0].trim();
        String toCity = to.getText().split(",")[1].split("\\|")[0].trim();

        String expectedFrom = ConfigReader.get("whereFrom");
        String expectedTo = ConfigReader.get("whereTo");

        return fromCity.equals(expectedFrom) && toCity.equals(expectedTo);
    }

    public boolean arePricesSortedAscending() {
        List<WebElement> prices = findElements(priceElements);
        List<Double> priceList = new ArrayList<>();
        for (WebElement price : prices) {
            String priceText = price.getText().replaceAll("[^\\d,]", "").replace(",", ".");
            priceList.add(Double.parseDouble(priceText));
        }

        for(int i = 0; i < priceList.size()-1; i++){
            if(priceList.get(i) > priceList.get(i+1)){
                return false;
            }
        }

        return true;
    }

    public boolean areDisplayedFlightsTHY() {
        List<WebElement> flights = findElements(marketingAirlines);

        for (WebElement flight : flights) {
            String marketingText = flight.getText();
            if(!marketingText.equals("Türk Hava Yolları")) {
                return false;
            }
        }
        return true;

    }

    public boolean isPriceSortingAccurate() {
        List<WebElement> prices = findElements(priceElements);
        List<Double> originalPrices = new ArrayList<>();
        for (WebElement price : prices) {
            String text = price.getText().replaceAll("[^\\d,]", "").replace(",", ".");
            originalPrices.add(Double.parseDouble(text));
        }

        List<Double> sortedPrices = new ArrayList<>(originalPrices);
        Collections.sort(sortedPrices);

        return originalPrices.equals(sortedPrices);
    }

}
