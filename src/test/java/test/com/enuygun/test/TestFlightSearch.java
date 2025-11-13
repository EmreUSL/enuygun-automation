package test.com.enuygun.test;

import com.enuygun.utils.ConfigReader;
import com.enuygun.utils.Log;
import test.com.enuygun.base.BaseTest;
import com.enuygun.pages.FlightListing;
import com.enuygun.pages.FlightSearch;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestFlightSearch extends BaseTest {

     @Test
     public void testFlightSearchAndTimeFilter() throws InterruptedException {
         FlightSearch flightSearch = new FlightSearch(driver);
         flightSearch.setFlightFrom(ConfigReader.get("whereFrom"));
         flightSearch.setFlightTo(ConfigReader.get("whereTo"));
         flightSearch.setDepartureDate(ConfigReader.get("departureDate"));
         flightSearch.setReturnDate(ConfigReader.get("returnDate"));
         flightSearch.clickDismissButton();
         flightSearch.clickSearchButton();

         Log.info("Navigating Flight List Page");
         FlightListing flightListing = new FlightListing(driver);

         flightListing.waitFlightListLoading();
         flightListing.filterDepartureTime();

         Assert.assertTrue(flightListing.areFlightListDisplayed());
         Log.info("All flight displayed in screen successfully");
         Assert.assertTrue(flightListing.areDepartureTimesInRange());
         Log.info("Departure time are in range");
         Assert.assertTrue(flightListing.areSelectedRouteTrue());
         Log.info("Selected route is true");
     }

}
