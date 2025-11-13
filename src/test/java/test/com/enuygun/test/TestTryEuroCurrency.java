package test.com.enuygun.test;

/*
  Case 3 : Critical Path Testing

  In this test scenario, I aimed to test whether currencies behave consistently when converted to each other.

   Step 1: Search for a round-trip flight between Istanbul and Ankara.
   Step 2: Select the desired departure time.
   Step 3: Click the Search button.
   Step 4: Verify that the Flight List page is displayed.
   Step 5: Store the currency of the first flight’s price in a variable.
   Step 6: Click the Regional Settings button.
   Step 7: Click the Price Currency dropdown menu.
   Step 8: Select EUR – Euro from the list.
   Step 9: Click the Apply button.
   Step 10: Verify that the prices are successfully converted from TRY to EUR.

   ** I conducted research and found that the EUR–TRY exchange rate was 49.210854, as retrieved from the EnUygun website’s Network API.

 */

import com.enuygun.pages.FlightListing;
import com.enuygun.pages.FlightSearch;
import com.enuygun.utils.ConfigReader;
import com.enuygun.utils.Log;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.com.enuygun.base.BaseTest;

public class TestTryEuroCurrency extends BaseTest {

    @Test
    public void TestTryEuroPriceCurrency() {
        FlightSearch flightSearch = new FlightSearch(driver);
        flightSearch.setFlightFrom(ConfigReader.get("whereFrom"));
        flightSearch.setFlightTo(ConfigReader.get("whereTo"));
        flightSearch.setDepartureDate(ConfigReader.get("departureDate"));
        flightSearch.clickDismissButton();
        flightSearch.clickSearchButton();

        FlightListing flightListing = new FlightListing(driver);
        flightListing.waitFlightListLoading();

        String tryCurrency = flightListing.currency();
        System.out.println("tryCurrency: " + tryCurrency);
        flightListing.changePriceCurrency();
        String euroCurrency = flightListing.currency();
        System.out.println("euroCurrency: " + euroCurrency);

        flightListing.verifyCurrencyConversionIsAccurate(tryCurrency, euroCurrency);
        Assert.assertTrue(flightListing.verifyCurrencyConversionIsAccurate(tryCurrency, euroCurrency));
        Log.info("Currency conversion accurate");


    }
}
