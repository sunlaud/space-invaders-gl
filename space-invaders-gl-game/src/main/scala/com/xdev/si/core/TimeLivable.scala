package com.xdev.si.core

import com.xdev.engine.logging.LogHelper

/**
 * User: xdev.developer@gmail.com
 * Date: 28.11.12
 * Time: 0:31
 */
trait TimeLivable extends LogHelper {

  val liveTime: Long
  private var currentTime: Long = 0L
  private var stopped = true
  def onTimeEnded()

  def startTimer(){
    this.stopped = false
  }

  def stopTimer(){
    stopped = true
  }

  def update(delta: Long){
    debug("Update timer: " + this.stopped)
    if (this.stopped) {return}
    currentTime += delta
    if (currentTime >= liveTime) {
      onTimeEnded()
      stopTimer()
    }
  }

}
