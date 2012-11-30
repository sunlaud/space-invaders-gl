package com.xdev.si.gllisteners

import com.xdev.engine.logging.LogHelper
import javax.media.opengl.GL2
import java.awt.Font
import com.xdev.engine.gl.render.GLEventListener2D
import com.xdev.si.Game
import com.jogamp.opengl.util.awt.TextRenderer

/**
 * Created by User: xdev
 * Date: 27.08.2010
 * Time: 1:19:36
 */

object HudRenderer extends GLEventListener2D with LogHelper {

  var textRenderer = new TextRenderer(new Font("Default", Font.PLAIN, 12))

  override def onInit(gl: GL2) { }

  override def onUpdateFrame(delta: Long, w: Int, h: Int) {}

  override def onRenderFrame(gl: GL2, w: Int, h: Int) {
    textRenderer.beginRendering(w, h)
    textRenderer.setColor(0f, 1f, 0f, 1f)
    textRenderer.draw("level : %d score : %d".format(Game.CURRENT_LEVEL, Game.SCORE), 10, Game.WND_HEIGHT - 25)
    textRenderer.endRendering()
  }
}