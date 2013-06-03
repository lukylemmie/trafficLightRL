package com.lempierzchalski.cs9417.ass3.Tests

import com.lempierzchalski.cs9417.ass3.Road

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 3/06/13
 * Time: 8:33 AM
 * To change this template use File | Settings | File Templates.
 */
object TestRoad extends App {
  val road = new Road()

  road.printRoad()
  road.insertCar()
  road.printRoad()
  road.timeStep()
  road.printRoad()
  road.printLights()
  road.switchLights()
  road.printLights()
  road.switchLights()

  for(i <- Range(0,5)){
    road.timeStep()
    road.printRoad()
  }

  road.insertCar()
  road.printRoad()
  road.timeStep()
  road.timeStep()
  road.printRoad()
  road.insertCar()
  road.insertCar()
  road.printRoad()

  for(i <- Range(0,50)){
    road.timeStep()
    road.printRoad()
  }

  road.insertCar()
  road.timeStep()

  for(i <- Range(0,50)){
    road.timeStep()
    road.printRoad()
  }
  road.printLights()
  road.switchLights()
  road.timeStep()
  road.printRoad()
  road.timeStep()
  road.printRoad()
  road.timeStep()
  road.printRoad()
}
