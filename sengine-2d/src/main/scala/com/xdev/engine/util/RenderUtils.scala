package com.xdev.engine.util

import javax.media.opengl.{GL, GLContext, GL2}
import java.awt.Rectangle
import org.openmali.vecmath2.{Tuple3f, Vector3f}

/**
 * User: xdev.developer@gmail.com
 * Date: 04.12.12
 * Time: 21:21
 */
object RenderUtils {

  def drawRect(gl: GL2, box: Rectangle, color: Tuple3f = new Tuple3f(0f, 1f, 0f)){
    val gl = GLContext.getCurrentGL.getGL2
    import gl._
    import GL._
    glDisable(GL_TEXTURE_2D)
    glDisable(GL_BLEND)
    glLineWidth(0.1f)
    glPushMatrix()
      glTranslatef(0, 0, 0)
      glBegin(GL_LINE_STRIP)
        glColor3f(color.x, color.y, color.z)
        glVertex2f(box.x, box.y)
        glVertex2f(box.x + box.width, box.y)
        glVertex2f(box.x+ box.width, box.y + box.height)
        glVertex2f(box.x, box.y + box.height)
        glVertex2f(box.x, box.y + box.height)
        glVertex2f(box.x, box.y)
      glEnd()
    glPopMatrix()
    glEnable(GL_TEXTURE_2D)
    glEnable(GL_BLEND)
    glColor3f(1f, 1f, 1f)
  }
}
