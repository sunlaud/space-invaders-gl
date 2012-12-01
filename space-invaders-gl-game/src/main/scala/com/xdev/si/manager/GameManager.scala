package com.xdev.si.manager

import com.xdev.engine.sprite.Sprite
import org.openmali.vecmath2.Vector3f
import com.xdev.si.entity.enemy.{AlienEnemy, EnemyEntity}
import com.xdev.si.entity.player.PlayerEntity
import com.xdev.engine.util.ResourceFactory
import com.xdev.si.entity.bonus._
import collection.mutable.{HashMap, ArrayBuffer}
import com.xdev.engine.logging.LogHelper
import util.Random
import com.xdev.si.core.loader.LevelLoader
import com.xdev.si.entity.weapon.shots.ShotEntity

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

  def createEnemies(map: Array[Array[Int]]): ArrayBuffer[EnemyEntity] = {
    val aliens = new ArrayBuffer[EnemyEntity]()
    for(y <- 0 until map.size) {
      for(x <- 0 until map(0).size) {
        //TODO: Remove listener, remove magic numbers
        map(y)(x) match {
          case 1 => {
            aliens += new AlienEnemy(new Vector3f((x * 43), (y * 30) + 30, 0.0f))
          }
          case _=>{}
        }

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