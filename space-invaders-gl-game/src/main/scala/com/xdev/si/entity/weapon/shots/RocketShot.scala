package com.xdev.si.entity.weapon.shots

import com.xdev.engine.util.ResourceFactory
import com.xdev.si.Game
import org.openmali.vecmath2.Vector3f
import com.xdev.engine.animation.FrameAnimation
import javax.media.opengl.GL2
import com.xdev.engine.logging.LogHelper

object RocketShot {
  //Weapon animation ids start from 100
  val FLIGHT_ANIMATION = 101
}

final class RocketShot(pos: Vector3f) extends ShotEntity(ResourceFactory.getSprite(Game.ROCKET_SHOT_SPRITE), pos) with LogHelper{

  private var currentAnimation = RocketShot.FLIGHT_ANIMATION
  private val acceleration = 1.02f

  //Overriding inherited damage values and healthPoints
  collisionDamage = 35f
  healthPoints = 50f

  override def init() {
    velocity.setY(-100)
    addFrameAnimation(new FrameAnimation (
      id = RocketShot.FLIGHT_ANIMATION,
      frames = Game.frameSets(RocketShot.FLIGHT_ANIMATION),
      duration = 1000,
      looped = true)
    )
    frameAnimations(currentAnimation).start()
  }

  override def move(delta: Long){
    super.move(delta)
    velocity.mulY(acceleration)
    collisionDamage += 1f
  }

  override def update(delta: Long){
    frameAnimations(currentAnimation).computeNextFrame(delta)
  }

  override def draw(gl: GL2) {
    frameAnimations(currentAnimation).render(gl, position)
  }

}
