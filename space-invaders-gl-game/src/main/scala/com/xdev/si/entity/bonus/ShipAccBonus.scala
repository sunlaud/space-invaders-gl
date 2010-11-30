package com.xdev.si.entity.bonus

import com.xdev.si.entity.AbstractEntity
import org.openmali.vecmath2.Vector3f
import com.xdev.engine.util.ResourceFactory
import com.xdev.si.Game
import com.xdev.engine.logging.LogHelper
import com.xdev.si.entity.player.ShipEntity

/**
 * User: xdev
 * Date: Nov 29, 2010
 * Time: 10:57:58 PM
 */

case class ShipAccBonus(pos: Vector3f) extends AbstractEntity(ResourceFactory.getSprite(Game.B_SHIP_SPEED_SPRITE), pos) with LogHelper {

  override def init(): Unit = {
    velocity.setY(200)
  }
  override def move(delta: Long){
    super.move(delta)
    if (position.getY() > Game.WND_HEIGHT) {
      notifyDead()
    }
  }
  override def collidedWith(target: AbstractEntity): Unit = {
    notifyDead()
    target.asInstanceOf[ShipEntity].applyBonus(this)
  }
  override def doLogic():Unit = {}
  override def update(delta: Long): Unit = {}
}