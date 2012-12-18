package com.xdev.si.scripting.lua

import org.luaj.vm2.Globals
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.{CoerceJavaToLua, JsePlatform}
import java.io.File
import com.xdev.engine.logging.LogHelper
import com.xdev.si.entity.weapon.shots.ShotParams


/**
 * Quck&dirty lua wrapper
 *
 * @author sunlaud
 */
class LuaScript extends LogHelper {

  private var luaChunk: LuaValue = null
  private var _G: Globals = JsePlatform.standardGlobals
  //LuaJC.install

  info("using this dir as base when searching scripts: " + new File(".").getCanonicalPath)

  //TODO: these methods need further generalization and more universal interface
  def getVoidOneArgFcn(fcnName: String): Object => Unit = {
    val fcn = _G.get(fcnName)
    (arg: Object) => fcn.call(CoerceJavaToLua.coerce(arg))
  }

  def getVoidTwoArgFcn(fcnName: String): (Object, Object) => Unit = {
    val fcn = _G.get(fcnName)
    (arg1: Object, arg2: Object) => fcn.call(CoerceJavaToLua.coerce(arg1), CoerceJavaToLua.coerce(arg2))
  }


  /**
   * Binds java interface (scala trait) and lua object which implements that interface. Lua object should have
   * methods with same name as interface (trait). Doesn't work with regular classes.
   */
  def getImplementor[T](interfaze: Class[T], luaImplementorObjName: String): T = {
    var luaImplementor = _G.get(luaImplementorObjName)
    var registrator = _G.get("getImplementor")
    var luaProxy = registrator.call(LuaValue.valueOf("getImplementor"), LuaValue.valueOf(interfaze.getCanonicalName()), luaImplementor)
    luaProxy.touserdata().asInstanceOf[T]
  }

  def load(fileName: String) {
    info("loading script '" + fileName + "'")
    luaChunk = _G.loadFile(fileName)
    luaChunk.call(LuaValue.valueOf(fileName))
  }

  //TODO: figure out how we should do proper cleanup
  def close() {
  }

}
