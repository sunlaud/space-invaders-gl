package com.xdev.si.entity

import javax.media.opengl.GL
import com.xdev.engine.sprite.Sprite
import collection.mutable.HashMap
import com.xdev.engine.animation.FrameAnimation
import java.awt.Rectangle
import org.openmali.vecmath2.Tuple2f

/**
 * Created by User: xdev
 * Date: 24.08.2010
 * Time: 21:56:53
 */
abstract class AbstractEntity (sprite : Sprite, cx: Float, cy: Float){
  //Coordinates
  val position: Tuple2f = new Tuple2f(0.0f, 0.0f)
  val size: Tuple2f = new Tuple2f(0.0f, 0.0f)
  val velocity: Tuple2f = new Tuple2f(0.0f, 0.0f)
  
  var x: Float = cx
  var y: Float = cy
  val width = sprite.getWidth()
  val height = sprite.getHeight()
  //Velocity
  protected var vx: Float = 0.0f
  protected var vy: Float = 0.0f
  //Bounding boxes
  private val thisBoundBox : Rectangle  = new Rectangle(cx.asInstanceOf[Int], cy.asInstanceOf[Int], width, height)
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
  protected def init(): Unit
  /**
   * Move  entity
   */
  def move(delta: Long){
    x = x + (vx * delta) / 1000.0f
    y = y + (vy * delta) / 1000.0f
    thisBoundBox.x = x.asInstanceOf[Int]
    thisBoundBox.y = y.asInstanceOf[Int]
  }

  def accelerate(dx: Float, dy: Float){
    vx += dx
    vy += dy
  }

  def stop(){
    vx = 0
    vy = 0
  }

  def draw(gl: GL) = sprite.draw(gl, x, y)
  
  def collidesWith(target : AbstractEntity): Boolean ={
    thisBoundBox.setBounds(x.asInstanceOf[Int],
      y.asInstanceOf[Int],
      width,
      height)
    targetBoundBox.setBounds(target.x.asInstanceOf[Int],
      target.y.asInstanceOf[Int],
      target.width,
      target.height)
    return thisBoundBox.intersects(targetBoundBox)
  }

  def collidedWith(target: AbstractEntity): Unit
  def doLogic():Unit
  def notifyDead(): Unit
  def update(delta: Long): Unit
  
  def addFrameAnimation(animation: FrameAnimation){
     frameAnimations.put(animation.id, animation)
  }
}
