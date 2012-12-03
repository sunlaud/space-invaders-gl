package com.xdev.engine.collisions

import org.openmali.spatial.quadtree.{QuadCell, QuadTree}
import org.openmali.vecmath2.Vector3f
import org.openmali.spatial.{SpatialNode, AxisIndicator}
import org.openmali.spatial.bodies.{Box, Classifier}
import org.openmali.spatial.bodies.Classifier.Classification
import collection.mutable.ArrayBuffer
import com.xdev.engine.util._

class Quadtree[T <: SpatialNode](center: Vector3f, w: Float, h: Float) extends QuadTree[T](center, AxisIndicator.POSITIVE_Z_AXIS, w, h, false) {

  def getClassifier(target: Box, node: QuadCell[T]): Classification  = {
    Classifier.classifyBoxBox(node, target)
  }

  def getIntersectNodes(target: Box): List[T] = {
    val nodes = new ArrayBuffer[T]()
    val root = getRootCell
    findNodes(target, root, nodes)
    nodes.toList
  }

  def render(){
    recRender(getRootCell)

    def recRender(cell: QuadCell[T]){
      BoundingBoxUtils.render(cell.getUpper, cell.getLower)
      if (cell.hasChildCells){
        if (cell.getCellHBack != null){recRender(cell.getCellHBack)}
        if (cell.getCellHFront != null){recRender(cell.getCellHFront)}
        if (cell.getCellHLeft != null){recRender(cell.getCellHLeft)}
        if (cell.getCellHRight != null){recRender(cell.getCellHRight)}
        if (cell.getCellQuBackLeft != null){recRender(cell.getCellQuBackLeft)}
        if (cell.getCellQuBackRight != null){recRender(cell.getCellQuBackRight)}
        if (cell.getCellQuFrontLeft != null){recRender(cell.getCellQuFrontLeft)}
        if (cell.getCellQuFrontRight != null){recRender(cell.getCellQuFrontRight)}        
      }
    }
  }

  private def findNodes(target: Box, cell: QuadCell[T], nodesHolder: ArrayBuffer[T]){
    val classifier = getClassifier(target, cell)
   // println("%s[%s] -> %s[%s] - %s".format(target.getCenter(new Vector3f()), target.getSize, cell.getCenter(new Vector3f()), cell.getSize, classifier))
    if (classifier == Classification.OUTSIDE){
       return
    }

    for (i <- 0 until cell.getNumNodes) {
      nodesHolder += cell.getNode(i)
    }

    if (cell.hasChildCells){
      if (cell.getCellHBack != null){
          findNodes(target, cell.getCellHBack, nodesHolder)
      }
      if (cell.getCellHFront != null){
        findNodes(target, cell.getCellHFront, nodesHolder)

      }
      if (cell.getCellHLeft != null){
        findNodes(target, cell.getCellHLeft, nodesHolder)

      }
      if (cell.getCellHRight != null){
        findNodes(target, cell.getCellHRight, nodesHolder)

      }
      if (cell.getCellQuBackLeft != null){
        findNodes(target, cell.getCellQuBackLeft, nodesHolder)

      }
      if (cell.getCellQuBackRight != null){
        findNodes(target, cell.getCellQuBackRight, nodesHolder)

      }
      if (cell.getCellQuFrontLeft != null){
        findNodes(target, cell.getCellQuFrontLeft, nodesHolder)

      }
      if (cell.getCellQuFrontRight != null){
        findNodes(target, cell.getCellQuFrontRight, nodesHolder)

      }
    }
  }
}
