package com.enuygun.pages;

import com.enuygun.base.BasePage;
import com.enuygun.utils.ConfigReader;
import com.enuygun.utils.DataAnalyzer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private final By whereFrom = By.xpath("//div[contains(text(),'İstanbul')]");
    private final By whereTo = By.xpath("//div[contains(text(),'Ankara')]");

    private final By flightCards = By.cssSelector("div.flight-item__wrapper");
    private final By marketingAirlines = By.cssSelector(".summary-marketing-airlines");
    private final By flightsDepartureTimes = By.cssSelector(".flight-departure-time");
    private final By flightsArriveTimes = By.cssSelector(".flight-arrival-time");
    private final By priceElements = By.cssSelector(".summary-average-price");
    private final By flightDuration = By.cssSelector(".summary-duration span");

    private final By currencyPrice = By.xpath("(//span[@class='money-int'])[1]");

    private final By regionalSettingButton = By.xpath("//button[@data-testid='undefined-summary']");
    private final By currencySetting = By.xpath("//input[@data-testid='currency-select-input']");
    private final By selectOption = By.xpath("//div[@data-testid='currency-select-option-EUR']");
    private final By saveButton = By.xpath("//button[@data-testid='display-settings-save']");

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

    public List<Map<String, String>> exractFlightsData() {
        List<Map<String, String>> data = new ArrayList<>();
        List<WebElement> cards = findElements(flightCards);

        for (WebElement card : cards) {
            Map<String, String> flight = new HashMap<>();

            flight.put("Airline Name", card.findElement(marketingAirlines).getText());
            flight.put("Departure Time", card.findElement(flightsDepartureTimes).getText());
            flight.put("Arrival Time", card.findElement(flightsArriveTimes).getText());
            flight.put("Price", card.findElement(priceElements).getText());
            flight.put("Flight Duration", card.findElement(flightDuration).getText());

            data.add(flight);

        }
        return data;
    }

    public void calculateAveragePrice(List<Map<String, String>> flight) {
        double avgPrice = DataAnalyzer.calculateAveragePrices(flight);
        System.out.println("Average Price: " + Math.round(avgPrice) + " TL");
    }

    public String currency() {
        return find(currencyPrice).getText();

    }

    public void changePriceCurrency() {
        click(regionalSettingButton);
        click(currencySetting);
        set(currencySetting, "EUR");
        click(selectOption);
        click(saveButton);
        waitFlightListLoading();
    }

    public boolean verifyCurrencyConversionIsAccurate(String tryPrice, String euroPrice) {

        Double tryPriceDouble = Double.parseDouble(tryPrice.replace(".", ""));
        double euroPriceDouble = Double.parseDouble(euroPrice);
        Double exchangeRate = 49.210854;

        double tryToEuroPrice = tryPriceDouble / exchangeRate;
        return euroPriceDouble == Math.round(tryToEuroPrice);
    }




}
