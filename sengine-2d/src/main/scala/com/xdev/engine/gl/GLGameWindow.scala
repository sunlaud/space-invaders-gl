package com.xdev.engine.gl

import com.xdev.engine.input.Keyboard
import java.awt.{Dimension, BorderLayout, Frame}
import javax.media.opengl._
import awt.{GLJPanel, GLCanvas}
import java.awt.event.{WindowEvent, WindowAdapter}
import com.xdev.engine.logging.LogHelper
import render.GLEventListener2D
import com.jogamp.opengl.util.Animator
import javax.media.opengl.GL._

// GL constants

import javax.media.opengl.GL2._
import javax.media.opengl.fixedfunc.GLMatrixFunc._

/**
 * Created by User: xdev
 * Date: 26.08.2010
 * Time: 1:17:26
 */

abstract class GLGameWindow(title: String, w: Int, h: Int) extends LogHelper {
  private var canvas: GLJPanel = null
  //Game loop animator
  private var animator: Animator = null
  private val frame = new Frame(title)

  /**
   * Start the rendering process. This method will cause the
   * display to redraw as fast as possible.
   */
  private def init(glCaps: GLCapabilities, gameRenderers: Array[GLEventListener2D]): Unit = {
    if (gameRenderers.isEmpty) throw new IllegalStateException("No GLEventListeners found")
    canvas = new GLJPanel(glCaps)
    canvas.setPreferredSize(new Dimension(w, h))
    //Add Root Renderer
    canvas.addGLEventListener(new RootRenderer())
    //Add all gameRenderers
    gameRenderers.foreach(canvas.addGLEventListener(_))
    canvas.setIgnoreRepaint(true)
    canvas.setFocusable(true)
    //Init keyboard and animator loop
    Keyboard.init(canvas)
    animator = new Animator(canvas)
    animator.setRunAsFastAsPossible(true)
    animator.start()

    // Setup the canvas inside the main window
    frame.setLayout(new BorderLayout())
    frame.add(canvas)
    frame.setResizable(false)
    frame.pack()
    frame.setLocationRelativeTo(null)
    // add a listener to respond to the user closing the window. If they
    // do we'd like to exit the game
    frame.addWindowListener(new WindowAdapter() {
      override def windowClosing(e: WindowEvent) {
        new Thread() {
          override def run() {
            closeWindow()
          }
        }.start
      }
    })
  }

  def showWindow() {
    init(new GLCapabilities(GLProfile.getDefault), initGameRenders())
    while (!animator.isAnimating()) {}
    frame.setVisible(true)
    canvas.requestFocus()
  }

  def closeWindow() {
    animator.stop()
    frame.dispose()
    System.exit(0)
  }

  def initGameResources(gl: GL2): Unit

  def initGameRenders(): Array[GLEventListener2D]

  //================================================================================
  private class RootRenderer extends GLEventListener {
    /**
     * Called by the JOGL rendering process at initialisation. This method
     * is responsible for setting up the GL context.
     * @param drawable The GL context which is being initialised
     */
    def init(drawable: GLAutoDrawable): Unit = {
      // get hold of the GL content
      val gl = drawable.getGL().getGL2
      // enable textures since we're going to use these for our sprites
      // set the background colour of the display to black
      gl.glClearColor(0, 0, 0, 0)
      // set the area being rendered
      gl.glViewport(0, 0, drawable.getWidth(), drawable.getHeight())
      // disable the OpenGL depth test since we're rendering 2D graphics
      gl.glDisable(GL_DEPTH_TEST)
      gl.glEnable(GL_BLEND)
      gl.glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA)
      initGameResources(gl)
    }

    /**
     * Called by the JOGL rendering process to display a frame. In this
     * case its responsible for blanking the display and then notifing
     * any registered callback that the screen requires rendering.
     * @param drawable The GL context component being drawn
     */
    def display(drawable: GLAutoDrawable): Unit = {
      // get hold of the GL content
      val gl = drawable.getGL().getGL2
      // clear the screen and setup for rendering
      gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT)

      gl.glMatrixMode(GL_MODELVIEW)
      gl.glLoadIdentity()
      // flush the graphics commands to the card
      gl.glFlush()
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
    def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int): Unit = {
      val gl = drawable.getGL().getGL2
      // at reshape we'e'd like to
      // treat the screen on a pixel by pixel basis by telling
      // it to use Orthographic projection.
      gl.glMatrixMode(GL_PROJECTION)
      gl.glLoadIdentity()
      gl.glOrtho(0, width, height, 0, -1, 1)
    }

    /**
     * Called by the JOGL rendering process if/when the display mode
     * is changed.
     * @param drawable The GL context which has changed
     * @param modeChanged True if the display mode has changed
     * @param deviceChanged True if the device in use has changed
     */
    def displayChanged(drawable: GLAutoDrawable, modeChanged: Boolean, deviceChanged: Boolean): Unit = {}

    def dispose(drawable: GLAutoDrawable) {}
  }

}