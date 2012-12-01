package com.xdev.si.entity.weapon

import collection.mutable.ArrayBuffer
import javax.media.opengl.GL2
import org.openmali.vecmath2.Vector3f
import com.xdev.si.core.HasWaitInterval
import shots.ShotEntity

/**
 * User: xdev.developer@gmail.com
 * Date: 06.12.2010
 * Time: 0:38:33
 */
abstract class AbstractWeapon(pos: Vector3f) extends HasWaitInterval{
  protected var lastFireTime: Long = 0
  protected val shotsList = new ArrayBuffer[ShotEntity]() //List of shots

  waitInterval(500) //Shot wait interval

  protected def makeShot(pos: Vector3f)

  final def fire() {
    if ((System.currentTimeMillis - lastFireTime) < waitInterval()) return
    makeShot(pos)
    lastFireTime = System.currentTimeMillis
  }

  def shots(): List[ShotEntity] = shotsList.toList

  final def removeUnusedShots() {
    shotsList--= shotsList.filter(e => e.isDead)
  }

  final def removeAllShots() {
    shotsList.clear()
  }

  final def getShotsCount: Int = shotsList.length

  final def draw(gl: GL2) {
    shotsList.foreach(_.draw(gl))
  }

  final def update(delta: Long) {
    shotsList.foreach(_.move(delta))
  }

  final def addShot(shot: ShotEntity) {
    shotsList += shot
  }
}