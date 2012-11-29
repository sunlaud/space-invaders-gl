package com.xdev.si.core

/**
 * User: xdev.developer@gmail.com
 * Date: 27.11.12
 * Time: 22:38
 */
trait HasWaitInterval {
  private var interval: Long = _
  def waitInterval(): Long = interval
  def waitInterval(i: Long) { this.interval = i}
}
