package com.xdev.si

import entity.bonus.ShotGunBonus
import entity.enemy.{AlienEnemy, EnemyEntity}
import entity.weapon.shots.{ShotgunShot, RocketShot}
import gllisteners.{DebugRenderer, BackgroundRenderer, HudRenderer, MainRenderLoop}
import javax.media.opengl.GL2
import com.xdev.engine.gl.GLGameWindow
import com.xdev.engine.gl.render.GLEventListener2D
import com.xdev.engine.sprite.Sprite
import collection.mutable.HashMap
import com.xdev.engine.tiles.{TileManager, TileMap}
import com.xdev.engine.util.ResourceFactory
import manager.GameManager
import javax.swing.SwingUtilities

object Game extends GLGameWindow("Space Invaders GL - Scala version 1.1", 800, 600){

  val WND_WIDTH = 800
  val WND_HEIGHT = 600
  var CURRENT_LEVEL = 1
  val LEVELS_COUNT = 5
  var SCORE = 0

  val PRESS_ANY_KEY_SPRITE = "/sprites/pressanykey.gif"
  val WIN_SPRITE = "/sprites/youwin.gif"
  val GAME_OVER_SPRITE = "/sprites/gotyou.gif"
  val SHIP_SPRITE = "/sprites/ship.gif"
  val SHOT_SPRITE = "/sprites/1.gif"
  val ALIEN_SPRITE_0 = "/sprites/alien.gif"
  val ALIEN_SPRITE_1 = "/sprites/alien2.gif"
  val ALIEN_SPRITE_2 = "/sprites/alien3.gif"
  val BACKGROUND_SPRITE = "/sprites/space.png"

  val EXPL_TILE_MAP = "/sprites/exp1.png"
  val ALIEN_MAIN_TILE_MAP = "/sprites/alienmain.png"
  val ALIEN_DMG_TILE_MAP = "/sprites/aliendmg.png"

  //Bonuses sprites
  val B_SHOT_SPEED_SPRITE = "/sprites/bonus/bonus_frate.png"
  val B_SHIP_SPEED_SPRITE = "/sprites/bonus/bonus_acc.png"
  val B_LASER = "/sprites/bonus/bonus_laser.png"
  val B_ROCKET = "/sprites/bonus/bonus_rocket_launcher.png"
  val B_SHOTGUN = "/sprites/bonus/bonus_shotgun.png"

  val LEVEL_PATH_PATTERN = "/levels/%s.lvl"

  //Shot sprites
  val LASER_SHOT_SPRITE = "/sprites/weapon/laser/laserShot.png"
  val ROCKET_SHOT_SPRITE = "/sprites/weapon/rocket/rocket.png"
  val ROCKET_BURST_TILE_MAP = "/sprites/weapon/rocket/rocketBurst.png"
  val SHOTGUN_SHRAPNEL_SPRITE = "/sprites/weapon/shotgun/scrap.png"
  val SHOTGUN_SHRAPNEL_TILE_MAP = "/sprites/weapon/shotgun/scrapBurst.png"

  //Shot animations
  var rocketTileMap: TileMap = null
  var shotgunShrapnelTileMap: TileMap = null

  var explTileMap:TileMap = null
  var alienMainTileMap:TileMap = null
  var alienDmgTileMap:TileMap = null

  val frameSets = new HashMap[Int, Array[Sprite]]()

  def initGameRenders(): Array[GLEventListener2D] = {
    info("Init game renders")
    val start = System.currentTimeMillis
    val listeners = Array[GLEventListener2D](BackgroundRenderer, MainRenderLoop, HudRenderer, DebugRenderer)
    debug("Initialize time : " + (System.currentTimeMillis - start) + " ms")
    return listeners
  }

  def initGameResources(gl: GL2): Unit = {
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
    ResourceFactory.getSprite(LASER_SHOT_SPRITE)
    ResourceFactory.getSprite(ROCKET_SHOT_SPRITE)
    ResourceFactory.getSprite(SHOTGUN_SHRAPNEL_SPRITE)
    ResourceFactory.getSprite(B_SHIP_SPEED_SPRITE)
    ResourceFactory.getSprite(B_SHOT_SPEED_SPRITE)
    ResourceFactory.getSprite(B_LASER)
    ResourceFactory.getSprite(B_ROCKET)
    ResourceFactory.getSprite(B_SHOTGUN)
    ResourceFactory.getSprite(BACKGROUND_SPRITE)

    info("Load animation frames")
    explTileMap = TileManager.load(EXPL_TILE_MAP, 42, 42)
    alienDmgTileMap = TileManager.load(ALIEN_DMG_TILE_MAP, 40, 25)
    alienMainTileMap = TileManager.load(ALIEN_MAIN_TILE_MAP, 40, 25)

    rocketTileMap = TileManager.load(ROCKET_BURST_TILE_MAP, 14, 48)
    shotgunShrapnelTileMap = TileManager.load(SHOTGUN_SHRAPNEL_TILE_MAP, 10, 9)

    frameSets.put(AlienEnemy.EXPLOSION_ANIMATION, explTileMap.toLine)
    frameSets.put(AlienEnemy.DAMAGE_ANIMATION, alienDmgTileMap.toLine)
    frameSets.put(AlienEnemy.MAIN_ANIMATION, alienMainTileMap.toLine)

    frameSets.put(RocketShot.FLIGHT_ANIMATION, rocketTileMap.toLine)
    frameSets.put(ShotgunShot.SPINNING_ANIMATION, shotgunShrapnelTileMap.toLine)
    info("Game resources loaded")

    info("Register game bonuses")
    GameManager.registerGameBonuses()
    info("Done")
    debug("Initialize time : " + (System.currentTimeMillis - start) + " ms")
  }
  
  //Game entry point
  def main(args: Array[String]): Unit = {
    SwingUtilities.invokeLater(new Runnable() {
      def run() {
        Game.showWindow()
      }
    })
  }
}
