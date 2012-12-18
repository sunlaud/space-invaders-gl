package com.xdev.si.entity.weapon.shots
import org.openmali.vecmath2.Vector3f
import com.xdev.si.Game
import com.xdev.si.entity.AbstractEntity
import com.xdev.engine.util.ResourceFactory
import com.xdev.engine.animation.FrameAnimation
import com.xdev.si.core.Animation
import javax.media.opengl.GL2

object ShotgunShot {
  //Weapon animation ids start from 100
  //Shotgun: 111-120
  val SPINNING_ANIMATION = 111
}

class ShotgunShot(pos: Vector3f) extends ShotEntity(ResourceFactory.getSprite(Game.SHOTGUN_SHRAPNEL_SPRITE), pos) with Animation{

  private val currentAnimation = ShotgunShot.SPINNING_ANIMATION

  applyParams(ShotParamsFactory.getShotgunShotParams)

  addFrameAnimation(new FrameAnimation(
    id = ShotgunShot.SPINNING_ANIMATION,
    frames = Game.frameSets(ShotgunShot.SPINNING_ANIMATION),
    duration = 500,
    looped = true)
  )
  frameAnimations(currentAnimation).start()

  override def collidedWith(target: AbstractEntity) {
    this.velocity.add(target.getVelocity)
    target.takeDamage(collisionDamage)
    this.takeDamage(target.collisionDamage)
  }

  override def update(delta: Long){
    frameAnimations(currentAnimation).computeNextFrame(delta)
  }

  override def draw(gl: GL2){
    frameAnimations(currentAnimation).render(gl, position)
  }

}
