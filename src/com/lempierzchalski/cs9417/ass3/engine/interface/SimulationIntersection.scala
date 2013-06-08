package com.lempierzchalski.cs9417.ass3.engine.interface

import com.lempierzchalski.cs9417.ass3.engine.TrafficLightColour

/**
 * User: Pierzchalski
 * Date: 8/06/13
 * Package: com.lempierzchalski.cs9417.ass3.engine.interface
 * Project: trafficLightRL
 */
trait SimulationIntersection {
  def roadCount: Int
  def insertCar(roadIndex: Int)
  def countWaiting: Int
  def printState: String
  def nearestCars: Seq[Option[Int]]
  def checkLights: Seq[TrafficLightColour]
  def getCooldown: Int
  def timeStep()
  def switchLights()
  def isCarWaiting: Boolean
}
