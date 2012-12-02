package com.xdev.engine.test

import org.specs2.mutable._
import com.xdev.engine.collisions.{QuadTreeNode, QuadTreeBuilder}
import org.openmali.vecmath2.{Point3f, Vector3f}


class QuadTreeTest extends SpecificationWithJUnit{
   "Quad tree " should {
     "Build " in {
       val nodes: List[QuadTreeNode] = List(
         new QuadTreeNode(new Point3f(10, 10, 0), 10),
         new QuadTreeNode(new Point3f(20, 20, 0), 10),
         new QuadTreeNode(new Point3f(30, 30, 0), 10))

       val tree = new QuadTreeBuilder[QuadTreeNode]().build(nodes, new Vector3f(50,50,0), 100, 100)
       val root = tree.getRootCell()

       root.getNumNodes must beEqualTo(3)

       val shot = new QuadTreeNode(new Point3f(10, 10, 0), 10)

     }
   }
}
