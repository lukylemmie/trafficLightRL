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
  var time = 0

  println(road.printRoad())
  road.printNearest()
  if(time % 5 == 0) road.insertCar(0)
  if(time % 7 == 0) road.insertCar(1)
  if(time % 8 == 0) road.switchLights()
  road.printNearest()
  println(road.printRoad())
  road.timeStep()
  time += 1
  if(time % 5 == 0) road.insertCar(0)
  if(time % 7 == 0) road.insertCar(1)
  if(time % 8 == 0) road.switchLights()
  println(road.printRoad())
  road.printLights()
  road.switchLights()
  road.printLights()
  road.switchLights()

  for(i <- Range(0,120)){
    road.timeStep()
    time += 1
    if(time % 5 == 0) road.insertCar(0)
    if(time % 7 == 0) road.insertCar(1)
    if(time % 8 == 0) road.switchLights()
    road.printLights()
    println(road.printRoad())
    road.printNearest()
  }
  road.switchLights()
  road.timeStep()
  println(road.printRoad())
  road.timeStep()
  println(road.printRoad())
  road.timeStep()
  println(road.printRoad())
}
