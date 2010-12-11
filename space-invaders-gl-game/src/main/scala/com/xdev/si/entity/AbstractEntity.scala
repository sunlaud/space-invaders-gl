package com.xdev.si.entity

import javax.media.opengl.GL
import com.xdev.engine.sprite.Sprite
import collection.mutable.HashMap
import com.xdev.engine.animation.FrameAnimation
import java.awt.Rectangle
import org.openmali.vecmath2.Vector3f

/**
 * Created by User: xdev
 * Date: 24.08.2010
 * Time: 21:56:53
 */
abstract class AbstractEntity (sprite : Sprite, pos: Vector3f) {
  //Coordinates
  val position = pos
  val width = sprite.getWidth()
  val height = sprite.getHeight()

  val velocity: Vector3f = new Vector3f(0.0f, 0.0f, 0.0f)
  //Bounding boxes
  private val thisBoundBox : Rectangle  = new Rectangle(position.getX().asInstanceOf[Int], position.getY().asInstanceOf[Int], width, height)
  private val targetBoundBox : Rectangle  = new Rectangle()
  //State
  var markedAsDead = false
  var isDead = false
  protected val frameAnimations = new HashMap[Int, FrameAnimation]()

  //Run init method on constructor creation
  init()

  /* =============================================
     Methods
    =============================================*/
  /**
   * Move  entity
   */
  def move(delta: Long){
    val dt = (delta / 1000.0f)
    position.addX(velocity.getX() * dt)
    position.addY(velocity.getY() * dt)
    position.addZ(velocity.getZ() * dt)
    thisBoundBox.x = position.getX().asInstanceOf[Int]
    thisBoundBox.y = position.getY().asInstanceOf[Int]
  }

  def accelerate(dx: Float, dy: Float){
    velocity.addX(dx)
    velocity.addY(dy)
  }

  def stop(){
    velocity.setX(0.0f)
    velocity.setY(0.0f)
    velocity.setZ(0.0f)
  }

  def draw(gl: GL) = sprite.draw(gl, position)
  
  def collidesWith(target : AbstractEntity): Boolean ={
    thisBoundBox.setBounds(position.getX().asInstanceOf[Int],
      position.getY().asInstanceOf[Int],
      width,
      height)
    targetBoundBox.setBounds(target.position.getX().asInstanceOf[Int],
      target.position.getY().asInstanceOf[Int],
      target.width,
      target.height)
    return thisBoundBox.intersects(targetBoundBox)
  }
  //Abstract methods
  protected def init(): Unit
  def collidedWith(target: AbstractEntity): Unit
  def doLogic():Unit
  def update(delta: Long): Unit

  def notifyDead(): Unit = {
    isDead = true
    markedAsDead = true
  }
  
  def addFrameAnimation(animation: FrameAnimation){
     frameAnimations.put(animation.id, animation)
  }
}
