package com.lempierzchalski.cs9417.ass3.Tests

import com.lempierzchalski.cs9417.ass3.engine.Road

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 3/06/13
 * Time: 8:33 AM
 * To change this template use File | Settings | File Templates.
 */
object TestRoad extends App {
  val road = new Road()

  println(road.printRoad())
  road.printNearest()
  road.insertCar()
  road.printNearest()
  println(road.printRoad())
  road.timeStep()
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
