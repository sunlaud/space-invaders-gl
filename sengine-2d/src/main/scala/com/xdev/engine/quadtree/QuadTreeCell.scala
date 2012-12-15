package com.xdev.engine.quadtree

import java.awt.Rectangle
import collection.mutable.ArrayBuffer

/**
 * User: xdev.developer@gmail.com
 * Date: 04.12.12
 * Time: 21:15
 */
class QuadTreeCell[T <: TreeNode](bounds: Rectangle) {
  private var leftOpt: Option[QuadTreeCell[T]] = None
  private var rightOpt: Option[QuadTreeCell[T]] = None
  private var frontOpt: Option[QuadTreeCell[T]] = None
  private var backOpt: Option[QuadTreeCell[T]] = None
  private var level = 0
  val halfWidth = bounds.width / 2
  val halfHeight = bounds.height / 2

  var nodes = new ArrayBuffer[T]()

  private var hasChildCells = false

  def insertNode(node: T, maxNodesInCell: Int): Int = {
    findCell(node, maxNodesInCell) match {
      case Some(cell) => {
        node.treeCell = cell
        cell._insertNode(node)
        cell.level
      }
      case None => -1
    }
  }

  def removeNode(node: T){
    nodes -= node
  }

  def clear(){
    nodes.clear()
    leftOpt = None
    rightOpt = None
    frontOpt = None
    backOpt = None
    level = 0
  }

  private def findCell(node: T, maxNodesInCell: Int): Option[QuadTreeCell[T]] = {
    if (level > 0 && isIntersects(this, node) && getNodesCount() < maxNodesInCell){
      return Some(this)
    }
    //Left
    val leftCell = leftOpt.getOrElse(new QuadTreeCell[T](new Rectangle(this.bounds.x, this.bounds.y, halfWidth, this.bounds.height)))
    if (isIntersects(leftCell, node)){
      leftCell.level = level + 1
      leftOpt = Some(leftCell)
      hasChildCells = true
      leftCell.findCell(node, maxNodesInCell) match {
        case Some(cell) => return Some(cell)
        case None =>
      }
    }

    //Right
    val rightCell = rightOpt.getOrElse(new QuadTreeCell[T](new Rectangle(halfWidth, this.bounds.y, this.halfWidth, this.bounds.height)))
    if (isIntersects(rightCell, node)){
      rightCell.level = level + 1
      rightOpt = Some(rightCell)
      hasChildCells = true
      rightCell.findCell(node, maxNodesInCell) match {
        case Some(cell) => return Some(cell)
        case None =>
      }
    }
    //Front
    val frontCell = frontOpt.getOrElse(new QuadTreeCell[T](new Rectangle(this.bounds.x, halfHeight, this.bounds.width, this.halfHeight)))
    if (isIntersects(frontCell, node)){
      frontCell.level = level + 1
      frontOpt = Some(frontCell)
      hasChildCells = true
      frontCell.findCell(node, maxNodesInCell) match {
        case Some(cell) => return Some(cell)
        case None =>
      }
    }
    //Back
    val backCell = backOpt.getOrElse(new QuadTreeCell[T](new Rectangle(this.bounds.x, this.bounds.y, this.bounds.width, this.halfHeight)))
    if (isIntersects(backCell, node)){
      backCell.level = level + 1
      backOpt = Some(backCell)
      hasChildCells = true
      backCell.findCell(node, maxNodesInCell) match {
        case Some(cell) => return Some(cell)
        case None =>
      }
    }
    None
  }

  private def _insertNode(node: T) {
      nodes += node
  }

  def isIntersects(cell: QuadTreeCell[T], node: TreeNode): Boolean = {
    cell.getBounds().intersects(node.bounds)
  }

  def getLeft(): Option[QuadTreeCell[T]] = leftOpt
  def getRight(): Option[QuadTreeCell[T]] = rightOpt
  def getFront(): Option[QuadTreeCell[T]] = frontOpt
  def getBack(): Option[QuadTreeCell[T]] = backOpt
  def getNodesCount(): Int = nodes.length
  def isHashChildCells(): Boolean = hasChildCells
  def getLevel(): Int = level
  def getBounds(): Rectangle = bounds

}
