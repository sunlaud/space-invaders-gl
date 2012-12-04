package com.xdev.si.gllisteners

import com.xdev.engine.gl.render.GLEventListener2D
import javax.media.opengl.GL2

import java.awt.Font
import com.xdev.si.Game
import com.jogamp.opengl.util.awt.TextRenderer

/**
 * User: Andrey Klochkov
 * Date: 25.11.2010
 * Time: 21:52:23
 */

object DebugRenderer extends GLEventListener2D{

  var isDebuggerInfoRendered = false
  var textBuffer: Array[String] = Array[String]()
  var textRenderer: TextRenderer = null

  def setDisplayMode(isDisplayed: Boolean){
    isDebuggerInfoRendered = isDisplayed
  }

  def isDebugEnabled: Boolean = isDebuggerInfoRendered

  def show(){
    setDisplayMode(true)
  }

  def hide(){
    setDisplayMode(false)
  }

  def setTextForDebugging(textLines: Array[String]){
    textBuffer = textLines
  }

  override def onUpdateFrame(delta: Long, w: Int, h: Int) {}
  
  override def onRenderFrame(gl: GL2, w: Int, h: Int) {
    if(isDebuggerInfoRendered && textBuffer.length > 0){
      textRenderer.beginRendering(w, h)
      textRenderer.setColor(1f, 1f, 1f, 1f)
      var y = Game.WND_HEIGHT - 50
      for(line <- textBuffer){
        textRenderer.draw(line, 10, y)
        y -= 10
      }
      textRenderer.endRendering()
    }
  }

  override def onInit(gl: GL2) {
    textRenderer = new TextRenderer(new Font("Default", Font.PLAIN, 9))    
  }

}