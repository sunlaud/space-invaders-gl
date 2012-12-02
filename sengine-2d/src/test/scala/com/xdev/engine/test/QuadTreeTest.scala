package com.xdev.engine.test

import org.specs2.mutable._
import com.xdev.engine.collisions.{QuadTreeNode, QuadTreeBuilder}
import org.openmali.vecmath2.{Point3f, Vector3f}
<<<<<<< HEAD
import com.xdev.engine.util.BoundingBoxUtils
import org.openmali.spatial.bodies.Classifier


class QuadTreeTest extends SpecificationWithJUnit{
   "Quad tree" should {
     "point 10,10 must be inside of tree " in {
=======


class QuadTreeTest extends SpecificationWithJUnit{
   "Quad tree " should {
     "Build " in {
>>>>>>> ad6b24c04a497255ce5a68b984965a5f11600b5b
       val nodes: List[QuadTreeNode] = List(
         new QuadTreeNode(new Point3f(10, 10, 0), 10),
         new QuadTreeNode(new Point3f(20, 20, 0), 10),
         new QuadTreeNode(new Point3f(30, 30, 0), 10))

       val tree = new QuadTreeBuilder[QuadTreeNode]().build(nodes, new Vector3f(50,50,0), 100, 100)
       val root = tree.getRootCell()
<<<<<<< HEAD
       val shot = new QuadTreeNode(new Point3f(10, 10, 0), 10)
       "INSIDE" must equalTo(tree.getClassifier(BoundingBoxUtils.createBox(shot.center, shot.size), root).toString)
     }

     "point 120,120 must be outside of tree  " in {
       val nodes: List[QuadTreeNode] = List(
         new QuadTreeNode(new Point3f(10, 10, 0), 10),
         new QuadTreeNode(new Point3f(20, 20, 0), 10),
         new QuadTreeNode(new Point3f(30, 30, 0), 10))

       val tree = new QuadTreeBuilder[QuadTreeNode]().build(nodes, new Vector3f(50,50,0), 100, 100)
       val root = tree.getRootCell()
       "OUTSIDE" must equalTo(tree.getClassifier(BoundingBoxUtils.createBox(new Point3f(120, 120, 0), 10), root).toString)
=======

       root.getNumNodes must beEqualTo(3)

       val shot = new QuadTreeNode(new Point3f(10, 10, 0), 10)

>>>>>>> ad6b24c04a497255ce5a68b984965a5f11600b5b
     }
   }
}
