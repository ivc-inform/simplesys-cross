package com.simplesys.common

import java.io.File

trait Pathes {
  def testResourcesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\".replace("\\", File.separator)

  def mainResourcesPath = System.getProperty("user.dir") + "\\src\\main\\resources\\".replace("\\", File.separator)
}