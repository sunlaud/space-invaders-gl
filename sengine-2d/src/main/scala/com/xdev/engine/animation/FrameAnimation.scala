package com.xdev.engine.animation
import com.xdev.engine.sprite.Sprite
import javax.media.opengl.GL

/**
 * Frame animation 
 * Created by User: xdev
 * Date: 27.11.2010
 * Time: 01:09:53
 */
class FrameAnimation (val id: Int, //Animation id
                      val frames: Array[Sprite], //Frames array
                      val duration: Int = 1000,  //Animation loop duration
                      val looped: Boolean = false,//Is animation looped
                      onAnimationEndedHook: => Unit = {}){//Function to execute on animation loop ended

  var lastFrameChange: Long = 0
  var currentFrameNumber: Int = 0
  val framesCount: Int = frames.length
  val oneFrameDuration = duration / frames.length
  var run: Boolean = false
  
  def computeNextFrame(delta:Long): Int ={
    if(!isRunning){return 0}
    lastFrameChange += delta
    if(lastFrameChange >= oneFrameDuration){
      lastFrameChange = 0
      currentFrameNumber += 1
      if (currentFrameNumber >= framesCount) {
        onAnimationEndedHook
        if(looped){
          restart()
        }else stop()
      }
    }
    return currentFrameNumber
  }

  def render(gl: GL, x: Float, y: Float): Unit = {
    if(!isRunning)return
    frames(currentFrameNumber).draw(gl, x, y)    
  }

  def isRunning() = run
  def start() = restart()
  def stop() = run = false
  def restart() = {
    lastFrameChange = 0
    currentFrameNumber = 0
    run = true
  }
}
