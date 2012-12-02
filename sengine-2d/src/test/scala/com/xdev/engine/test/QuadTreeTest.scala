package com.xdev.engine.test

import org.specs2.mutable._
import com.xdev.engine.collisions.{QuadTreeNode, QuadTreeBuilder}
import org.openmali.vecmath2.{Point3f, Vector3f}
import com.xdev.engine.util.BoundingBoxUtils
import org.openmali.spatial.bodies.Classifier
import collection.mutable.ArrayBuffer


class QuadTreeTest extends SpecificationWithJUnit{
   "Quad tree cx50 cy50 100x100" should {
     "point cx10 cy10 must be inside of tree " in {
       val nodes: List[QuadTreeNode] = List(
         new QuadTreeNode(new Point3f(10, 10, 0), 10),
         new QuadTreeNode(new Point3f(20, 20, 0), 10),
         new QuadTreeNode(new Point3f(30, 30, 0), 10))

       val tree = new QuadTreeBuilder[QuadTreeNode]().build(nodes, new Vector3f(50,50,0), 100, 100)
       val root = tree.getRootCell()
       val shot = new QuadTreeNode(new Point3f(10, 10, 0), 10)
       "INSIDE" must equalTo(tree.getClassifier(BoundingBoxUtils.createBox(shot.center, shot.size), root).toString)
     }

     "point cx120 cy120 must be outside of tree  " in {
       val nodes: List[QuadTreeNode] = List(
         new QuadTreeNode(new Point3f(10, 10, 0), 10),
         new QuadTreeNode(new Point3f(20, 20, 0), 10),
         new QuadTreeNode(new Point3f(30, 30, 0), 10))

       val tree = new QuadTreeBuilder[QuadTreeNode]().build(nodes, new Vector3f(50,50,0), 100, 100)
       val root = tree.getRootCell()
       "OUTSIDE" must equalTo(tree.getClassifier(BoundingBoxUtils.createBox(new Point3f(120, 120, 0), 10), root).toString)
     }

     "5x20 nodes" in {
       val nodes = new ArrayBuffer[QuadTreeNode]()
       for(y <- 0 until 10) {
         for(x <- 0 until 10) {
           nodes += new QuadTreeNode(new Point3f((x * 15), (y * 15), 0.0f), 30)
         }
       }
       val tree = new QuadTreeBuilder[QuadTreeNode]().build(nodes.toList, new Vector3f(100,100,0), 200, 200)
       val root = tree.getRootCell()
       val box = BoundingBoxUtils.createBox(new Point3f(15, 15, 0), 10)
       "INSIDE" must equalTo(tree.getClassifier(box, root).toString)

       println("Found nodes: " + tree.getIntersectNodes(BoundingBoxUtils.createBox(new Point3f(15, 15, 0), 20)).size)
     }
   }
}
