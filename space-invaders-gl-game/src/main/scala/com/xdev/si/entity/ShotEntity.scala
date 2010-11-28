package com.xdev.si.entity

import com.xdev.si.gllisteners.MainRenderLoop
import com.xdev.engine.sprite.Sprite

/**
 * Created by User: xdev
 * Date: 25.08.2010
 * Time: 0:07:51
 */
object ShotEntity{
  var lastFire: Long = 0
  val firingInterval: Long = 500
  val START_VELOCITY_Y = -350
}
class ShotEntity(sprite : Sprite, listener: MainRenderLoop, cx: Float, cy: Float) extends AbstractEntity(sprite, cx, cy){

  private var used  = false

  override def init(){
    vy = ShotEntity.START_VELOCITY_Y
  }

  override def move(delta: Long){
    super.move(delta)
    if (y < 0) notifyDead()
  }

  override def collidedWith(target: AbstractEntity): Unit = {
    if (used) {return}
    this.notifyDead()
    target.notifyDead()
    used = true
  }

  override def doLogic():Unit= {}
  override def update(delta: Long){}

  override def toString = "ShotEntity[" + x + ", " + y + "]"
}
