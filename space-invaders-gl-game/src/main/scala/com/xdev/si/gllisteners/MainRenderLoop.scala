package com.xdev.si.gllisteners

import com.xdev.engine.logging.LogHelper
import com.xdev.engine.gl.render.GLEventListener2D
import javax.media.opengl.GL
import com.xdev.si.manager.GameManager
import com.xdev.si.entity.{AlienEntity, ShipEntity}
import collection.mutable.ArrayBuffer
import java.awt.event.KeyEvent
import com.xdev.engine.input.Keyboard
import com.xdev.si.GameStateType._
import com.xdev.si.{GameStateType, Game}

/**
 * Created by User: xdev
 * Date: 26.08.2010
 * Time: 22:57:23
 */

class MainRenderLoop extends GLEventListener2D with LogHelper {

  private var playerShip: ShipEntity = null
  private val aliens = new ArrayBuffer[AlienEntity]()

  private var currentGameState = NEW_GAME

  def onInit(gl: GL): Unit = {
    debug("Initialize")
    playerShip = GameManager.createPlayerShip(this, Game.SHIP_SPRITE, Game.WND_WIDTH / 2, Game.WND_HEIGHT - 25)
    aliens ++= GameManager.createAliens(this, Game.ALIEN_SPRITE_0, 5, 12)
  }

  def onUpdateFrame(delta: Long, w: Int, h: Int): Unit = {
    processKeyboard()
    currentGameState match {
      case GAME_RUN =>{
        aliens.foreach(_.move(delta))
        playerShip.move(delta)

        checkCollisions()
        //Remove dead entites
        aliens--=aliens.filter(e => e.isDead)
        playerShip.shots--= playerShip.shots.filter(e => e.isDead)
        //Check
        if (aliens.length == 0){
          notifyAllAlienKilled()
          return
        }

        //Update 
        aliens.foreach(_.update(delta))
        playerShip.update(delta)
      }
      case _ =>
    }
  }

  def onRenderFrame(gl: GL, w: Int, h: Int): Unit = {
    currentGameState match {
      case NEW_GAME =>{
        //TODO: Remove magic numbers
        GameManager.createInfoTexture(Game.PRESS_ANY_KEY_SPRITE).draw(gl, 325,250)
      }
      case WIN =>{
        //TODO: Remove magic numbers
        GameManager.createInfoTexture(Game.WIN_SPRITE).draw(gl, 325,250)
      }
      case LOSE =>{
        //TODO: Remove magic numbers
        GameManager.createInfoTexture(Game.GAME_OVER_SPRITE).draw(gl, 325,250)
      }
      case _ =>
    }
    aliens.foreach(_.draw(gl))
    playerShip.draw(gl)
  }

   private def processKeyboard(): Unit = {
     currentGameState match {
       case NEW_GAME =>{
         if(Keyboard.isPressed(KeyEvent.VK_SPACE)){
            setGameState(GAME_RUN)
         }
       }
       case GAME_RUN => {
         //TODO: Fix Move speed - remove magic number
         val leftPressed = Keyboard.isPressed(KeyEvent.VK_LEFT)
         val rightPressed = Keyboard.isPressed(KeyEvent.VK_RIGHT)
         val firePressed = Keyboard.isPressed(KeyEvent.VK_SPACE)

         playerShip.stop()
         if(leftPressed)playerShip.accelerate(-250, 0)
         if(rightPressed)playerShip.accelerate(250, 0)
         if(firePressed){playerShip.fire()}
       }
       case WIN =>{
         if(Keyboard.isPressed(KeyEvent.VK_SPACE)){
           var bonus = 1000 * Game.CURRENT_LEVEL
           Game.SCORE += bonus
           Game.CURRENT_LEVEL += 1
           aliens ++= GameManager.createAliens(this, Game.ALIEN_SPRITE_0, 5, 12)
           setGameState(GAME_RUN)
         }
       }
       case LOSE =>{
         if(Keyboard.isPressed(KeyEvent.VK_SPACE)){
           var bonus = 1000 * Game.CURRENT_LEVEL
           if(Game.SCORE >= bonus)Game.SCORE -= bonus
           aliens.clear()
           aliens ++= GameManager.createAliens(this, Game.ALIEN_SPRITE_0, 5, 12)
           setGameState(GAME_RUN)
         }
       }
       case _ =>
     }
   }

  def setGameState(gameState: GameStateType.Value){
    currentGameState = gameState
  }

  def notifyPlayerShipDestroyed():Unit={
    setGameState(LOSE)
    playerShip.shots.clear()
   }

  def notifyAllAlienKilled():Unit={
    setGameState(WIN)
    playerShip.shots.clear()
    aliens.clear()
   }

  def notifyAlienKilled():Unit={
    aliens.foreach(_.runFaster())
    Game.SCORE += 100
  }

  def updateEnemyesLogic(){
    aliens.foreach(_.doLogic())
  }

  private def checkCollisions(): Unit = {
    for(shot <- playerShip.shots if !shot.isDead; if !shot.markedAsDead){
      for(enemy <- aliens if !enemy.isDead; if !enemy.markedAsDead; if shot.collidesWith(enemy)){
        shot.collidedWith(enemy)
        return
      }
    }
  }

}
