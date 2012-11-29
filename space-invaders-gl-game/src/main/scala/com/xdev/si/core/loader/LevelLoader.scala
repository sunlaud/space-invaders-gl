package com.xdev.si.core.loader

/**
 * User: xdev.developer@gmail.com
 * Date: 29.11.12
 * Time: 21:53
 */
object LevelLoader {
   def load(path: String): Array[Array[Int]] = {
     for (line <- scala.io.Source.fromFile(path).getLines()){

     }
   }
}
