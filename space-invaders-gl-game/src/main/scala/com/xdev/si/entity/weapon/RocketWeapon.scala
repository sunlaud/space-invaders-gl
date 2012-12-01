package com.xdev.si.entity.weapon

import com.xdev.si.manager.GameManager
import org.openmali.vecmath2.Vector3f
import com.xdev.si.Game
import com.xdev.engine.util.ResourceFactory
import com.xdev.si.entity.AbstractEntity
import shots.{RocketShot, LaserShot, ShotEntity}

/**
 * User: xdev
 * Date: 06.12.2010
 * Time: 0:59:20
 */

class RocketWeapon(pos: Vector3f) extends AbstractWeapon(pos) {

  protected def makeShot(pos: Vector3f) {
    addShot(new RocketShot(new Vector3f(pos.getX + 10, pos.getY - 30, 0.0f)))
  }
}