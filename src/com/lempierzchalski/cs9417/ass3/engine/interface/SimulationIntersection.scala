package com.lempierzchalski.cs9417.ass3.engine.interface

import com.lempierzchalski.cs9417.ass3.engine.TrafficLightColour

/**
 * User: Pierzchalski
 * Date: 8/06/13
 * Package: com.lempierzchalski.cs9417.ass3.engine.interface
 * Project: trafficLightRL
 */
trait SimulationIntersection {
  def carEntranceCount: Int //return total number of car lanes (on all roads)
  def insertCar(entranceIndex: Int) //inserts a car at given
  def countWaiting: Int
  def printState: String
  def nearestCars: Seq[Option[Int]]
  def checkLights: Seq[TrafficLightColour]
  def getCoolDown: Int
  def timeStep()
  def switchLights()
  def isCarWaiting: Boolean
}
