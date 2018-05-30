package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * This class MyStopwatch implements the Stopwatch interface MyStopwatch is designed to be
 * thread-safe.
 * 
 * @author Lei Guo
 */
public class MyStopwatch implements Stopwatch {
  private final static double NANO_MILLI_CONVERTION = 1000000.0;
  private final String id;
  private final Object lock = new Object();
  private boolean isStarted = false;
  private long lapStartTimeNanos;
  // Stop() records the final lap, but if hitting start() right after hitting stop makes it resume
  // timing.
  private boolean lapTimeContinued = false;

  // CopyOnWriteArrayList can be very inefficient because it copies itself every time it's modified
  private List<Long> lapTimes = Collections.synchronizedList(new ArrayList<>());

  private MyStopwatch(String id) {
    this.id = id;
  }

  /**
   * Get a new instance of MyStopwatch.
   * 
   * @param id the id of the new instance
   * @return a new instance of MyStopwatch
   */
  static MyStopwatch getInstance(String id) {
    return new MyStopwatch(id);
  }

  /**
   * Returns the Id of this stopwatch
   * 
   * @return the Id of this stopwatch. Will never be empty or null.
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * Starts the stopwatch.
   * 
   * @throws IllegalStateException thrown when the stopwatch is already running
   */
  @Override
  public void start() {
    synchronized (lock) {
      if (true == isStarted) {
        throw new IllegalStateException("The stopwatch is already started");
      }

      lapStartTimeNanos = System.nanoTime();
      isStarted = true;
    }
  }

  /**
   * Stores the time elapsed since the last time lap() was called or since start() was called if
   * this is the first lap. Note that if hitting start() right after hitting stop(), the timer
   * should resume its last lap.
   * 
   * @throws IllegalStateException thrown when the stopwatch isn't running
   */
  @Override
  public void lap() {
    synchronized (lock) {
      if (!isStarted) {
        throw new IllegalStateException("The stopwatch is already started");
      }

      long lapEndTimeNanos = System.nanoTime();
      long curLapTimeNanos = lapEndTimeNanos - lapStartTimeNanos;
      long curLapTimeMillis = Math.round(curLapTimeNanos / NANO_MILLI_CONVERTION);

      if (lapTimeContinued) {
        int index = lapTimes.size() - 1;
        long preLapTime = lapTimes.get(index);
        lapTimes.set(lapTimes.size() - 1, preLapTime + curLapTimeMillis);
      } else {
        lapTimes.add(curLapTimeMillis);
      }

      lapStartTimeNanos = lapEndTimeNanos;
      lapTimeContinued = false;
    }
  }

  /**
   * Stops the stopwatch (and records as one final lap).
   * 
   * @throws IllegalStateException thrown when the stopwatch isn't running
   */
  @Override
  public void stop() {
    lap();
    synchronized (lock) {
      isStarted = false;
      lapTimeContinued = true;
    }
  }

  /**
   * Resets the stopwatch. If the stopwatch is running, this method stops the watch and resets it.
   * This also clears all recorded laps.
   */
  @Override
  public void reset() {
    synchronized (lock) {
      isStarted = false;
      lapTimeContinued = false;
      lapTimes = Collections.synchronizedList(new ArrayList<Long>());
    }

  }

  /**
   * Returns a list of lap times (in milliseconds). This method can be called at any time and will
   * not throw an exception.
   * 
   * @return a list of recorded lap times or an empty list.
   */
  @Override
  public List<Long> getLapTimes() {
    List<Long> lapTimesSnapshots;

    // instead using "lock", manually synchronizing on lapTimes when iterating over it
    synchronized (lapTimes) {
      lapTimesSnapshots = new ArrayList<>(lapTimes);
    }

    return lapTimesSnapshots;
  }

  /**
   * Returns the hashCode value for MyStopwatch.
   * 
   * @return hashCode value for the instance
   */
  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + id.hashCode();
    result = 31 * result + (isStarted ? 1 : 0);
    result = 31 * result + (lapTimeContinued ? 1 : 0);
    result = 31 * result + lapTimes.hashCode();
    return result;
  }

  /**
   * Decide whether this instance and the other object are equal. Two stopwatches are equal only
   * when they are the same instance or they have matching lap times and in the same state.
   * 
   * @return a boolean whether this instance and the other object are equal or not
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other == null) {
      return false;
    }

    if (getClass() != other.getClass()) {
      return false;
    }

    MyStopwatch otherWatch = (MyStopwatch) other;

    if (this.isStarted != otherWatch.isStarted) {
      return false;
    }

    if (this.lapTimeContinued != otherWatch.lapTimeContinued) {
      return false;
    }

    if (!this.lapTimes.equals(otherWatch.getLapTimes())) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append(isStarted ? "Still timing" : "Timing stopped");
    sb.append("Laps : [");

    // getLapTimes() returns a shallow copy and already handles thread-safety
    List<Long> lapTimesCopy = getLapTimes();

    Iterator<Long> itr = lapTimesCopy.iterator();
    if (itr.hasNext()) {
      long lap = itr.next();
      sb.append(String.valueOf(lap));
    }

    while (itr.hasNext()) {
      sb.append(", ");
      long lap = itr.next();
      sb.append(String.valueOf(lap));
    }

    sb.append("]");

    return sb.toString();
  }
}
