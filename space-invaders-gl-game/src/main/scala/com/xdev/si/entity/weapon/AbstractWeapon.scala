package com.xdev.si.entity.weapon

import collection.mutable.ArrayBuffer
import javax.media.opengl.GL
import org.openmali.vecmath2.Vector3f

/**
 * User: xdev
 * Date: 06.12.2010
 * Time: 0:38:33
 */
abstract class AbstractWeapon(pos: Vector3f) {
  val shots = new ArrayBuffer[ShotEntity]() //List of shots
  var firingInterval = 500 //milliseconds
  val weaponPos = pos

  def fire()

  def removeUnusedShots() {
    shots--= shots.filter(e => e.isDead)    
  }

  def removeAllShots() {
    shots.clear()    
  }

  def getShotsCount: Int = shots.length

  def draw(gl: GL) {
    shots.foreach(_.draw(gl))
  }

  def update(delta: Long) {
    shots.foreach(_.move(delta))
  }

  def addShot(shot: ShotEntity) {
    shots += shot    
  }
}