package com.xdev.si.scripting.lua

import com.xdev.si.configuration.Configurator


/**
 * Configures mutable scala objects (using var properties) via lua scripts.
 * Currently it supports only setting var object fields (implemented as methods)
 * @param configFileName lua file name to use
 *
 * @author sunlaud
 */
class MutablesLuaConfigurator(configFileName: String) extends Configurator {

  //file containing some scala/lua glue code
  private val systemFileName = "scripts/lua/system/scala_bridge.lua"
  private val script = new LuaScript()
  script.load(systemFileName)
  //this one dynamically defines lua functions used by #registerConfigurable
  private val defineMutablesConfigurator = script.getVoidTwoArgFcn("defineMutablesConfigurator")

  /**
   * Binds scala object and lua function used to configure that object
   * @param configurable scala object to configure
   * @param configureFcnName lua function which being called configures scala object
   */
  override def registerConfigurable[T](configurable: T, configureFcnName: String) {
    defineMutablesConfigurator(configureFcnName.asInstanceOf[Object], configurable.asInstanceOf[Object])
  }

  def configure() {
    script.load(configFileName)
  }

}
