package edu.nyu.pqs.stopwatch.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A thread-safe object used to time laps which implements Stopwatch. Different threads can share a
 * single stopwatch object and safely call any of the stopwatch methods such as start,stop,reset and
 * recording of laps.
 * 
 * @author priyanka
 *
 */
public class StopwatchImplementation implements Stopwatch {

  private final String stopwatchId;
  private long startTime;
  private long recentLapTime;
  private boolean isRunning;
  private List<Long> lapTimes;
  private final Object stopwatchLock = new Object();
  private long temporaryLap;

  /**
   * Initializes the list of lapTimes and sets the ID for the stopwatch object.
   */
  public StopwatchImplementation(String id) {
    this.lapTimes = new ArrayList<Long>();
    this.lapTimes = Collections.synchronizedList(this.lapTimes);
    this.stopwatchId = id;
    this.temporaryLap = 0;
  }

  /**
   * Setter for isRunning
   * 
   * @param value
   */
  void setIsRunning(boolean value) {
    this.isRunning = value;
  }

  /**
   * Getter for isRunning
   * 
   * @return the value of isRunning
   */
  boolean getIsRunning() {
    return this.isRunning;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {
    return this.stopwatchId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void start() {
    synchronized (stopwatchLock) {
      if (this.getIsRunning() == true) {
        throw new IllegalStateException("Cannot start when stopwatch is already running.");
      }
      this.setIsRunning(true);
      if (this.temporaryLap != 0) {
        this.lapTimes.remove(this.lapTimes.size() - 1);
      }
      this.startTime = System.currentTimeMillis();
      this.recentLapTime = System.currentTimeMillis();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void lap() {
    synchronized (stopwatchLock) {
      if (this.getIsRunning() == false) {
        throw new IllegalStateException("Cannot lap when stopwatch is not running.");
      }
      if (this.getLapTimes().size() == 0) {
        lapTimes.add(System.currentTimeMillis() - this.startTime + this.temporaryLap);

      } else {
        lapTimes.add(System.currentTimeMillis() - this.recentLapTime + this.temporaryLap);
      }
      this.recentLapTime = System.currentTimeMillis();
      this.temporaryLap = 0;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void stop() {
    synchronized (stopwatchLock) {
      if (this.getIsRunning() == false) {
        throw new IllegalStateException("Cannot stop when stopwatch is not running.");
      }
      if (this.getLapTimes().size() == 0) {
        this.temporaryLap = System.currentTimeMillis() - this.startTime;
      } else {
        this.temporaryLap = System.currentTimeMillis() - this.recentLapTime;
      }
      lapTimes.add(this.temporaryLap);
      this.setIsRunning(false);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reset() {
    synchronized (stopwatchLock) {
      this.setIsRunning(false);
      this.lapTimes.clear();
      this.temporaryLap = 0;
      this.startTime = 0;
      this.recentLapTime = 0;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Long> getLapTimes() {
    synchronized (stopwatchLock) {
      return this.lapTimes;
    }
  }

  /**
   *
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "Stopwatch:" + this.getId();
  }

}
