package com.xdev.si.entity.enemy

import javax.media.opengl.GL
import com.xdev.engine.sprite.Sprite
import com.xdev.engine.animation.FrameAnimation
import com.xdev.si.Game
import org.openmali.vecmath2.Vector3f
import com.xdev.si.entity.AbstractEntity
import com.xdev.si.gllisteners.MainRenderLoop

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
