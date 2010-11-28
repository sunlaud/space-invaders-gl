package com.xdev.si.entity

import com.xdev.si.Game
import javax.media.opengl.GL
import com.xdev.si.gllisteners.MainRenderLoop
import com.xdev.engine.sprite.Sprite
import com.xdev.engine.animation.FrameAnimation

/**
 * Created by User: xdev
 * Date: 25.08.2010
 * Time: 0:06:32
 */
object AlienEntity{
  val MAIN_ANIMATION = 0
  val EXPLOSION_ANIMATION = 1
}
class AlienEntity(sprite: Sprite, listener: MainRenderLoop, cx: Float, cy: Float) extends AbstractEntity(sprite, cx, cy){

  var currentAnimation = AlienEntity.MAIN_ANIMATION

  //Change started velocity
  vx = -50.0f * Game.CURRENT_LEVEL

  override def init(){
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
      onAnimationEndedHook = {isDead = true})
    )
    frameAnimations(currentAnimation).start()
  }

  override def move(delta: Long){
    if ((vx < 0) && (x <= 0)) {/*doLogic()*/}
    if ((vx > 0) && (x > Game.WND_WIDTH - width)) {/*doLogic()*/}
    super.move(delta)
  }

  override def update(delta: Long){
    frameAnimations(currentAnimation).computeNextFrame(delta)
  }

  override def draw(gl: GL): Unit = {
    frameAnimations(currentAnimation).render(gl, x, y)
  }

  def doLogic():Unit= {
    // swap over horizontal movement and move down the
    // screen a bit
    if(vx < 0) y -= 10 else y += 30
    vx = -vx
    // if we've reached the bottom of the screen then the player
    // dies
    if (y >= Game.WND_HEIGHT - (height * 3)) {
      //listener.notifyDeath()
    }
  }

  def runFaster():Unit = {
    vx = vx * 1.02f
  }

  def collidedWith(target: AbstractEntity): Unit = {}
  
  def notifyDead(): Unit = {
    markedAsDead = true
    currentAnimation = AlienEntity.EXPLOSION_ANIMATION
    if(!frameAnimations(currentAnimation).isRunning())
      frameAnimations(currentAnimation).start()
  }
  override def toString = "AlienEntity[" + x + ", " + y + "]"
}
