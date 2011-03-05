package com.xdev.si.entity.bonus

import org.openmali.vecmath2.Vector3f
import com.xdev.engine.sprite.Sprite
import com.xdev.si.entity.AbstractEntity
import com.xdev.si.Game
import java.awt.Rectangle
import javax.media.opengl.GL
/**
 * User: xdev
 * Date: Nov 30, 2010
 * Time: 11:08:51 PM
 */

abstract class AbstractBonus(sprite : Sprite, pos: Vector3f) {
  //Coordinates
  val position = pos
  val width = sprite.getWidth()
  val height = sprite.getHeight()

  val velocity: Vector3f = new Vector3f(0.0f, 80.0f, 0.0f)
  //Bounding boxes
  private val thisBoundBox : Rectangle  = new Rectangle(position.getX().asInstanceOf[Int], position.getY().asInstanceOf[Int], width, height)
  private val targetBoundBox : Rectangle  = new Rectangle()
  //State
  var markedAsDead = false
  var isDead = false

  /* =============================================
     Methods
    =============================================*/
  /**
   * Move  entity
   */
  def move(delta: Long){
    position.addX((velocity.getX() * delta) / 1000.0f)
    position.addY((velocity.getY() * delta) / 1000.0f)
    position.addZ((velocity.getZ() * delta) / 1000.0f)
    thisBoundBox.x = position.getX().asInstanceOf[Int]
    thisBoundBox.y = position.getY().asInstanceOf[Int]
    if (position.getY() > Game.WND_HEIGHT) {
       notifyDead()
    }
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

  def isCollidesWith(target : AbstractEntity): Boolean ={
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

  def collidedWith(target: AbstractEntity): Unit = {
    notifyDead()
    applyBonus(target)
  }

  def notifyDead(): Unit = {
    isDead = true
    markedAsDead = true
  }
  //Abstract methods

  /**
   * Apply bonus to target entity
   */
  def applyBonus(target: AbstractEntity): Unit
}