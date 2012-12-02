package com.xdev.engine.collisions

import org.openmali.spatial.SpatialNode
import org.openmali.spatial.bounds.Bounds
import org.openmali.vecmath2.Point3f
import com.xdev.engine.util.BoundingBoxUtils

<<<<<<< HEAD
class QuadTreeNode(val center: Point3f, val size: Float) extends SpatialNode {
=======
class QuadTreeNode(center: Point3f, size: Float) extends SpatialNode {
>>>>>>> ad6b24c04a497255ce5a68b984965a5f11600b5b

  var cell: Any = null

  def getWorldBounds: Bounds = BoundingBoxUtils.create(center, size)

  def setTreeCell(treeCell: Any) {this.cell = treeCell}

  def getTreeCell: AnyRef = cell.asInstanceOf[AnyRef]
}
