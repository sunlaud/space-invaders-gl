package com.xdev.si.manager

import com.xdev.si.ResourceFactory
import collection.mutable.ArrayBuffer
import com.xdev.si.entity.{AlienEntity, ShipEntity}
import com.xdev.si.gllisteners.MainRenderLoop
import com.xdev.engine.sprite.Sprite

/**
 * User: xdev
 * Date: Nov 28, 2010
 * Time: 1:39:15 AM
 */

object GameManager {

  def createPlayerShip(texture: String, x: Float, y: Float): ShipEntity = {
    return new ShipEntity(ResourceFactory.getSprite(texture), x, y)
  }

  def createAliens(listener: MainRenderLoop, texture: String, rows: Int, columns: Int): ArrayBuffer[AlienEntity] = {
    val aliens = new ArrayBuffer[AlienEntity]()
    for(y <- 0 until rows) {
      for(x <- 0 until columns) {
        //TODO: Remove listener, remove magic numbers
        aliens += new AlienEntity(ResourceFactory.getSprite(texture), listener, 100 + (x * 50),(50) + y * 30)
      }
    }
   return aliens
  }

  def createNewGameHud(texture: String): Sprite = {
      return ResourceFactory.getSprite(texture)
  }

}