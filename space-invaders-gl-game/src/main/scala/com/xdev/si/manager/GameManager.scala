package com.xdev.si.manager

import com.xdev.engine.sprite.Sprite
import org.openmali.vecmath2.Vector3f
import com.xdev.si.entity.weapon.ShotEntity
import com.xdev.si.entity.enemy.{AlienEnemy, EnemyEntity}
import com.xdev.si.entity.player.PlayerEntity
import com.xdev.engine.util.ResourceFactory
import com.xdev.si.entity.bonus._
import collection.mutable.{HashMap, ArrayBuffer}
import com.xdev.engine.logging.LogHelper
import util.Random
import com.xdev.si.core.loader.LevelLoader

/**
 * User: xdev
 * Date: Nov 28, 2010
 * Time: 1:39:15 AM
 */

object GameManager extends LogHelper{

  val availableBonuses = new HashMap[Int, AbstractBonus]()
  val BONUSES_RANDOM_SEEK = 15

  //======================================================================
  // Entity management
  //======================================================================

  def createPlayer(texture: String, position: Vector3f): PlayerEntity = {
    new PlayerEntity(ResourceFactory.getSprite(texture), position)
  }

  def createEnemies(texture: String, rows: Int, columns: Int): ArrayBuffer[EnemyEntity] = {
    val map = LevelLoader.load("/levels/1.lvl")

    val aliens = new ArrayBuffer[EnemyEntity]()
    for(y <- 0 until rows) {
      for(x <- 0 until columns) {
        //TODO: Remove listener, remove magic numbers
        aliens += new AlienEnemy(new Vector3f(100 + (x * 50), (50) + y * 30, 0.0f))
      }
    }
   aliens
  }

  def createSprite(texture: String): Sprite = {
      ResourceFactory.getSprite(texture)
  }
  
  def createShot(texture: String, position: Vector3f): ShotEntity = {
    new ShotEntity(ResourceFactory.getSprite(texture), position)
  }
  //======================================================================
  // Bonuses management
  //======================================================================
  def registerGameBonuses() {
      availableBonuses.put(availableBonuses.size, new ShotSpeedBonus(new Vector3f(0,0,0)))
      availableBonuses.put(availableBonuses.size, new ShipAccBonus(new Vector3f(0,0,0)))
      availableBonuses.put(availableBonuses.size, new ShotGunBonus(new Vector3f(0,0,0)))
  }

  def generateRandomBonus(startPosition: Vector3f): Option[AbstractBonus] = {
    val randomBonus: Option[AbstractBonus] = availableBonuses.get(Random.nextInt(availableBonuses.size * BONUSES_RANDOM_SEEK))
    if(randomBonus.isDefined){
      return randomBonus.get match {
        case b: ShotSpeedBonus => Some(new ShotSpeedBonus(startPosition))
        case b: ShipAccBonus => Some(new ShipAccBonus(startPosition))
        case b: ShotGunBonus => Some(new ShotGunBonus(startPosition))
        case _ => None
      }
    }
    None
  }
}