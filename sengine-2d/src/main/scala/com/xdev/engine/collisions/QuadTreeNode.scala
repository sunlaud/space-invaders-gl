package com.xdev.engine.collisions

import org.openmali.spatial.SpatialNode
import org.openmali.spatial.bounds.Bounds
import org.openmali.vecmath2.Point3f
import com.xdev.engine.util.BoundingBoxUtils

class QuadTreeNode(val center: Point3f, val size: Float) extends SpatialNode {

  var cell: Any = null

  def getWorldBounds: Bounds = BoundingBoxUtils.create(center, size)

  def setTreeCell(treeCell: Any) {this.cell = treeCell}

  def getTreeCell: AnyRef = cell.asInstanceOf[AnyRef]
}
