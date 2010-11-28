package com.xdev.si.entity

import com.xdev.si.Game
import com.xdev.engine.sprite.Sprite
import collection.mutable.ArrayBuffer
import com.xdev.si.manager.GameManager
import javax.media.opengl.GL
import com.xdev.si.gllisteners.MainRenderLoop

/**
 * Created by User: xdev
 * Date: 24.08.2010
 * Time: 23:55:17
 */
case class ShipEntity(sprite : Sprite, listener: MainRenderLoop, cx: Float, cy: Float) extends AbstractEntity(sprite, cx, cy){

  val shots = new ArrayBuffer[ShotEntity]()
  
  override def move(delta: Long){
     shots.foreach(_.move(delta))
     if ((vx < 0) && (x <= 0)) return
     if ((vx > 0) && (x > Game.WND_WIDTH - width)) return
     super.move(delta)
  }

  override def draw(gl: GL): Unit = {
    super.draw(gl)
    shots.foreach(_.draw(gl))
  }

  override def init(){}
  override def collidedWith(target: AbstractEntity): Unit = {}
  override def doLogic():Unit= {}
  override def notifyDead(): Unit = {}
  override def update(delta: Long){}

  def fire(){
    if (System.currentTimeMillis() - ShotEntity.lastFire < ShotEntity.firingInterval) {
        return
    }
     // if we waited long enough, create the shot entity, and record the time.
    ShotEntity.lastFire = System.currentTimeMillis()
    shots += GameManager.createShot(listener, Game.SHOT_SPRITE, x + 10, y - 30)
  }
}
