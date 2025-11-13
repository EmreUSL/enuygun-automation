package test.com.enuygun.test;

import com.enuygun.pages.FlightListing;
import com.enuygun.pages.FlightSearch;
import com.enuygun.utils.ConfigReader;
import com.enuygun.utils.Log;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.com.enuygun.base.BaseTest;

public class TestPriceSorting extends BaseTest {

    @Test(priority = 1)
    public void performFlightSearch() throws InterruptedException {
        FlightSearch flightSearch = new FlightSearch(driver);
        flightSearch.setFlightFrom(ConfigReader.get("whereFrom"));
        flightSearch.setFlightTo(ConfigReader.get("whereTo"));
        flightSearch.setDepartureDate(ConfigReader.get("departureDate"));
        flightSearch.setReturnDate(ConfigReader.get("returnDate"));
        flightSearch.clickDismissButton();
        flightSearch.clickSearchButton();
        Log.info("Navigating Flight List Page");
    }

    @Test(priority = 2, dependsOnMethods = "performFlightSearch")
    public void testPriceSorting() throws InterruptedException {
        FlightListing flightListing = new FlightListing(driver);
        flightListing.waitFlightListLoading();
        flightListing.filterDepartureTime();
        flightListing.filterAirlineFlight();

        Assert.assertTrue(flightListing.arePricesSortedAscending());
        Log.info("Prices sorted as ascending");
        Assert.assertTrue(flightListing.areDisplayedFlightsTHY());
        Log.info("Displayed flights thy");
        Assert.assertTrue(flightListing.isPriceSortingAccurate());
        Log.info("Price sorting accurate");

    }
}
