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
class QuadTree[T <: TreeNode](width: Int, height: Int) {

  val rootCell: QuadTreeCell[T] = new QuadTreeCell[T](new Rectangle(width, height))
  var maxLevel = 0
  var maxNodesInCell = 4

  def insertNodes(nodes: List[T]): Int = {
    val notInserted = new ArrayBuffer[T]()
    for (node <- nodes){
      val level = rootCell.insertNode(node, maxNodesInCell)
      if (level == -1){
        notInserted += node
      }
      maxLevel = maxLevel max level
    }

    notInserted.foreach(println)

    maxLevel
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
    println("Root cell. Nodes: %d".format(rootCell.nodes.length))
  }
}
