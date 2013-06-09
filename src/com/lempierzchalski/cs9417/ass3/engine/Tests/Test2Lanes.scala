package com.lempierzchalski.cs9417.ass3.engine.Tests

import com.lempierzchalski.cs9417.ass3.engine.Road

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 9/06/13
 * Time: 12:25 AM
 * To change this template use File | Settings | File Templates.
 */
object Test2Lanes extends App {
  val road = new Road(laneCount = 2)

  println(road.printRoad())
  road.printNearest()
  road.insertCar()
  road.printNearest()
  println(road.printRoad())
  road.timeStep()
  road.insertCar()
  println(road.printRoad())
  road.printLights()
  road.switchLights()
  road.printLights()
  road.switchLights()

  for(i <- Range(0,5)){
    road.timeStep()
    println(road.printRoad())
  }

  road.insertCar()
  road.insertCar()
  println(road.printRoad())
  road.timeStep()
  road.timeStep()
  println(road.printRoad())
  road.printNearest()
  road.insertCar()
  road.insertCar()
  println(road.printRoad())

  for(i <- Range(0,50)){
    road.timeStep()
    println(road.printRoad())
  }

  road.insertCar()
  road.timeStep()
  road.switchLights()

  for(i <- Range(0,50)){
    road.timeStep()
    println(road.printRoad())
    road.printNearest()
  }
  road.printLights()
  road.switchLights()
  road.timeStep()
  println(road.printRoad())
  road.timeStep()
  println(road.printRoad())
  road.timeStep()
  println(road.printRoad())
}
