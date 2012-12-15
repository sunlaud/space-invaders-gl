package com.xdev.engine.quadtree

import java.awt.Rectangle

/**
 * User: xdev.developer@gmail.com
 * Date: 04.12.12
 * Time: 21:16
 */
trait TreeNode {
  val bounds: Rectangle
  private var _treeCell: Option[Any] = None
  def treeCell = _treeCell
  def treeCell_=(value: Any) = _treeCell = Some(value)

}
