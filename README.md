# EnUygun-Automation

This project automates flight search and filtering on **Enuygun.com** using **Selenium** and, following a clean and maintainable **Page Object Model (POM)** structure.
<img width="1146" height="463" alt="Screenshot 2025-11-13 at 22 04 51" src="https://github.com/user-attachments/assets/af922043-b234-481c-9168-99062b83bca9" />

## Overview

The automation covers:

- Searching for round-trip flights between configurable cities.
- Setting departure and return dates dynamically.
- Applying filters on the flight listing page, such as departure time or airline.
- Verifying the displayed flights match the selected route and applied filters.
- Currency Validation: Confirms that flight prices update correctly when changing between TRY and EUR. ( Case 3 )

---

## Key Features

- **Page Object Model (POM):** Separates test logic from page interactions for maintainability.
- **Thread-safe WebDriver:** Each test runs with its own WebDriver instance using `ThreadLocal`.
- **Logging:** Uses **Log4j2** to record test execution steps and debug information.
- **Screenshots:** Captures screenshots automatically on test failures for easier debugging.
- **CSV Data Extraction:** Flight details like airline, departure/arrival times, price, and duration are extracted and can be exported to CSV for reporting.

---
