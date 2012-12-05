package com.xdev.si.entity.weapon.shots

import com.xdev.engine.util.ResourceFactory
import com.xdev.si.Game
import org.openmali.vecmath2.Vector3f
import com.xdev.engine.animation.FrameAnimation
import javax.media.opengl.GL2
import com.xdev.engine.logging.LogHelper
import com.xdev.si.core.Animation
import com.xdev.si.entity.AbstractEntity

object FireBallShot {
  //Weapon animation ids start from 100
  //FireBall: 121-130
  val FLIGHT_ANIMATION = 121
}

class FireBallShot(pos: Vector3f) extends ShotEntity(ResourceFactory.getSprite(Game.FIREBALL_SHOT_SPRITE), pos) with Animation{

  private val currentAnimation = FireBallShot.FLIGHT_ANIMATION

  //this is slow but powerful weapon
  collisionDamage = 100f
  healthPoints = 900f

  velocity.setY(-180)
  addFrameAnimation(new FrameAnimation(
    id = FireBallShot.FLIGHT_ANIMATION,
    frames = Game.frameSets(FireBallShot.FLIGHT_ANIMATION),
    duration = 300,
    looped = true)
  )
  frameAnimations(currentAnimation).start()

  override def update(delta: Long){
    frameAnimations(currentAnimation).computeNextFrame(delta)
  }

  override def draw(gl: GL2){
    frameAnimations(currentAnimation).render(gl, position)
  }

}
