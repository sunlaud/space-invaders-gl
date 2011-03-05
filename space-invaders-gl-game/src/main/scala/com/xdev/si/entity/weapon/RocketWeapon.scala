package com.xdev.si.entity.weapon

import com.xdev.si.manager.GameManager
import org.openmali.vecmath2.Vector3f
import com.xdev.si.Game

/**
 * User: xdev
 * Date: 06.12.2010
 * Time: 0:59:20
 */

class RocketWeapon(pos: Vector3f) extends AbstractWeapon(pos) {
 private var lastFire: Long = 0

 override def fire(): Unit = {
   if (System.currentTimeMillis() - lastFire < firingInterval) {
       return
   }
    // if we waited long enough, create the shot entity, and record the time.
   lastFire = System.currentTimeMillis()
   addShot(GameManager.createShot(Game.SHOT_SPRITE, new Vector3f(weaponPos.getX() + 10, weaponPos.getY() - 30, 0.0f)))
 }

}