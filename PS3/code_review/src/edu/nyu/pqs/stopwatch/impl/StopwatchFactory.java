package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import edu.nyu.pqs.stopwatch.api.Stopwatch;
import edu.nyu.pqs.stopwatch.api.StopwatchImplementation;

/**
 * The StopwatchFactory is a thread-safe factory class for Stopwatch objects. It maintains
 * references to all created Stopwatch objects and provides a convenient method for getting a list
 * of those objects.
 *
 */
public class StopwatchFactory {
  private static Map<String, Stopwatch> stopwatchMap = new ConcurrentHashMap<String, Stopwatch>();
  private static List<Stopwatch> stopwatchList = new ArrayList<Stopwatch>();
  private final static Object stopwatchFactoryLock = new Object();

  /**
   * Creates and returns a new Stopwatch object
   * 
   * @param id The identifier of the new object
   * @return The new Stopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty, null, or already taken.
   */
  public static Stopwatch getStopwatch(String id) {
    if (id == null || id.trim().equals("")) {
      throw new IllegalArgumentException("Invalid ID");
    }
    synchronized (stopwatchFactoryLock) {
      if (!stopwatchMap.isEmpty() && stopwatchMap.containsKey(id)) {
        throw new IllegalArgumentException("ID already exists");
      }
      StopwatchImplementation stopwatch = new StopwatchImplementation(id);
      stopwatchMap.put(id, stopwatch);
      stopwatchList.add(stopwatch);
      return stopwatch;
    }
  }

  /**
   * Returns a list of all created stopwatches
   * 
   * @return a List of al creates Stopwatch objects. Returns an empty list if no Stopwatches have
   *         been created.
   */
  public static List<Stopwatch> getStopwatches() {
    synchronized (stopwatchFactoryLock) {
      return stopwatchList;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "Stopwatch Factory";
  }

}
