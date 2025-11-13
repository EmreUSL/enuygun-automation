package com.enuygun.utils;

import java.util.*;


public class DataAnalyzer {

    public static double calculateAveragePrices(List<Map<String, String>> flights) {
        List<Integer> allPrices = new ArrayList<>();

        for (Map<String, String> flight : flights) {
            String priceText = flight.get("Price");
            if (priceText == null || priceText.isEmpty()) continue;

            priceText = priceText.replace(" TL", "").replace(".", "");
            int price = Integer.parseInt(priceText);

            allPrices.add(price);
        }

        return allPrices.stream().mapToInt(Integer::intValue).average().orElse(0);
    }


    public static Map<String, Integer> findMinMaxPriceByAirline(List<Map<String, String>> flights, String type) {
        Map<String, Integer> results = new HashMap<>();
        Map<String, List<Integer>> airlinePrices = new HashMap<>();

        for (Map<String, String> flight : flights) {
            String airline = flight.get("Airline");
            String priceText = flight.get("Price");

            if (priceText == null || priceText.isEmpty()) continue;

            priceText = priceText.replace(" TL", "").replace(".", "");
            int price = Integer.parseInt(priceText);

            airlinePrices.computeIfAbsent(airline, k -> new ArrayList<>()).add(price);
        }

        for (Map.Entry<String, List<Integer>> entry : airlinePrices.entrySet()) {
            List<Integer> prices = entry.getValue();
            if (!prices.isEmpty()) {
                if (type.equalsIgnoreCase("min")) {
                    results.put(entry.getKey(), Collections.min(prices));
                } else if (type.equalsIgnoreCase("max")) {
                    results.put(entry.getKey(), Collections.max(prices));
                }
            } else {
                results.put(entry.getKey(), 0);
            }
        }

        return results;
    }
}