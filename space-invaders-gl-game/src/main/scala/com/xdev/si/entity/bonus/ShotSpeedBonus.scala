package com.xdev.si.entity.bonus

import com.xdev.si.entity.AbstractEntity
import org.openmali.vecmath2.Vector3f
import com.xdev.engine.util.ResourceFactory
import com.xdev.si.Game
import com.xdev.si.entity.player.PlayerEntity

/**
 * User: xdev.developer@gmail.com
 * Date: Nov 29, 2010
 * Time: 10:57:58 PM
 */

case class ShotSpeedBonus(pos: Vector3f) extends AbstractBonus(ResourceFactory.getSprite(Game.B_SHOT_SPEED_SPRITE), pos) {

  override def applyBonus(target: AbstractEntity) { target match {
      case ship: PlayerEntity => {
        if(ship.weapon.waitInterval() > PlayerEntity.MIN_FIRE_INTERVAL)
          ship.weapon.waitInterval(ship.weapon.waitInterval() - 15)
      }
      case _ =>
    }
  }
}