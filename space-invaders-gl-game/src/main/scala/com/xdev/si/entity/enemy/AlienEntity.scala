package com.xdev.si.entity.enemy

import javax.media.opengl.GL
import com.xdev.engine.sprite.Sprite
import com.xdev.engine.animation.FrameAnimation
import com.xdev.si.Game
import org.openmali.vecmath2.Vector3f
import com.xdev.si.entity.AbstractEntity
import com.xdev.si.gllisteners.MainRenderLoop

/**
 * Created by User: xdev
 * Date: 25.08.2010
 * Time: 0:06:32
 */
object AlienEntity{
  val MAIN_ANIMATION = 0
  val EXPLOSION_ANIMATION = 1
}
class AlienEntity(sprite: Sprite, pos: Vector3f) extends AbstractEntity(sprite, pos) {

  var currentAnimation = AlienEntity.MAIN_ANIMATION

  override def init(){
    //Change started velocity
    velocity.setX(-30.0f * Game.CURRENT_LEVEL)

    addFrameAnimation(new FrameAnimation (
      id = AlienEntity.MAIN_ANIMATION,
      frames = Game.frameSets(AlienEntity.MAIN_ANIMATION),
      duration = 1000,
      looped = true)
    )
    addFrameAnimation(new FrameAnimation (
      id = AlienEntity.EXPLOSION_ANIMATION,
      frames = Game.frameSets(AlienEntity.EXPLOSION_ANIMATION),
      duration = 1000,
      onAnimationEndedHook = {
        isDead = true; MainRenderLoop.notifyAlienKilled()
      })
    )
    frameAnimations(currentAnimation).start()
  }

  override def move(delta: Long){
    if(markedAsDead)return 
    if ((velocity.getX() < 0) && (position.getX() <= 0)) { MainRenderLoop.updateEnemyesLogic() }
    if ((velocity.getX() > 0) && (position.getX() > Game.WND_WIDTH - width)) { MainRenderLoop.updateEnemyesLogic() }
    super.move(delta)
  }

  override def update(delta: Long){
    frameAnimations(currentAnimation).computeNextFrame(delta)
  }

  override def draw(gl: GL): Unit = {
    frameAnimations(currentAnimation).render(gl, position)
  }

  override def doLogic():Unit= {
    // swap over horizontal movement and move down the
    // screen a bit
    if(velocity.getX() < 0) position.subY(10) else position.addY(30)
    velocity.mulX(-1)
    // if we've reached the bottom of the screen then the player
    // dies
    if (position.getY() >= Game.WND_HEIGHT - (height * 3)) {
      MainRenderLoop.notifyPlayerShipDestroyed()
    }
  }

  def runFaster():Unit = {
    velocity.mulX(1.02f)
  }

  override def collidedWith(target: AbstractEntity): Unit = {}
  
  override def notifyDead(): Unit = {
    markedAsDead = true
    currentAnimation = AlienEntity.EXPLOSION_ANIMATION
    if(!frameAnimations(currentAnimation).isRunning())
      frameAnimations(currentAnimation).start()
    //Generate bonuse after enemy dead    
    MainRenderLoop.generateBonus(position.clone())
  }
  override def toString = "AlienEntity[" + position + "]"
}
