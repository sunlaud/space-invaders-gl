package com.xdev.engine.util

import org.openmali.vecmath2._
import org.openmali.spatial.bounds.BoundingBox
import org.openmali.spatial.bodies.Box
import javax.media.opengl.{GL, GLContext}

object BoundingBoxUtils {
  def create(center: Point3f , size: Tuple3f): BoundingBox = {
    return new BoundingBox(center.x() - size.x / 2f,
      center.y() - size.y / 2f,
      center.z() - size.z / 2f,
      center.x() + size.x / 2f,
      center.y() + size.y / 2f,
      center.z() + size.z / 2f);
  }

  def createBox(center: Point3f, size: Float): Box ={
    return new Box(center.x() - size / 2f,
      center.y() - size / 2f,
      center.z() - size / 2f,
      center.x() + size / 2f,
      center.y() + size / 2f,
      center.z() + size / 2f);
  }

  def createBox(center: Point3f, size: Tuple3f): Box ={
    return new Box(center.x() - size.x / 2f,
      center.y() - size.y / 2f,
      center.z() - size.z / 2f,
      center.x() + size.x / 2f,
      center.y() + size.y / 2f,
      center.z() + size.z / 2f);
  }
  def render(box: Box) {
    render(box.getUpper, box.getLower)
  }
  def render(upper: Tuple3f, lower: Tuple3f) {
    val gl = GLContext.getCurrentGL.getGL2
    import gl._
    import GL._
    val Z = 0.f
    glDisable(GL_TEXTURE_2D)
    glDisable(GL_BLEND)
    glLineWidth(0.1f)
    glPushMatrix()
    glTranslatef(0, 0, 0)
    glBegin(GL_LINE_STRIP)
      glColor3f(0f, 1f, 0f)
      glVertex3f(lower.x(), lower.y(), Z)
      glVertex3f(upper.x(), lower.y(), Z)
      glVertex3f(upper.x(), upper.y(), Z)
      glVertex3f(lower.x(), upper.y(), Z)
      glVertex3f(lower.x(), lower.y(), Z)
      glVertex3f(lower.x(), lower.y(), Z)
      glVertex3f(lower.x(), upper.y(), Z)
      glVertex3f(lower.x(), upper.y(), Z)
      glVertex3f(upper.x(), upper.y(), Z)
      glVertex3f(upper.x(), upper.y(), Z)
      glVertex3f(upper.x(), upper.y(), Z)
      glVertex3f(upper.x(), upper.y(), Z)
      glVertex3f(lower.x(), upper.y(), Z)
      glVertex3f(lower.x(), lower.y(), Z)
      glVertex3f(upper.x(), lower.y(), Z)
      glVertex3f(upper.x(), lower.y(), Z)
      glVertex3f(upper.x(), upper.y(), Z)
      glVertex3f(upper.x(), upper.y(), Z)
      glVertex3f(upper.x(), lower.y(), Z)
    glEnd()
    glPopMatrix()
    glEnable(GL_TEXTURE_2D)
    glEnable(GL_BLEND)
    glColor3f(1f, 1f, 1f)
  }
}
