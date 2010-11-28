package com.xdev.engine.logging

import org.apache.log4j.Logger

/**
 * Created by User: xdev
 * Date: 21.09.2010
 * Time: 0:05:19
 */

trait LogHelper {
  val loggerName = this.getClass.getName
  lazy val logger = Logger.getLogger(loggerName)

  def debug(message: Any) =  if(logger.isDebugEnabled)logger.debug(Console.GREEN + message + Console.RESET)
  def info(message: Any)  =  if(logger.isInfoEnabled)logger.info(Console.YELLOW + message + Console.RESET)
  def error(message: Any) =  logger.error(Console.RED + message + Console.RESET)
  def fatal(message: Any) =  logger.fatal(Console.RED + message + Console.RESET)
  def warn(message: Any) =  logger.warn(Console.BLUE + message + Console.RESET)
  def error(message: Any, t: Throwable) =  logger.error(Console.RED + message + Console.RESET, t)
  def fatal(message: Any, t: Throwable) =  logger.fatal(Console.RED + message + Console.RESET, t)

}