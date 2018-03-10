package com.devotedworker.Generation.Utility;

import java.util.HashMap;

/**
 * This class allows for the "Logging" of the length of time between operations, and store them for later use
 */
public class PerformanceUtility {
    private static HashMap<String,Long> inProgressTimings = new HashMap<>();
    private static HashMap<String,Double> completeTimings = new HashMap<>();

    /**
     * Begins the Logging sequence
     * @param identifier
     */
    public static void startLogging(String identifier)
    {
        inProgressTimings.put(identifier,System.currentTimeMillis());
    }

    /**
     * Ends the log sequence, making the timings avalible for reference via getTimings(String Identifier)
     * @param identifier
     */
    public static void endLogging(String identifier)
    {
        completeTimings.put(identifier, (System.currentTimeMillis()-inProgressTimings.get(identifier))/1000.0);
        inProgressTimings.remove(identifier);
    }

    /**
     * Retrieve completed Timings
     */
    public static double getTimings(String identifier)
    {
        return completeTimings.get(identifier);
    }
}
