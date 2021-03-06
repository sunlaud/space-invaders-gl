package com.xdev.si.entity.bonus

import org.openmali.vecmath2.Vector3f
import com.xdev.engine.util.ResourceFactory
import com.xdev.si.Game
import com.xdev.si.entity.AbstractEntity
import com.xdev.si.entity.player.PlayerEntity
import com.xdev.si.entity.weapon.RocketWeapon

final case class RocketBonus (pos: Vector3f) extends AbstractBonus(ResourceFactory.getSprite(Game.B_ROCKET), pos) {

  override def applyBonus(target: AbstractEntity) { target match {
    case p: PlayerEntity => {
     p.changeWeapon(new RocketWeapon(p.pos))
    }
    case _ =>
  }
  }
}
