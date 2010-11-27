package com.xdev.engine.util

/**
* Created by User: xdev
* Date: 25.08.2010
* Time: 16:01:21
*/

object SystemTimer {
  /**
   * Get the high resolution time in milliseconds
   *
   * @return The high resolution time in milliseconds
   */
  def getTime(): Long = {
    // we get the "timer ticks" from the high resolution timer
    // divide on 1 000 000 so our end result is in milliseconds
    return System.nanoTime() / 1000000
  }

  /**
   * Sleep for a fixed number of milliseconds.
   *
   * @param duration The amount of time in milliseconds to sleep for
   */
  def sleep(duration: Long):Unit = {
    Thread.sleep(duration)
  }
}
