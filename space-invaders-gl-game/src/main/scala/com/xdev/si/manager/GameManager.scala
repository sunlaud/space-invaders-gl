package com.xdev.si.manager

import com.xdev.si.gllisteners.MainRenderLoop
import com.xdev.engine.sprite.Sprite
import org.openmali.vecmath2.Vector3f
import com.xdev.si.entity.weapon.ShotEntity
import com.xdev.si.entity.enemy.AlienEntity
import com.xdev.si.entity.player.ShipEntity
import com.xdev.engine.util.ResourceFactory
import com.xdev.si.entity.bonus._
import collection.mutable.{HashMap, ArrayBuffer}
import com.xdev.engine.logging.LogHelper
import util.Random

/**
 * User: xdev
 * Date: Nov 28, 2010
 * Time: 1:39:15 AM
 */

object GameManager extends LogHelper{

  val availableBonuses = new HashMap[Int, AbstractBonus]()
  //======================================================================
  // Entity management
  //======================================================================

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
  //======================================================================
  // Bonuses management
  //======================================================================
  def registerGameBonuses(): Unit = {
      availableBonuses.put(availableBonuses.size, new ShotSpeedBonus(new Vector3f(0,0,0)))
      availableBonuses.put(availableBonuses.size, new ShipAccBonus(new Vector3f(0,0,0)))
  }

  def generateRandomBonus(startPosition: Vector3f): Option[AbstractBonus] = {
    val randomIndex = Random.nextInt(availableBonuses.size * 15)
    val randomBonus: Option[AbstractBonus] = availableBonuses.get(randomIndex)
    if(randomBonus.isDefined){
      return randomBonus.get match {
        case b: ShotSpeedBonus => Some(new ShotSpeedBonus(startPosition))
        case b: ShipAccBonus => Some(new ShipAccBonus(startPosition))
        case _ => None
      }
    }
    return None
  }
}