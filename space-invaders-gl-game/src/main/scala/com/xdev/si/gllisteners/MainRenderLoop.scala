package com.xdev.si.gllisteners

import com.xdev.engine.logging.LogHelper
import com.xdev.engine.gl.render.GLEventListener2D
import javax.media.opengl.GL
import com.xdev.si.Game
import com.xdev.si.manager.GameManager
import com.xdev.si.entity.{AlienEntity, ShipEntity}
import collection.mutable.ArrayBuffer
import java.awt.event.KeyEvent
import com.xdev.engine.input.Keyboard

/**
 * Created by User: xdev
 * Date: 26.08.2010
 * Time: 22:57:23
 */

class MainRenderLoop extends GLEventListener2D with LogHelper {

  private var playerShip: ShipEntity = null
  private val aliens = new ArrayBuffer[AlienEntity]()

  private var currentGameState = GameState.NEW_GAME

  def onInit(gl: GL): Unit = {
    debug("Initialize")
    playerShip = GameManager.createPlayerShip(Game.SHIP_SPRITE, Game.WND_WIDTH / 2, Game.WND_HEIGHT - 25)
    aliens ++= GameManager.createAliens(this, Game.ALIEN_SPRITE_0, 5, 12)
  }

  def onUpdateFrame(delta: Long, w: Int, h: Int): Unit = {
    processKeyboard()
    currentGameState match {
      case GameState.GAME_RUN =>{
        aliens.foreach(_.move(delta))
        playerShip.move(delta)

        aliens.foreach(_.update(delta))
      }
      case _ =>
    }

  }
  
  def onRenderFrame(gl: GL, w: Int, h: Int): Unit = {
    currentGameState match {
      case GameState.NEW_GAME =>{
        //TODO: Remove magic numbers
        GameManager.createNewGameHud(Game.PRESS_ANY_KEY_SPRITE).draw(gl, 325,250)
      }
      case _ =>
    }
    aliens.foreach(_.draw(gl))
    playerShip.draw(gl)
  }

   private def processKeyboard(): Unit = {
     currentGameState match {
       case GameState.NEW_GAME =>{
         if(Keyboard.isPressed(KeyEvent.VK_SPACE)){
            currentGameState = GameState.GAME_RUN           
         }
       }
       case GameState.GAME_RUN => {
         //TODO: Fix Move speed 
         val leftPressed = Keyboard.isPressed(KeyEvent.VK_LEFT)
         val rightPressed = Keyboard.isPressed(KeyEvent.VK_RIGHT)
         val firePressed = Keyboard.isPressed(KeyEvent.VK_SPACE)
         DebugRenderer.setTextForDebugging("In Game| left: " + leftPressed + " right: " + rightPressed + " fire: " + firePressed)

         playerShip.stop()
         if(leftPressed)playerShip.accelerate(-250, 0)
         if(rightPressed)playerShip.accelerate(250, 0)
         if(firePressed){}
       }
       case _ =>
     }
   }

  private object GameState extends Enumeration {
    val GAME_RUN, GAME_PAUSE, WIN, LOSE, NEW_GAME = Value
  }
}
