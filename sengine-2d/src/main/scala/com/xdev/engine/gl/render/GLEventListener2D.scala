package com.xdev.engine.gl.render

import com.xdev.engine.util.SystemTimer
import javax.media.opengl.{GL2, GL, GLAutoDrawable, GLEventListener}
import javax.media.opengl.GL._
import com.xdev.engine.logging.LogHelper

/**
 * Created by User: xdev
 * Date: 26.08.2010
 * Time: 22:49:59
 */

abstract class GLEventListener2D extends GLEventListener with LogHelper{

  protected var delta : Long = 0
  /** The time at which the last rendering looped started from the point of view of the game logic */
  private var lastLoopTime: Long = SystemTimer.getTime
  /** The last time at which we recorded the frame rate */
  private var lastFpsTime : Long = 0
  /** The current number of frames recorded */
  protected var fps : Int = 0
  private var fpsAccumulator : Int = 0
  /**
   * Called by the JOGL rendering process at initialisation. This method
   * is responsible for setting up the GL context.
   * @param drawable The GL context which is being initialised
   */
  def init(drawable: GLAutoDrawable) {
    // get hold of the GL content
    onInit(drawable.getGL.getGL2)
  }

  def onInit(gl: GL2)

  /**
   * Called by the JOGL rendering process to display a frame. In this
   * case its responsible for blanking the display and then notifing
   * any registered callback that the screen requires rendering.
   * @param drawable The GL context component being drawn
   */
  def display(drawable: GLAutoDrawable) {
    calculateDelta()
    onUpdateFrame(delta, drawable.getWidth, drawable.getHeight)
    onRenderFrame(drawable.getGL.getGL2, drawable.getWidth, drawable.getHeight)
  }
  def onUpdateFrame(delta: Long, w: Int, h: Int)
  def onRenderFrame(gl: GL2, w: Int, h: Int)

  private def calculateDelta(){
    delta = SystemTimer.getTime - lastLoopTime
    lastLoopTime = SystemTimer.getTime
    // update the frame counter
    lastFpsTime += delta
    fpsAccumulator += 1
    // update our FPS counter if a second has passed since
    // we last recorded
    if (lastFpsTime >= 1000) {
      lastFpsTime = 0
      fps = fpsAccumulator
      fpsAccumulator = 0;
    }
  }
  /**
     * Called by the JOGL rendering process if and when the display is
     * resized.
     * @param drawable The GL content component being resized
     * @param x The new x location of the component
     * @param y The new y location of the component
     * @param width The width of the component
     * @param height The height of the component
     */
  def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {}
  /**
	 * Called by the JOGL rendering process if/when the display mode
	 * is changed.
	 * @param drawable The GL context which has changed
	 * @param modeChanged True if the display mode has changed
	 * @param deviceChanged True if the device in use has changed
	 */
  def displayChanged(drawable: GLAutoDrawable, modeChanged: Boolean, deviceChanged: Boolean) {}

  def dispose(drawable: GLAutoDrawable) {
  }
}