package com.xdev.si.manager

import com.xdev.si.ResourceFactory
import collection.mutable.ArrayBuffer
import com.xdev.si.gllisteners.MainRenderLoop
import com.xdev.engine.sprite.Sprite
import org.openmali.vecmath2.Vector3f
import com.xdev.si.entity.weapon.ShotEntity
import com.xdev.si.entity.enemy.AlienEntity
import com.xdev.si.entity.player.ShipEntity

/**
 * User: xdev
 * Date: Nov 28, 2010
 * Time: 1:39:15 AM
 */

object GameManager {

  def createPlayerShip(listener: MainRenderLoop, texture: String, position: Vector3f): ShipEntity = {
    return new ShipEntity(ResourceFactory.getSprite(texture), listener, position)
  }

  def createAliens(listener: MainRenderLoop, texture: String, rows: Int, columns: Int): ArrayBuffer[AlienEntity] = {
    val aliens = new ArrayBuffer[AlienEntity]()
    for(y <- 0 until rows) {
      for(x <- 0 until columns) {
        //TODO: Remove listener, remove magic numbers
        aliens += new AlienEntity(ResourceFactory.getSprite(texture), listener, new Vector3f(100 + (x * 50), (50) + y * 30, 0.0f))
      }
    }
   return aliens
  }

  def createInfoTexture(texture: String): Sprite = {
      return ResourceFactory.getSprite(texture)
  }
  
  def createShot(listener: MainRenderLoop, texture: String, position: Vector3f): ShotEntity = {
    return new ShotEntity(ResourceFactory.getSprite(texture), listener, position)
  }
}