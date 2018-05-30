package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The StopwatchFactory is a thread-safe factory class for Stopwatch objects. It maintains
 * references to all created Stopwatch objects and provides a convenient method for getting a list
 * of those objects.
 *
 */
public class StopwatchFactory {
  private static Map<String, MyStopwatch> createdWatches = new ConcurrentHashMap<>();

  /**
   * Creates and returns a new Stopwatch object
   * 
   * @param id The identifier of the new object
   * @return The new Stopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty, null, or already taken.
   */
  public static MyStopwatch getStopwatch(String id) {
    if (id == null || id.equals("")) {
      throw new IllegalArgumentException("id cannot be null or empty");
    }

    MyStopwatch stopwatch = createdWatches.get(id);
    if (stopwatch != null) {
      throw new IllegalArgumentException("id was already taken");
    }

    stopwatch = MyStopwatch.getInstance(id);
    createdWatches.put(id, stopwatch);
    return stopwatch;
  }

  /**
   * Returns a list of all created stopwatches
   * 
   * @return a List of al creates Stopwatch objects. Returns an empty list if no Stopwatches have
   *         been created.
   */
  public static List<MyStopwatch> getStopwatches() {
    /*
     * "ConcurrentHashMap.iterator() may or may not reflect insertions or removals that occurred
     * since the iterator was constructed. However, iterators are designed to be used by only one
     * thread at a time."
     */
    List<MyStopwatch> result;
    synchronized (createdWatches) {
      result = new ArrayList<>(createdWatches.values());
    }
    return result;
  }
}
