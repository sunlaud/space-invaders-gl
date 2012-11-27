package com.xdev.si.entity.weapon

import org.openmali.vecmath2.Vector3f
import com.xdev.si.Game
import com.xdev.engine.util.ResourceFactory

/**
 * User: xdev.developer@gmail.com
 * Date: 08.12.2010
 * Time: 23:27:47
 */

final class ShotgunWeapon(pos: Vector3f) extends AbstractWeapon(pos) {

  private val deltaMove = 0.3f

  protected def makeShot(pos: Vector3f) {
    val left = new ShotEntity(ResourceFactory.getSprite(Game.SHOT_SPRITE), new Vector3f(pos.getX + 10, pos.getY - 30, 0.0f)) {
      override def moveFunction(dt: Float, pos: Vector3f, velocity: Vector3f): Vector3f = {
        pos.addX((velocity.getX * dt) - deltaMove)
        pos.addY(velocity.getY * dt)
        pos.addZ(velocity.getZ * dt)
        pos
      }
    }
    val center = new ShotEntity(ResourceFactory.getSprite(Game.SHOT_SPRITE), new Vector3f(pos.getX + 10, pos.getY - 30, 0.0f)) {
      override def moveFunction(dt: Float, pos: Vector3f, velocity: Vector3f): Vector3f = {
        pos.addX(velocity.getX * dt)
        pos.addY(velocity.getY * dt)
        pos.addZ(velocity.getZ * dt)
        pos
      }
    }
    val right = new ShotEntity(ResourceFactory.getSprite(Game.SHOT_SPRITE), new Vector3f(pos.getX + 10, pos.getY - 30, 0.0f)) {
      override def moveFunction(dt: Float, pos: Vector3f, velocity: Vector3f): Vector3f = {
        pos.addX((velocity.getX * dt) + deltaMove)
        pos.addY(velocity.getY * dt)
        pos.addZ(velocity.getZ * dt)
        pos
      }
    }
    addShot(left)
    addShot(center)
    addShot(right)
  }

}