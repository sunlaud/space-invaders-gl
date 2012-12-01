package com.xdev.si.core

import collection.mutable.HashMap
import com.xdev.engine.animation.FrameAnimation

/**
 * User: xdev.developer@gmail.com
 * Date: 01.12.12
 * Time: 22:56
 */
trait Animation {

  val frameAnimations = new HashMap[Int, FrameAnimation]()

  def addFrameAnimation(animation: FrameAnimation){
    frameAnimations.put(animation.id, animation)
  }
}
