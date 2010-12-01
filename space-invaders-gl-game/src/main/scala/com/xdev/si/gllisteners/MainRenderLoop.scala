package com.xdev.si.gllisteners

import com.xdev.engine.logging.LogHelper
import com.xdev.engine.gl.render.GLEventListener2D
import javax.media.opengl.GL
import com.xdev.si.manager.GameManager
import collection.mutable.ArrayBuffer
import java.awt.event.KeyEvent
import com.xdev.engine.input.Keyboard
import com.xdev.si.GameStateType._
import com.xdev.si.{GameStateType, Game}
import org.openmali.vecmath2.Vector3f
import com.xdev.si.entity.player.ShipEntity
import com.xdev.si.entity.enemy.AlienEntity
import com.xdev.si.entity.AbstractEntity
import com.xdev.si.entity.bonus.{AbstractBonus, ShipAccBonus, ShotSpeedBonus}

/**
 * Created by User: xdev
 * Date: 26.08.2010
 * Time: 22:57:23
 */

class MainRenderLoop extends GLEventListener2D with LogHelper {

  private var playerShip: ShipEntity = null
  private val aliens = new ArrayBuffer[AlienEntity]()
  private val bonuses = new ArrayBuffer[AbstractBonus]()

  private var currentGameState = NEW_GAME

  private val infoSpritePos = new Vector3f(325.0f, 250.0f, 0.0f)
  private val playerShipStartPosition = new Vector3f(Game.WND_WIDTH / 2, Game.WND_HEIGHT - 25, 0.0f)

  override def onInit(gl: GL): Unit = {
    debug("Initialize")
    playerShip = GameManager.createPlayerShip(this, Game.SHIP_SPRITE, playerShipStartPosition)
    aliens ++= GameManager.createAliens(this, Game.ALIEN_SPRITE_0, 5, 12)
  }

  override def onUpdateFrame(delta: Long, w: Int, h: Int): Unit = {
    processKeyboard()
    currentGameState match {
      case GAME_RUN =>{
        aliens.foreach(_.move(delta))
        playerShip.move(delta)
        bonuses.foreach(_.move(delta))

        checkCollisions()
        //Remove dead entites
        aliens--=aliens.filter(e => e.isDead)
        bonuses--=bonuses.filter(e => e.isDead)
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

  override def onRenderFrame(gl: GL, w: Int, h: Int): Unit = {
    if(DebugRenderer.isDebugEnabled())
      renderDebugInfo()

    currentGameState match {
      case NEW_GAME =>{
        //TODO: Remove magic numbers
        GameManager.createInfoTexture(Game.PRESS_ANY_KEY_SPRITE).draw(gl, infoSpritePos)
      }
      case WIN =>{
        //TODO: Remove magic numbers
        GameManager.createInfoTexture(Game.WIN_SPRITE).draw(gl, infoSpritePos)
      }
      case LOSE =>{
        //TODO: Remove magic numbers
        GameManager.createInfoTexture(Game.GAME_OVER_SPRITE).draw(gl, infoSpritePos)
      }
      case _ =>
    }
    bonuses.foreach(_.draw(gl))
    aliens.foreach(_.draw(gl))
    playerShip.draw(gl)
  }

   private def processKeyboard(): Unit = {
     //TODO: After changing input processing logic - refactor this
     if(Keyboard.isPressed(KeyEvent.VK_F2)){
       DebugRenderer.show()
       return
     }
     if(Keyboard.isPressed(KeyEvent.VK_F3)){
       DebugRenderer.hide()
       return
     }
     if(Keyboard.isPressed(KeyEvent.VK_ESCAPE)){
       Game.closeWindow()
       return
     }
     currentGameState match {
       case NEW_GAME =>{
         if(Keyboard.isPressed(KeyEvent.VK_SPACE)){
            setGameState(GAME_RUN)
         }
       }
       case GAME_RUN => {
         //TODO: Fix Move speed - remove magic number
         val upPressed = Keyboard.isPressed(KeyEvent.VK_UP)
         val downPressed = Keyboard.isPressed(KeyEvent.VK_DOWN)
         val leftPressed = Keyboard.isPressed(KeyEvent.VK_LEFT)
         val rightPressed = Keyboard.isPressed(KeyEvent.VK_RIGHT)
         val firePressed = Keyboard.isPressed(KeyEvent.VK_SPACE)

         playerShip.stop()
         
         if(upPressed)playerShip.accelerate(0, -playerShip.acceleration)
         if(downPressed)playerShip.accelerate(0, playerShip.acceleration)
         if(leftPressed)playerShip.accelerate(-playerShip.acceleration, 0)
         if(rightPressed)playerShip.accelerate(playerShip.acceleration, 0)
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
           playerShip.position.set(playerShipStartPosition)
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

  /**
   * Generate random bonus affter enemy killed
   */
  def generateBonus(killedEnemyPosition: Vector3f){
    val generatedBonus: Option[AbstractBonus] = GameManager.generateRandomBonus(killedEnemyPosition)
    if(generatedBonus.isDefined){
      bonuses += generatedBonus.get
    }
  }

  private def checkCollisions(): Unit = {
    for(shot <- playerShip.shots if !shot.isDead; if !shot.markedAsDead){
      for(enemy <- aliens if !enemy.isDead; if !enemy.markedAsDead; if shot.collidesWith(enemy)){
        shot.collidedWith(enemy)
        return
      }
    }
    
    for(enemy <- aliens if !enemy.isDead; if !enemy.markedAsDead; if playerShip.collidesWith(enemy)){
        playerShip.collidedWith(enemy)
        return
      }

    for(bonus <- bonuses if !bonus.isDead; if !bonus.markedAsDead; if bonus.isCollidesWith(playerShip)){
        bonus.collidedWith(playerShip)
        return
    }
  }

  private def renderDebugInfo(){
    val textBuff = Array[String](
        "game state : " + currentGameState,
        "fps : " + fps,
        "delta : " + delta,
        "aliens : " + aliens.length,
        "shots : " + playerShip.shots.length,
        "bonuses : " + bonuses.length,
        "ship acceleration : " + playerShip.acceleration,
        "ship firingInterval : " + playerShip.firingInterval
      )
    DebugRenderer.setTextForDebugging(textBuff)
  }
}
