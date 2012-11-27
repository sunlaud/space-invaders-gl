package com.xdev.si.entity.enemy

import com.xdev.engine.sprite.Sprite
import org.openmali.vecmath2.Vector3f
import com.xdev.si.entity.AbstractEntity

/**
 * Created by User: xdev.developer@gmail.com
 * Date: 25.08.2010
 * Time: 0:06:32
 */
abstract class EnemyEntity(sprite: Sprite, pos: Vector3f) extends AbstractEntity(sprite, pos) {
  /**
   * Notifies when player kill friend enemy
   */
  def onFriendEnemyKilled()
}
