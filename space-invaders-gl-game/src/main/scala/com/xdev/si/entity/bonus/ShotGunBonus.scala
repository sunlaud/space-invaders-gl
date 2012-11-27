package com.xdev.si.entity.bonus

import org.openmali.vecmath2.Vector3f
import com.xdev.engine.util.ResourceFactory
import com.xdev.si.Game
import com.xdev.si.entity.AbstractEntity
import com.xdev.si.entity.player.PlayerEntity
import com.xdev.si.entity.weapon.ShotgunWeapon

/**
 * User: xdev.developer@gmail.com
 * Date: 27.11.12
 * Time: 23:44
 */
final case class ShotGunBonus (pos: Vector3f) extends AbstractBonus(ResourceFactory.getSprite(Game.B_SHOTGUN), pos) {

  override def applyBonus(target: AbstractEntity) { target match {
    case p: PlayerEntity => {
     p.weapon(new ShotgunWeapon(p.pos))
    }
    case _ =>
  }
  }
}
