package com.xdev.si.entity.weapon

import org.openmali.vecmath2.Vector3f
import com.xdev.si.Game
import com.xdev.engine.util.ResourceFactory
import shots.{ShotgunShot, ShotEntity}
import com.xdev.si.entity.AbstractEntity

/**
 * User: xdev.developer@gmail.com
 * Date: 08.12.2010
 * Time: 23:27:47
 */

final class ShotgunWeapon(pos: Vector3f) extends AbstractWeapon(pos) {

  private val deltaMove = 0.3f

  protected def makeShot(pos: Vector3f) {
    val left = new ShotgunShot(new Vector3f(pos.getX, pos.getY - 30, 0.0f)) {
      override def fx(x: Float): Float = x - deltaMove

    }
    val center = new ShotgunShot(new Vector3f(pos.getX + 10, pos.getY - 30, 0.0f))

    val right = new ShotgunShot(new Vector3f(pos.getX + 20, pos.getY - 30, 0.0f)) {
      override def fx(x: Float): Float = x + deltaMove
    }
    addShot(left)
    addShot(center)
    addShot(right)
  }

}