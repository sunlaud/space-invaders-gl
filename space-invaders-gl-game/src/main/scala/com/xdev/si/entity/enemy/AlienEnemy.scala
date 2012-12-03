package com.xdev.si.entity.enemy

import com.xdev.engine.util.ResourceFactory
import com.xdev.si.Game
import org.openmali.vecmath2.Vector3f
import com.xdev.engine.animation.FrameAnimation
import com.xdev.si.gllisteners.MainRenderLoop
import javax.media.opengl.GL2
import com.xdev.si.entity.AbstractEntity
import com.xdev.si.core.Animation

/**
 * User: xdev.developer@gmail.com
 * Date: 27.11.12
 * Time: 23:08
 */
object AlienEnemy {
  val MAIN_ANIMATION = 0
  val EXPLOSION_ANIMATION = 1
  val DAMAGE_ANIMATION = 3
}
final class AlienEnemy(pos: Vector3f) extends EnemyEntity(ResourceFactory.getSprite(Game.ALIEN_SPRITE_0), pos) with Animation{
  private var currentAnimation = AlienEnemy.MAIN_ANIMATION

  healthPoints = 50

  collisionDamage = 30

    //Change started velocity
    velocity.setX(-30.0f * Game.CURRENT_LEVEL)

    addFrameAnimation(new FrameAnimation (
      id = AlienEnemy.MAIN_ANIMATION,
      frames = Game.frameSets(AlienEnemy.MAIN_ANIMATION),
      duration = 1000,
      looped = true)
    )
    addFrameAnimation(new FrameAnimation (
      id = AlienEnemy.EXPLOSION_ANIMATION,
      frames = Game.frameSets(AlienEnemy.EXPLOSION_ANIMATION),
      duration = 1000,
      onAnimationEndedHook = {
        isDead = true; MainRenderLoop.notifyAlienKilled()
      })
    )
    addFrameAnimation(new FrameAnimation(
      id = AlienEnemy.DAMAGE_ANIMATION,
      frames = Game.frameSets(AlienEnemy.DAMAGE_ANIMATION),
      duration = 250,
      onAnimationEndedHook = {
        currentAnimation = AlienEnemy.MAIN_ANIMATION
      })
    )
    frameAnimations(currentAnimation).start()

  override def move(delta: Long){
    if(markedAsDead)return
    if ((velocity.getX < 0) && (position.getX <= 0)) { MainRenderLoop.updateEnemiesLogic() }
    if ((velocity.getX > 0) && (position.getX > Game.WND_WIDTH - width)) { MainRenderLoop.updateEnemiesLogic() }
    super.move(delta)
  }

  override def update(delta: Long){
    frameAnimations(currentAnimation).computeNextFrame(delta)
  }

  override def draw(gl: GL2) {
    frameAnimations(currentAnimation).render(gl, position)
  }

  override def doLogic() {
    // swap over horizontal movement and move down the
    // screen a bit
    if(velocity.getX < 0) position.subY(10) else position.addY(30)
    velocity.mulX(-1)
    // if we've reached the bottom of the screen then the player
    // dies
    if (position.getY >= Game.WND_HEIGHT - (height * 3)) {
      MainRenderLoop.notifyPlayerShipDestroyed()
    }
  }

  override def collidedWith(target: AbstractEntity) {
  }

  override def notifyDead() {
    markedAsDead = true
    currentAnimation = AlienEnemy.EXPLOSION_ANIMATION
    if(!frameAnimations(currentAnimation).isRunning)
      frameAnimations(currentAnimation).start()
    //Generate bonuse after enemy dead
    MainRenderLoop.generateBonus(position.clone())
  }

  override def takeDamage(damage: Float) {
    currentAnimation = AlienEnemy.DAMAGE_ANIMATION
    if(!frameAnimations(currentAnimation).isRunning)
      frameAnimations(currentAnimation).start()
    super.takeDamage(damage)
  }

  /**
   * Notifies when player kill friend enemy
   */
  def onFriendEnemyKilled() {
    velocity.mulX(1.02f) // Run faster
  }
}
