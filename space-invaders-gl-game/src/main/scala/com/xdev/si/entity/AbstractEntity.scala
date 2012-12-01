package com.xdev.si.entity

import javax.media.opengl.GL2
import com.xdev.engine.sprite.Sprite
import collection.mutable.HashMap
import com.xdev.engine.animation.FrameAnimation
import java.awt.Rectangle
import org.openmali.vecmath2.Vector3f
import com.xdev.si.core.MoveFunction

/**
 * Created by User: xdev
 * Date: 24.08.2010
 * Time: 21:56:53
 */
abstract class AbstractEntity (sprite : Sprite, pos: Vector3f, vel: Vector3f) extends MoveFunction {
  //Coordinates
  val position = pos
  protected val velocity = vel
  val width = sprite.getWidth
  val height = sprite.getHeight

  //Bounding boxes
  private val thisBoundBox : Rectangle  = new Rectangle(position.getX.asInstanceOf[Int], position.getY.asInstanceOf[Int], width, height)
  private val targetBoundBox : Rectangle  = new Rectangle()
  //State
  var healthPoints: Float = 100
  var collisionDamage: Float = 10
  var markedAsDead = false
  var isDead = false
  protected val frameAnimations = new HashMap[Int, FrameAnimation]()

  //Run init method on constructor creation
  init()

  def this(sprite: Sprite, pos:Vector3f) = this(sprite, pos, new Vector3f(0.0f, 0.0f, 0.0f))
  def this(sprite: Sprite) = this(sprite, new Vector3f(0.0f, 0.0f, 0.0f) , new Vector3f(0.0f, 0.0f, 0.0f))
  /* =============================================
     Methods
    =============================================*/

  def fx(x: Float) = x
  def fy(y: Float) = y

  /**
   * Move  entity
   */
  def move(delta: Long){
    val dt = (delta / 1000.0f)
    val x = pos.addX(velocity.getX * dt).getX
    val y = pos.addY(velocity.getY * dt).getY
    position.setX(fx(x))
    position.setY(fy(y))
    position.setZ(pos.getZ)
    thisBoundBox.x = position.getX.asInstanceOf[Int]
    thisBoundBox.y = position.getY.asInstanceOf[Int]
  }

  final def accelerate(dx: Float = 0.0f, dy: Float = 0.0f, dz: Float = 0.0f){
    velocity.addX(dx)
    velocity.addY(dy)
    velocity.addZ(dz)
  }

  final def stop(){
    velocity.setX(0.0f)
    velocity.setY(0.0f)
    velocity.setZ(0.0f)
  }

  def draw(gl: GL2) {sprite.draw(gl, position)}
  
  def collidesWith(target : AbstractEntity): Boolean ={
    thisBoundBox.setBounds(position.getX.asInstanceOf[Int],
      position.getY.asInstanceOf[Int],
      width,
      height)
    targetBoundBox.setBounds(target.position.getX.asInstanceOf[Int],
      target.position.getY.asInstanceOf[Int],
      target.width,
      target.height)
    thisBoundBox.intersects(targetBoundBox)
  }
  //Abstract methods
  protected def init()
  def collidedWith(target: AbstractEntity)
  def doLogic()
  def update(delta: Long)

  def notifyDead() {
    isDead = true
    markedAsDead = true
  }

  def takeDamage(damage: Float) {
    healthPoints -= damage;
    if (healthPoints < 0) notifyDead();
  }

  def heal(heal: Float) {
    healthPoints += heal
  }
  
  final def addFrameAnimation(animation: FrameAnimation){
     frameAnimations.put(animation.id, animation)
  }
}
