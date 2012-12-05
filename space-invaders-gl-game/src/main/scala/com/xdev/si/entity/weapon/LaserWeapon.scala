package com.xdev.si.entity.weapon

import org.openmali.vecmath2.Vector3f
import shots.LaserShot

class LaserWeapon(pos: Vector3f) extends AbstractWeapon(pos) {

  waitInterval(150)

  protected def makeShot(pos: Vector3f) {
    val left = new LaserShot(new Vector3f(pos.getX, pos.getY - 30, 0.0f))
    val right = new LaserShot(new Vector3f(pos.getX + 20, pos.getY - 30, 0.0f))
    addShot(left)
    addShot(right)
  }

}
