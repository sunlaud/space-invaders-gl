package com.xdev.si.entity.weapon.shots
import com.xdev.si.scripting.lua.MutablesLuaConfigurator


/**
 * Configuration-aware source of shots params.
 *
 * @author sunlaud
 */
object ShotParamsFactory {

  private val CONFIG_FILE_NAME = "configs/weapon_config.lua"

  private val configurator = new MutablesLuaConfigurator(CONFIG_FILE_NAME)

  //TODO do something with that boilerplate code

  // prevent reassign since references to this mutable objects are used by configurator
  // params hardcoded here are used as defaults if config file is missing
  private val laserShotParams = new ShotParams(10, 10, -800)
  private val rocketShotParams = new ShotParams(35, 50, -100)
  private val shotgunShotParams = new ShotParams(10, 10, -800)
  private val fireballShotParams = new ShotParams(100, 900, -180)

  //bind to lua functions
  configurator.registerConfigurable(laserShotParams, "Laser")
  configurator.registerConfigurable(shotgunShotParams, "Shotgun")
  configurator.registerConfigurable(fireballShotParams, "Fireball")
  configurator.registerConfigurable(rocketShotParams, "Rocket")

  configurator.configure()

  //maybe defencive copying is kind of overkill here
  def getLaserShotParams = new ShotParams(laserShotParams)
  def getRocketShotParams = new ShotParams(rocketShotParams)
  def getShotgunShotParams = new ShotParams(shotgunShotParams)
  def getFireballShotParams = new ShotParams(fireballShotParams)

  def reloadConfig() {
    configurator.configure()
  }
}
