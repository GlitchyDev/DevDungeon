package com.devotedworker.Generation.Utility;

import java.util.HashMap;

public class PerformanceUtility {
    private static HashMap<String,Long> inProgressTimings = new HashMap<>();
    private static HashMap<String,Double> completeTimings = new HashMap<>();

    public static void startLogging(String identifier)
    {
        inProgressTimings.put(identifier,System.currentTimeMillis());
    }

    public static void endLogging(String identifier)
    {
        completeTimings.put(identifier, (System.currentTimeMillis()-inProgressTimings.get(identifier))/1000.0);
        inProgressTimings.remove(identifier);
    }

    public static double getTimings(String identifier)
    {
        return completeTimings.get(identifier);
    }
}
