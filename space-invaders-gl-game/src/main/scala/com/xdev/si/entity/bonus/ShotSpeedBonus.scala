package com.xdev.si.entity.bonus

import com.xdev.si.entity.AbstractEntity
import org.openmali.vecmath2.Vector3f
import com.xdev.engine.util.ResourceFactory
import com.xdev.si.Game

/**
 * User: xdev
 * Date: Nov 29, 2010
 * Time: 10:57:58 PM
 */

case class ShotSpeedBonus(pos: Vector3f) extends AbstractEntity(ResourceFactory.getSprite(Game.B_SHOT_SPEED_SPRITE), pos) {

  override def init(): Unit = {
    velocity.setY(100)
  }
  override def move(delta: Long){
    super.move(delta)
    if (position.getY() < 0) notifyDead()
  }
  override def collidedWith(target: AbstractEntity): Unit = {}
  override def doLogic():Unit = {}
  override def update(delta: Long): Unit = {}
}