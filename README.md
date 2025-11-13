# EnUygun-Automation

This project is an automation framework for testing flight search functionalities on enuygun.com. It covers various scenarios including round-trip flight searches, time filters, airline-specific price sorting, and currency validation (TRY/EUR). The framework is designed to be modular and maintainable, using the Page Object Model (POM) structure for all pages and components.



<img width="1146" height="463" alt="Screenshot 2025-11-13 at 22 04 51" src="https://github.com/user-attachments/assets/af922043-b234-481c-9168-99062b83bca9" />

![Flight Automation](https://user-images.githubusercontent.com/placeholder/flight-banner.png)
*Comprehensive solution for flight search and filtering automation.*

---

## 🔍 Overview

The automation covers:

- 🛫 Searching for round-trip flights between configurable cities.
- 📅 Setting departure and return dates dynamically.
- 🎛 Applying filters on the flight listing page, such as departure time or airline.
- ✅ Verifying the displayed flights match the selected route and applied filters.
- 💱 Currency Validation: Confirms that flight prices update correctly when changing between TRY and EUR. (Case 3)

---

## ⚡ Features

1. **Flight Search & Time Filter**  
   - Performs round-trip flight searches with parameterized departure/return dates and cities.  
   - Applies time filters on departure flights and verifies results are within the expected range.  

2. **Airline-Specific Price Sorting**  
   - Filters flights for a specific airline (e.g., Turkish Airlines).  
   - Validates ascending price sorting and correct airline display.  

3. **Currency Validation 💶**  
   - Checks if displayed prices correctly update when switching between TRY and EUR.  

4. **Reporting & Screenshots 📸**  
   - Generates detailed execution reports using ExtentReports.  
   - Captures screenshots on failure or at key test steps for easier debugging.  

5. **Logging 📝**  
   - Uses Log4j2 to track execution steps and debug issues.  

---

## 🛠 Technologies & Tools

- **Java 21** – Main programming language used for automation scripts.  
- **Selenium 4.38.0** – For browser automation and UI interactions.  
- **TestNG 7.11.0** – Test framework for structuring and running test cases.  
- **WebDriverManager 6.3.3** – Automatic driver management for different browsers.  
- **ExtentReports 5.1.0** – For generating detailed test reports with screenshots.  
- **Log4j 2.25.2** – Logging framework to record execution details.  
- **Rest-Assured 5.5.6** – For API testing (used in specific test cases).  

---

## 🏗 Framework Structure

- **Page Object Model (POM)** – Each page has a corresponding Java class with reusable methods.  
- **Config Management** – Centralized `config.properties` file for browser, URLs, and date configurations.  
- **Driver Factory** – Handles WebDriver initialization and teardown for parallel execution.  
- **Listeners & Reporting** – TestNG listeners capture screenshots and integrate with ExtentReports.  
- **Utility Classes** – Helper methods for date selection, element interactions, CSV handling, and logging.  

---
