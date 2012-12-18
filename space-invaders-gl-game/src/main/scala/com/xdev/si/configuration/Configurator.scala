package com.xdev.si.configuration


/**
 *
 * @author sunlaud
 */
trait Configurator {

  /**
   * Binds scala object and config section/function/etc used to configure that object
   * @param configurable scala object to configure
   * @param configureFcnName config section/function/etc used to configure object
   */
  def registerConfigurable[T](configurable: T, configureFcnName: String)

  /**
   * Perform (re)configuration. Can be called repeatedly.
   */
  def configure()

}
