package com.xdev.engine.collisions

import org.openmali.spatial.quadtree.{QuadCell, QuadTree}
import org.openmali.vecmath2.Vector3f
import org.openmali.spatial.{SpatialNode, AxisIndicator}
import org.openmali.spatial.bodies.{Box, Classifier}
import org.openmali.spatial.bodies.Classifier.Classification

class Quadtree[T <: SpatialNode](center: Vector3f, w: Float, h: Float) extends QuadTree[T](center, AxisIndicator.POSITIVE_Z_AXIS, w, h, h, false) {

  def getClassifier(target: Box, node: QuadCell[T]): Classification  = {
    Classifier.classifyBoxBox(node, target)
  }
}
