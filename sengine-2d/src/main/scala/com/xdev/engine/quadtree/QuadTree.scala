package com.xdev.engine.quadtree

import java.awt.Rectangle
import javax.media.opengl.GL2
import com.xdev.engine.util.RenderUtils
import collection.mutable.ArrayBuffer
import org.openmali.vecmath2.Tuple3f

/**
 * User: xdev.developer@gmail.com
 * Date: 04.12.12
 * Time: 21:15
 */
class QuadTree[T <: TreeNode](width: Int, height: Int, maxNodesInCell: Int = 4) {

  private val rootCell: QuadTreeCell[T] = new QuadTreeCell[T](new Rectangle(width, height))

  def insertNodes(nodes: Seq[T]){
    nodes.foreach(insertNode)
  }

  def insertNode(node: T): Int = {
    rootCell.insertNode(node, maxNodesInCell)
  }

  def updateNode(node: T){
    if (removeNode(node)){
      rootCell.insertNode(node, maxNodesInCell)
    }
  }

  def removeNode(node: T): Boolean = {
    node.treeCell match {
      case Some(cell) => {
        cell.asInstanceOf[QuadTreeCell[T]].removeNode(node)
        true
      }
      case _ => false
    }
  }

  def clear(){
    rootCell.clear()
  }

  def render(gl: GL2){
    import RenderUtils._

    def draw(gl: GL2, c: QuadTreeCell[T], color: Tuple3f){
      drawRect(gl, c.getBounds(), color)
      c.nodes.foreach(n => drawRect(gl, n.bounds, new Tuple3f(0f, 0f, 1f)))
    }

    def drawRecursive(gl: GL2, c: QuadTreeCell[T], color: Tuple3f){
      draw(gl, c, color)
      if (c.isHashChildCells()){
        c.getLeft().map(c => drawRecursive(gl, c, new Tuple3f(0, 1, 0)))
        c.getRight().map(c => drawRecursive(gl, c, new Tuple3f(0, 1, 0)))
        c.getFront().map(c => drawRecursive(gl, c, new Tuple3f(0, 1, 0)))
        c.getBack().map(c => drawRecursive(gl, c, new Tuple3f(0, 1, 0)))
      }
    }
    drawRecursive(gl, rootCell, new Tuple3f(0, 1, 1))
  }

  def dumpTree(){
    def dumpRecursive(name: String, cell : QuadTreeCell[T]){
      val margin = " " * (cell.getLevel() * 2)
      println(margin + "%s level: %s nodes: %s center: (%s, %s) size: (%s, %s)".format(name,
        cell.getLevel(),
        cell.getNodesCount(),
        cell.getBounds().getCenterX,
        cell.getBounds().getCenterY,
        cell.getBounds().width,
        cell.getBounds().height
      ))
      println(margin + "{")
      for (node <- cell.nodes){
        println((margin * 2) + "node xy:(%s, %s) size: (%s, %s)".format(node.bounds.x,
          node.bounds.y,
          node.bounds.width,
          node.bounds.height))
      }
      if (cell.isHashChildCells()){
        cell.getLeft().map(c => dumpRecursive("LEFT", c))
        cell.getRight().map(c => dumpRecursive("RIGHT", c))
        cell.getFront().map(c => dumpRecursive("FRONT", c))
        cell.getBack().map(c => dumpRecursive("BACK", c))
      }
      println(margin + "}")
    }
    dumpRecursive("ROOT", rootCell)
  }

  def getIntersectsNodes(target: TreeNode): Seq[T] = {

    def getNodesRecursive(cell: QuadTreeCell[T], target: TreeNode): ArrayBuffer[T] ={
      var holder = new ArrayBuffer[T]()
      if (cell.isIntersects(cell, target)){
        if (cell.isHashChildCells()){
          cell.getLeft().map(c => {
            holder ++= getNodesRecursive(c, target)
          })
          cell.getRight().map(c => {
            holder ++= getNodesRecursive(c, target)
          })
          cell.getFront().map(c => {
            holder ++= getNodesRecursive(c, target)
          })
          cell.getBack().map(c => {
            holder ++= getNodesRecursive(c, target)
          })
        }
        holder ++= cell.nodes
      }
      holder
    }
    return getNodesRecursive(rootCell, target)
  }

}
