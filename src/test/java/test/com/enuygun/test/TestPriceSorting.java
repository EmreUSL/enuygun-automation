package test.com.enuygun.test;

import com.enuygun.pages.FlightListing;
import com.enuygun.pages.FlightSearch;
import com.enuygun.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.com.enuygun.base.BaseTest;

public class TestPriceSorting extends BaseTest {

    @Test
    public void performFlightSearch() throws InterruptedException {
        FlightSearch flightSearch = new FlightSearch(driver);
        flightSearch.setFlightFrom(ConfigReader.get("whereFrom"));
        flightSearch.setFlightTo(ConfigReader.get("whereTo"));
        flightSearch.setDepartureDate(ConfigReader.get("departureDate"));
        flightSearch.setReturnDate(ConfigReader.get("returnDate"));
        flightSearch.clickSearchButton();
    }

    @Test
    public void testPriceSorting() throws InterruptedException {
        FlightListing flightListing = new FlightListing(driver);
        flightListing.waitFlightListLoading();
        flightListing.filterDepartureTime();
        flightListing.filterAirlineFlight();

        Assert.assertTrue(flightListing.arePricesSortedAscending());
        Assert.assertTrue(flightListing.areDisplayedFlightsTHY());
        Assert.assertTrue(flightListing.isPriceSortingAccurate());

    }
}
