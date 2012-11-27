package com.xdev.si.core

/**
 * Created by IntelliJ IDEA.
 * User: xdev
 * Date: 27.11.12
 * Time: 22:38
 */
trait HasWaitInterval {
  private var interval: Long = _
  def waitInterval(): Long = interval
  def waitInterval(i: Long) { this.interval = i}
}
