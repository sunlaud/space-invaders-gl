package com.xdev.si

import entity.enemy.EnemyEntity
import gllisteners.{DebugRenderer, BackgroundRenderer, HudRenderer, MainRenderLoop}
import javax.media.opengl.GL
import com.xdev.engine.gl.GLGameWindow
import com.xdev.engine.gl.render.GLEventListener2D
import com.xdev.engine.sprite.Sprite
import collection.mutable.HashMap
import com.xdev.engine.tiles.{TileManager, TileMap}
import com.xdev.engine.util.ResourceFactory
import manager.GameManager

object Game extends GLGameWindow("Space Invaders GL - Scala version 1.1", 800, 600){

  val WND_WIDTH = 800
  val WND_HEIGHT = 600
  var CURRENT_LEVEL = 1
  var SCORE = 0

  val PRESS_ANY_KEY_SPRITE = "/sprites/pressanykey.gif"
  val WIN_SPRITE = "/sprites/youwin.gif"
  val GAME_OVER_SPRITE = "/sprites/gotyou.gif"
  val SHIP_SPRITE = "/sprites/ship.gif"
  val SHOT_SPRITE = "/sprites/shot.gif"
  val ALIEN_SPRITE_0 = "/sprites/alien.gif"
  val ALIEN_SPRITE_1 = "/sprites/alien2.gif"
  val ALIEN_SPRITE_2 = "/sprites/alien3.gif"
  val BACKGROUND_SPRITE = "/sprites/space.png"

  val EXPL_TILE_MAP = "/sprites/exp1.png"

  //Bonuses sprites
  val B_SHOT_SPEED_SPRITE = "/sprites/shotSpeed.png"
  val B_SHIP_SPEED_SPRITE = "/sprites/plazma.png"

  var explTileMap:TileMap = null

  val frameSets = new HashMap[Int, Array[Sprite]]()

  def initGameRenders(): Array[GLEventListener2D] = {
    info("Init game renders")
    val start = System.currentTimeMillis
    val listeners = Array[GLEventListener2D](BackgroundRenderer, MainRenderLoop, HudRenderer, DebugRenderer)
    debug("Initialize time : " + (System.currentTimeMillis - start) + " ms")
    return listeners
  }

  def initGameResources(gl: GL): Unit = {
    val start = System.currentTimeMillis
    info("Init game resources")
    info("Preinit textures")
    ResourceFactory.getSprite(PRESS_ANY_KEY_SPRITE)
    ResourceFactory.getSprite(WIN_SPRITE)
    ResourceFactory.getSprite(GAME_OVER_SPRITE)
    ResourceFactory.getSprite(SHIP_SPRITE)
    ResourceFactory.getSprite(SHOT_SPRITE)
    ResourceFactory.getSprite(ALIEN_SPRITE_0)
    ResourceFactory.getSprite(ALIEN_SPRITE_1)
    ResourceFactory.getSprite(ALIEN_SPRITE_2)
    ResourceFactory.getSprite(B_SHIP_SPEED_SPRITE)
    ResourceFactory.getSprite(BACKGROUND_SPRITE)

    info("Load animation frames")
    explTileMap = TileManager.load(EXPL_TILE_MAP, 42, 42)
    val enemyMainAnimatonFrames = Array[Sprite](
                  ResourceFactory.getSprite(Game.ALIEN_SPRITE_0),
                  ResourceFactory.getSprite(Game.ALIEN_SPRITE_1),
                  ResourceFactory.getSprite(Game.ALIEN_SPRITE_2),
                  ResourceFactory.getSprite(Game.ALIEN_SPRITE_1),
                  ResourceFactory.getSprite(Game.ALIEN_SPRITE_0)
                )
    frameSets.put(EnemyEntity.MAIN_ANIMATION, enemyMainAnimatonFrames)
    frameSets.put(EnemyEntity.EXPLOSION_ANIMATION, explTileMap.toLine)
    info("Game resources loaded")

    info("Register game bonuses")
    GameManager.registerGameBonuses()
    info("Done")
    debug("Initialize time : " + (System.currentTimeMillis - start) + " ms")
  }
  
  //Game entry point
  def main(args: Array[String]): Unit = {
    Game.showWindow()
  }
}
