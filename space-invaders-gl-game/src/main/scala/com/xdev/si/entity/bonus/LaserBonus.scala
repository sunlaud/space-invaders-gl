package com.xdev.si.entity.bonus

import org.openmali.vecmath2.Vector3f
import com.xdev.engine.util.ResourceFactory
import com.xdev.si.Game
import com.xdev.si.entity.AbstractEntity
import com.xdev.si.entity.player.PlayerEntity
import com.xdev.si.entity.weapon.LaserWeapon

final case class LaserBonus (pos: Vector3f) extends AbstractBonus(ResourceFactory.getSprite(Game.B_LASER), pos) {

  override def applyBonus(target: AbstractEntity) { target match {
    case p: PlayerEntity => {
     p.weapon(new LaserWeapon(p.pos))
    }
    case _ =>
  }
  }
}
