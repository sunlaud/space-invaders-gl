package com.xdev.si.entity.player

import com.xdev.si.Game
import com.xdev.engine.sprite.Sprite
import collection.mutable.ArrayBuffer
import com.xdev.si.manager.GameManager
import javax.media.opengl.GL
import com.xdev.si.gllisteners.MainRenderLoop
import org.openmali.vecmath2.Vector3f
import com.xdev.si.entity.AbstractEntity
import com.xdev.si.entity.weapon.ShotEntity

/**
 * Created by User: xdev
 * Date: 24.08.2010
 * Time: 23:55:17
 */
case class ShipEntity(sprite : Sprite, listener: MainRenderLoop, pos: Vector3f) extends AbstractEntity(sprite, pos){

  private var lastFire: Long = 0
  private val firingInterval = 500
  val shots = new ArrayBuffer[ShotEntity]()
  
  override def move(delta: Long){
     shots.foreach(_.move(delta))
     if ((velocity.getX() < 0) && (position.getX() <= 0)) return
     if ((velocity.getX() > 0) && (position.getX() > Game.WND_WIDTH - width)) return
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
    if (System.currentTimeMillis() - lastFire < firingInterval) {
        return
    }
     // if we waited long enough, create the shot entity, and record the time.
    lastFire = System.currentTimeMillis()
    shots += GameManager.createShot(listener, Game.SHOT_SPRITE, new Vector3f(position.getX() + 10, position.getY() - 30, 0.0f))
  }
}