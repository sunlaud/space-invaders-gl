package com.xdev.si.core.loader

import com.xdev.engine.logging.LogHelper
import com.xdev.engine.util.ResourceRetriever

/**
 * User: xdev.developer@gmail.com
 * Date: 29.11.12
 * Time: 21:53
 */
object LevelLoader extends LogHelper {

   def load(path: String): Array[Array[Int]] = {
     val lines = scala.io.Source.fromInputStream(ResourceRetriever.getResourceAsStream(path)).getLines()
     var i = 0
     val map: Array[Array[Int]] = Array.fill[Int](lines.size, 1)(0)
     for (line <- scala.io.Source.fromFile(path).getLines()){
       debug(line)
       debug(i)
       line.map(c => if (c.isDigit) c.asDigit else 0).zipWithIndex.foreach(z => map(i)(z._1) = z._2)
       i += 1
     }
     debug(map)
     map
   }
}
