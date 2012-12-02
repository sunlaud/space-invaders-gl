package com.xdev.engine.collisions
import org.openmali.spatial.quadtree._
import org.openmali.vecmath2.Vector3f
import org.openmali.spatial.{SpatialNode, AxisIndicator}

class QuadTreeBuilder[T <: SpatialNode] {

  def build(nodes: List[T], center: Vector3f, w: Float, h: Float): Quadtree[T] = {
    val tree = new Quadtree[T](center, w, h)
    nodes.foreach(tree.insertNode(_))
    tree
  }
}
