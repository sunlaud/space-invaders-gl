package com.xdev.engine.util

import javax.media.opengl.{GLContext, GL}
import collection.mutable.HashMap
import javax.imageio.ImageIO
import com.xdev.engine.logging.LogHelper
import com.jogamp.opengl.util.texture.{TextureIO, Texture}
import javax.media.opengl.GL._

/**
 * Created by User: xdev
 * Date: 26.08.2010
 * Time: 2:16:50
 */

object TextureLoader extends LogHelper{
  /** The table of textures that have been loaded in this loader */
      private val table = new HashMap[String, Texture]()

      def getTexture(resourceName: String): Texture ={
          if (table.contains(resourceName)){
            return table(resourceName)
          }
        val gl = GLContext.getCurrentGL
          debug("Load texture " + resourceName)
          val texture: Texture = TextureIO.newTexture(ResourceRetriever.getResourceAsStream(resourceName), false, TextureIO.PNG)

          texture.setTexParameteri (gl, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
          // Use the NEAREST minification function when the pixel being
          // textured maps to an area greater than one texel.
          texture.setTexParameteri (gl, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
          debug("Texture loaded ")
          table.put(resourceName, texture)
          texture
      }
}
