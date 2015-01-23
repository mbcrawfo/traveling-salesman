package util;

/**
 * A timer for measuring elapsed wall time.
 * @author Michael Crawford
 */
public class Timer {
  private long startTime = 0L;
  private long endTime = 0L;
  
  /**
   * Starts the timer. If the timer has been previously started, call {@link 
   * Timer#reset() reset()} prior to calling start a second time.
   */
  public void start() {
    startTime = System.nanoTime();
  }
  
  /**
   * Stops the timer. The ending time is stored for later retrieval.
   * @return The elapsed time in nanoseconds.
   */
  public long stop() {
    endTime = System.nanoTime();
    return elapsedNanoSec();
  }
  
  /**
   * Returns the time elapsed since the timer was started. If the timer was
   * previously stopped, then the time elapsed from {@link Timer#start() 
   * start()} to {@link Timer#stop() stop()} is returned.
   * @return The elapsed time in nanoseconds.
   */
  public long elapsedNanoSec() {
    return (endTime == 0L ? System.nanoTime() : endTime) - startTime;
  }
  
  /**
   * Returns the time elapsed since the timer was started. If the timer was
   * previously stopped, then the time elapsed from {@link Timer#start() 
   * start()} to {@link Timer#stop() stop()} is returned.
   * @return The elapsed time in milliseconds.
   */
  public long elapsedMilliSec() {
    return elapsedNanoSec() / 1000000;
  }
  
  /**
   * Returns the time elapsed since the timer was started. If the timer was
   * previously stopped, then the time elapsed from {@link Timer#start() 
   * start()} to {@link Timer#stop() stop()} is returned.
   * @return The elapsed time in seconds.
   */
  public double elapsedSec() {
    return ((double)elapsedNanoSec()) / 1000000000.0;
  }
  
  /**
   * Resets the timer.
   */
  public void reset() {
    startTime = 0L;
    endTime = 0L;
  }
}
