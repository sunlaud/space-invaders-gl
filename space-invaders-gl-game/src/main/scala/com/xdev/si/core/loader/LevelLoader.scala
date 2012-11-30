package com.xdev.si.core.loader

import com.xdev.engine.logging.LogHelper
import com.xdev.engine.util.ResourceRetriever

/**
 * User: xdev.developer@gmail.com
 * Date: 29.11.12
 * Time: 21:53
 */
class LevelLoader extends LogHelper {
   def load(path: String): Array[Array[Int]] = {
     val lines = scala.io.Source.fromInputStream(ResourceRetriever.getResourceAsStream(path)).getLines().toList
     debug("Original map")
     debug(lines.mkString("\n"))
     var i = 0
     val map: Array[Array[Int]] = Array.fill[Int](lines.size, lines.map(_.length).max)(0)
     for(line <- lines){
       line.map(c => if (c.isDigit) c.asDigit else 0).zipWithIndex.foreach(z => map(i)(z._2) = z._1)
       i += 1
     }
     debug("Result map")
     map.foreach(a => debug(a.mkString))
     map
   }
}
