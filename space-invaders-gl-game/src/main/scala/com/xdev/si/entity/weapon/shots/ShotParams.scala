package com.xdev.si.entity.weapon.shots


/**
 * DTO to hold basic shot params.
 *
 * @author sunlaud
 */
class ShotParams(damg: Float, durablty: Float, velocty: Float) {

  var damage: Float = damg
  var durability: Float = durablty
  var velocity: Float = velocty

  def this(proto: ShotParams) = this(proto.damage, proto.durability, proto.velocity)

  override def toString: String = {
    "ShotParams[damage=" + damage + ", durability=" + durability + ", velocity=" + velocity + "]"
  }

}



