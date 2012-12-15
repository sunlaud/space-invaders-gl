package com.xdev.engine.util

import java.net.{URI, URL}
import java.io.{FileInputStream, InputStream, IOException}
import com.xdev.engine.logging.LogHelper
import io.Source

/**
 * Created by User: xdev
 * Date: 26.08.2010
 * Time: 11:00:35
 */

object ResourceRetriever extends LogHelper{

  @throws(classOf[IOException])
  def getResource(filename: String) : URL = {
    debug("getResource : " + filename)
    val url: URL = getClass.getResource(filename)
    if(url == null) new URL("file", "localhost", filename) else url
  }

  @throws(classOf[IOException])
  def getResourceAsStream(filename: String) : InputStream = {
    val convertedFileName = filename.replace('\\', '/')
    val stream = getClass.getResourceAsStream(convertedFileName)
    debug(filename + " -> " + convertedFileName + " stream iexists : " + (stream != null))
    // If not found in jar, then load from disk
    if (stream == null) {
      debug("Trying to load from filesystem")
      new FileInputStream("." + convertedFileName)
    }else {
      stream
    }
  }

}
