package com.xdev.si.entity.weapon

import com.xdev.si.gllisteners.MainRenderLoop
import com.xdev.engine.sprite.Sprite
import org.openmali.vecmath2.Vector3f
import com.xdev.si.entity.AbstractEntity

/**
 * Created by User: xdev
 * Date: 25.08.2010
 * Time: 0:07:51
 */
class ShotEntity(sprite : Sprite,  pos: Vector3f) extends AbstractEntity(sprite, pos){

  private var used  = false

  override def init(){
    velocity.setY(-350)//Start shot velocity
  }

  override def move(delta: Long){
    super.move(delta)
    if (position.getY() < 0) notifyDead()
  }

  override def collidedWith(target: AbstractEntity): Unit = {
    if (used) {return}
    this.notifyDead()
    target.notifyDead()
    used = true
  }

  override def doLogic():Unit= {}
  override def update(delta: Long){}

  override def toString = "ShotEntity[" + position + "]"
}
