package com.xdev.si.gllisteners

import com.xdev.engine.logging.LogHelper
import com.xdev.engine.gl.render.GLEventListener2D
import javax.media.opengl.GL2
import com.xdev.si.manager.GameManager
import collection.mutable.ArrayBuffer
import java.awt.event.KeyEvent
import com.xdev.engine.input.Keyboard
import com.xdev.si.GameStateType._
import com.xdev.si.{GameStateType, Game}
import org.openmali.vecmath2.Vector3f
import com.xdev.si.entity.player.PlayerEntity
import com.xdev.si.entity.enemy.EnemyEntity
import com.xdev.si.entity.bonus.AbstractBonus
import com.xdev.si.core.loader.LevelLoader
import com.xdev.si.entity.weapon.{FireBallWeapon, ShotgunWeapon, RocketWeapon, LaserWeapon}

/**
 * Created by User: xdev
 * Date: 26.08.2010
 * Time: 22:57:23
 */

object MainRenderLoop extends GLEventListener2D with LogHelper {

  private var player: PlayerEntity = null
  private val enemies = new ArrayBuffer[EnemyEntity]()
  private val bonuses = new ArrayBuffer[AbstractBonus]()

  private var currentGameState = NEW_GAME

  private val INFO_SPRITE_POS = new Vector3f(325.0f, 250.0f, 0.0f)
  private val PLAYER_START_POS = new Vector3f(Game.WND_WIDTH / 2, Game.WND_HEIGHT - 25, 0.0f)

  override def onInit(gl: GL2) {
    debug("Initialize")
    player = GameManager.createPlayer(Game.SHIP_SPRITE, PLAYER_START_POS)
    enemies ++= GameManager.createEnemies(new LevelLoader().load(Game.LEVEL_PATH_PATTERN.format(Game.CURRENT_LEVEL)))
  }

  override def onUpdateFrame(delta: Long, w: Int, h: Int) {
    processKeyboard()
    currentGameState match {
      case GAME_RUN =>{
        enemies.foreach(_.move(delta))
        player.move(delta)
        bonuses.foreach(_.move(delta))

        checkCollisions()

        //Remove dead entites
        enemies --= enemies.filter(e => e.isDead)
        bonuses --= bonuses.filter(e => e.isDead)
        player.weapon.removeUnusedShots()
        //Check
        if (enemies.length == 0){
          notifyAllAlienKilled()
          return
        }

        //Update 
        enemies.foreach(_.update(delta))
        player.update(delta)
      }
      case _ =>
    }
  }

  override def onRenderFrame(gl: GL2, w: Int, h: Int) {
    if(DebugRenderer.isDebugEnabled)
      renderDebugInfo()

    currentGameState match {
      case NEW_GAME =>{
        //TODO: Remove magic numbers
        GameManager.createSprite(Game.PRESS_ANY_KEY_SPRITE).draw(gl, INFO_SPRITE_POS)
      }
      case WIN =>{
        //TODO: Remove magic numbers
        GameManager.createSprite(Game.WIN_SPRITE).draw(gl, INFO_SPRITE_POS)
      }
      case LOSE =>{
        //TODO: Remove magic numbers
        GameManager.createSprite(Game.GAME_OVER_SPRITE).draw(gl, INFO_SPRITE_POS)
      }
      case _ =>
    }
    player.draw(gl)
    bonuses.foreach(_.draw(gl))
    enemies.foreach(_.draw(gl))
  }

   private def processKeyboard() {
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
         player.stop()
         if(Keyboard.isPressed(KeyEvent.VK_LEFT))player.accelerate(-player.acceleration)
         if(Keyboard.isPressed(KeyEvent.VK_RIGHT))player.accelerate(player.acceleration)
         if(Keyboard.isPressed(KeyEvent.VK_Z)){player.fire()}

         //Change weapon keys, for debug purposes
         if(Keyboard.isPressed(KeyEvent.VK_1)){player.changeWeapon(new LaserWeapon(player.pos))}
         if(Keyboard.isPressed(KeyEvent.VK_2)){player.changeWeapon(new RocketWeapon(player.pos))}
         if(Keyboard.isPressed(KeyEvent.VK_3)){player.changeWeapon(new ShotgunWeapon(player.pos))}
         if(Keyboard.isPressed(KeyEvent.VK_4)){player.changeWeapon(new FireBallWeapon(player.pos))}
       }
       case WIN =>{
         if(Keyboard.isPressed(KeyEvent.VK_SPACE)){
           var bonus = 1000 * Game.CURRENT_LEVEL
           Game.SCORE += bonus
           if (Game.CURRENT_LEVEL + 1 <= Game.LEVELS_COUNT){
             Game.CURRENT_LEVEL += 1
           }
           enemies ++= GameManager.createEnemies(new LevelLoader().load(Game.LEVEL_PATH_PATTERN.format(Game.CURRENT_LEVEL)))
           setGameState(GAME_RUN)
         }
       }
       case LOSE =>{
         if(Keyboard.isPressed(KeyEvent.VK_SPACE)){
           var bonus = 1000 * Game.CURRENT_LEVEL
           if(Game.SCORE >= bonus)Game.SCORE -= bonus
           enemies.clear()
           enemies ++= GameManager.createEnemies(new LevelLoader().load(Game.LEVEL_PATH_PATTERN.format(Game.CURRENT_LEVEL)))
           player.position.set(PLAYER_START_POS)
           setGameState(GAME_RUN)
         }
       }
       case _ =>
     }
   }

  def setGameState(gameState: GameStateType.Value){
    currentGameState = gameState
  }

  def notifyPlayerShipDestroyed(){
    setGameState(LOSE)
    player.weapon.removeAllShots()
   }

  def notifyAllAlienKilled(){
    setGameState(WIN)
    player.weapon.removeAllShots()
    enemies.clear()
   }

  def notifyAlienKilled(){
    enemies.foreach(_.onFriendEnemyKilled())
    Game.SCORE += 100
  }

  def updateEnemiesLogic(){
    enemies.foreach(_.doLogic())
  }

  /**
   * Generate random bonus affter enemy killed
   */
  def generateBonus(killedEnemyPosition: Vector3f){
    GameManager.generateRandomBonus(killedEnemyPosition).foreach(bonuses += _)
  }

  private def checkCollisions(){
    for(shot <- player.weapon.shots if !shot.isDead; if !shot.markedAsDead){
      for(enemy <- enemies if !enemy.isDead; if !enemy.markedAsDead; if shot.collidesWith(enemy)){
        shot.collidedWith(enemy)
        return
      }
    }
    
    for(enemy <- enemies if !enemy.isDead; if !enemy.markedAsDead; if player.collidesWith(enemy)){
        player.collidedWith(enemy)
        return
      }

    for(bonus <- bonuses if !bonus.isDead; if !bonus.markedAsDead; if bonus.isCollidesWith(player)){
        bonus.collidedWith(player)
        return
    }
  }

  private def renderDebugInfo(){
    val textBuff = Array[String](
        "game state : " + currentGameState,
        "fps : " + fps,
        "delta : " + delta,
        "aliens : " + enemies.length,
        "shots : " + player.weapon.getShotsCount,
        "bonuses : " + bonuses.length,
        "ship acceleration : " + player.acceleration,
        "ship firingInterval : " + player.weapon.waitInterval,
        "current weapon : " + player.weapon.name,
        "weapon stack : " + player.weaponStack.keySet.toString
      )
    DebugRenderer.setTextForDebugging(textBuff)
  }
}
