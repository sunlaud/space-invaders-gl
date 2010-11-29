package com.xdev.si.gllisteners

import com.xdev.engine.logging.LogHelper
import javax.media.opengl.GL
import com.xdev.si.Game
import com.xdev.engine.gl.render.GLEventListener2D
import com.xdev.engine.sprite.Sprite
import org.openmali.vecmath2.Vector3f
import com.xdev.engine.util.ResourceFactory

/**
 * Created by User: xdev
 * Date: 27.08.2010
 * Time: 1:50:10
 */

class BackgroundRenderer extends GLEventListener2D with LogHelper {
  var background: Sprite = null
  val position: Vector3f = new Vector3f(0.0f, 0.0f, 0.0f)
  def onInit(gl: GL): Unit = {
    background = ResourceFactory.getSprite(Game.BACKGROUND_SPRITE)

  }

  def onUpdateFrame(delta: Long, w: Int, h: Int): Unit = {}
  
  def onRenderFrame(gl: GL, w: Int, h: Int): Unit = {
     background.draw(gl, position)
  }
}
