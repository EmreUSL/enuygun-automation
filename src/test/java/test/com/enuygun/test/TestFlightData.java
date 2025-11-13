package test.com.enuygun.test;

import com.enuygun.pages.FlightListing;
import com.enuygun.pages.FlightSearch;
import com.enuygun.utils.CSVUtils;
import com.enuygun.utils.ConfigReader;
import com.enuygun.utils.Log;
import org.testng.annotations.Test;
import test.com.enuygun.base.BaseTest;

import java.util.List;
import java.util.Map;

public class TestFlightData extends BaseTest {

    @Test
    public void testFlightData() {
        FlightSearch flightSearch = new FlightSearch(driver);
        flightSearch.setFlightFrom(ConfigReader.get("whereFrom"));
        flightSearch.setFlightTo(ConfigReader.get("whereTo"));
        flightSearch.setDepartureDate(ConfigReader.get("departureDate"));
        flightSearch.clickDismissButton();
        flightSearch.clickSearchButton();

        Log.info("Navigating Flight List Page");
        FlightListing flightListing = new FlightListing(driver);
        flightListing.waitFlightListLoading();

        Log.info("Data write into csv");
        CSVUtils.writeFlightDataToCSV(flightListing.exractFlightsData(),ConfigReader.get("filePath"));

        List<Map<String, String>> flights = flightListing.exractFlightsData();
        Log.info("Calculating average price");
        flightListing.calculateAveragePrice(flights);

    }


}
