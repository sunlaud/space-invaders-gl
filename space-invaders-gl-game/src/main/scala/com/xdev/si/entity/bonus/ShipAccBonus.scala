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

case class ShipAccBonus(pos: Vector3f) extends AbstractBonus(ResourceFactory.getSprite(Game.B_SHIP_SPEED_SPRITE), pos){

  override def applyBonus(target: AbstractEntity): Unit = target match {
    case ship: ShipEntity => {
      if(ship.acceleration < ShipEntity.MAX_ACCELERATION)
        ship.acceleration += 15
    }
    case _ =>
  }
}