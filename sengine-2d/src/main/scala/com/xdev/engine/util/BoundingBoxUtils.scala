package com.xdev.engine.util

import org.openmali.vecmath2.Point3f
import org.openmali.spatial.bounds.BoundingBox
<<<<<<< HEAD
import org.openmali.spatial.bodies.Box
=======
>>>>>>> ad6b24c04a497255ce5a68b984965a5f11600b5b

/**
 * Created with IntelliJ IDEA.
 * User: vika
 * Date: 02.12.12
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
object BoundingBoxUtils {
  def create(center: Point3f , size: Float): BoundingBox = {
    return new BoundingBox(center.x() - size / 2f,
      center.y() - size / 2f,
      center.z() - size / 2f,
      center.x() + size / 2f,
      center.y() + size / 2f,
      center.z() + size / 2f);
  }

<<<<<<< HEAD
  def createBox(center: Point3f, size: Float): Box ={
    return new Box(center.x() - size / 2f,
      center.y() - size / 2f,
      center.z() - size / 2f,
      center.x() + size / 2f,
      center.y() + size / 2f,
      center.z() + size / 2f);
  }

=======
>>>>>>> ad6b24c04a497255ce5a68b984965a5f11600b5b
}
