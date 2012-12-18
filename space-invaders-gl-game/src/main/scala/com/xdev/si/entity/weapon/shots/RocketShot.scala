package com.xdev.si.entity.weapon.shots

import com.xdev.engine.util.ResourceFactory
import com.xdev.si.Game
import org.openmali.vecmath2.Vector3f
import com.xdev.engine.animation.FrameAnimation
import javax.media.opengl.GL2
import com.xdev.engine.logging.LogHelper
import com.xdev.si.core.Animation
import com.xdev.si.entity.AbstractEntity

object RocketShot {
  //Weapon animation ids start from 100
  //Rocket: 101-110
  val FLIGHT_ANIMATION = 101
}

class RocketShot(pos: Vector3f) extends ShotEntity(ResourceFactory.getSprite(Game.ROCKET_SHOT_SPRITE), pos) with Animation{

  private val currentAnimation = RocketShot.FLIGHT_ANIMATION
  private val acceleration = 1.02f

  applyParams(ShotParamsFactory.getRocketShotParams)

  addFrameAnimation(new FrameAnimation(
    id = RocketShot.FLIGHT_ANIMATION,
    frames = Game.frameSets(RocketShot.FLIGHT_ANIMATION),
    duration = 300,
    looped = true)
  )
  frameAnimations(currentAnimation).start()

  override def move(delta: Long){
    super.move(delta)
    velocity.mulY(acceleration)
    collisionDamage += 1.2f
  }

  override def update(delta: Long){
    frameAnimations(currentAnimation).computeNextFrame(delta)
  }

  override def draw(gl: GL2){
    frameAnimations(currentAnimation).render(gl, position)
  }

}
