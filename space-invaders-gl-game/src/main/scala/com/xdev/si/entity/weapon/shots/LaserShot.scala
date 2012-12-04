package com.xdev.si.entity.weapon.shots

import com.xdev.engine.util.ResourceFactory
import com.xdev.si.Game
import org.openmali.vecmath2.Vector3f
import com.xdev.engine.logging.LogHelper

class LaserShot(pos: Vector3f) extends ShotEntity(ResourceFactory.getSprite(Game.LASER_SHOT_SPRITE), pos){

  //Overriding inherited damage values and healthPoints
  collisionDamage = 10f
  healthPoints = 10f

  velocity.setY(-800)

  override def move(delta: Long){
    super.move(delta)
  }
}
