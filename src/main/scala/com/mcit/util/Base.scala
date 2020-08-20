package com.mcit.util

trait Base {

  import org.apache.log4j.{Level, Logger}

  val rootLogger: Logger = Logger.getRootLogger
  rootLogger.setLevel(Level.ERROR)
}
