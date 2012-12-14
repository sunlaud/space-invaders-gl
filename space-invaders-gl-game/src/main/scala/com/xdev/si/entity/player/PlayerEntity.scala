package com.xdev.si.entity.player

import com.xdev.si.Game
import com.xdev.engine.sprite.Sprite
import javax.media.opengl.GL2
import org.openmali.vecmath2.Vector3f
import com.xdev.si.entity.AbstractEntity
import com.xdev.si.entity.weapon.{LaserWeapon, ShotgunWeapon, RocketWeapon, FireBallWeapon, AbstractWeapon}
import collection.mutable

/**
 * Created by User: xdev.developer@gmail.com
 * Date: 24.08.2010
 * Time: 23:55:17
 */
object PlayerEntity{
  val MAX_ACCELERATION = 500
  val MIN_FIRE_INTERVAL = 100
}

final case class PlayerEntity(sprite : Sprite, pos: Vector3f) extends AbstractEntity(sprite, pos){
  private var playerWeapon: AbstractWeapon = new LaserWeapon(pos)
  var acceleration = 250

  val weaponStack = mutable.HashMap[String, AbstractWeapon]()

  def weapon(w: AbstractWeapon) { this.playerWeapon = w}
  def weapon = this.playerWeapon

  override def move(delta: Long){
    if ((velocity.getX < 0) && (position.getX <= 0)) position.setX(0.0f)
    if ((velocity.getX > 0) && (position.getX > Game.WND_WIDTH - width)) position.setX(Game.WND_WIDTH - width)
    super.move(delta)
    weapon.update(delta)
  }

  override def draw(gl: GL2) {
    super.draw(gl)
    weapon.draw(gl)
  }

  override def collidedWith(target: AbstractEntity) {
    target.notifyDead()
    this.notifyDead()
  }
  
  override def doLogic() {}
  
  override def update(delta: Long){}

  def fire(){
    weapon.fire()
  }

  def changeWeapon(weapon: AbstractWeapon) {
    if (weapon.name == playerWeapon.name) return
    //Save already fired projectiles and remove them from weapon being saved
    val shots = playerWeapon.shots
    playerWeapon.removeAllShots()
    //Save current Weapon
    weaponStack.put(playerWeapon.name, playerWeapon)
    //Get saved weapon from stack, if possible
    playerWeapon = weaponStack.get(weapon.name).getOrElse(weapon)
    playerWeapon.addAllShots(shots)
  }

}