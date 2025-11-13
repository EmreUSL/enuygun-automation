package com.enuygun.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CSVUtils {

    public static void writeFlightDataToCSV(List<Map<String, String>> flightData, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Airline Name,Departure Time,Arrival Time,Price,Flight Duration,Connection Information\n");
            for (Map<String, String> flight : flightData) {
                writer.write(String.join(",",
                        flight.get("Airline Name"),
                        flight.get("Departure Time"),
                        flight.get("Arrival Time"),
                        flight.get("Price"),
                        flight.get("Flight Duration"),
                        flight.get("Connection Information")));
                writer.write("\n");
            }
            System.out.println("✅ Flight data saved to CSV successfully: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
