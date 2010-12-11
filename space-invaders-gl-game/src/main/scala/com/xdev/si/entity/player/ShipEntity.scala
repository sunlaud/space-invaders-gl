package com.xdev.si.entity.player

import com.xdev.si.Game
import com.xdev.engine.sprite.Sprite
import javax.media.opengl.GL
import org.openmali.vecmath2.Vector3f
import com.xdev.si.entity.AbstractEntity
import com.xdev.si.entity.weapon.{RocketWeapon, AbstractWeapon}

/**
 * Created by User: xdev
 * Date: 24.08.2010
 * Time: 23:55:17
 */
object ShipEntity{
  val MAX_ACCELERATION = 500
  val MIN_FIRE_INTERVAL = 100
}
case class ShipEntity(sprite : Sprite, pos: Vector3f) extends AbstractEntity(sprite, pos){
  var weapon: AbstractWeapon = new RocketWeapon(pos)
  var acceleration = 250

  override def move(delta: Long){
    if ((velocity.getX() < 0) && (position.getX() <= 0)) position.setX(0.0f)
    if ((velocity.getX() > 0) && (position.getX() > Game.WND_WIDTH - width)) position.setX(Game.WND_WIDTH - width)
    super.move(delta)
    weapon.update(delta)
  }

  override def draw(gl: GL): Unit = {
    super.draw(gl)
    weapon.draw(gl)
  }

  override def init(){}
  
  override def collidedWith(target: AbstractEntity): Unit = {
    target.notifyDead()
    this.notifyDead()
  }
  
  override def doLogic():Unit= {}
  
  override def update(delta: Long){}

  def fire(){
    weapon.fire()
  }
}