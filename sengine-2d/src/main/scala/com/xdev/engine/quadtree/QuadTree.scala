package com.xdev.engine.quadtree

import java.awt.Rectangle
import javax.media.opengl.GL2
import com.xdev.engine.util.RenderUtils

/**
 * User: xdev.developer@gmail.com
 * Date: 04.12.12
 * Time: 21:15
 */
class QuadTree[T <: TreeNode](width: Int, height: Int) {
  val rootCell: QuadTreeCell[T] = new QuadTreeCell[T](new Rectangle(width, height))

  def render(gl: GL2){
    RenderUtils.drawRect(gl, rootCell.bounds)
  }
}
