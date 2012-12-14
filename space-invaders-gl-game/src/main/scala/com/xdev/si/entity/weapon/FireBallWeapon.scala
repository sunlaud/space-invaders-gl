package com.xdev.si.entity.weapon

import com.xdev.si.manager.GameManager
import org.openmali.vecmath2.Vector3f
import com.xdev.si.Game
import com.xdev.engine.util.ResourceFactory
import com.xdev.si.entity.AbstractEntity
import shots.FireBallShot


class FireBallWeapon(pos: Vector3f) extends AbstractWeapon(pos) {

  name = "FireBall"

  waitInterval(3000)

  protected def makeShot(pos: Vector3f) {
    addShot(new FireBallShot(new Vector3f(pos.getX, pos.getY - 60, 0.0f)))
  }
}