package com.lempierzchalski.cs9417.ass3.Tests

import com.lempierzchalski.cs9417.ass3.engine.{IntersectionParams, Road}

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 3/06/13
 * Time: 8:33 AM
 * To change this template use File | Settings | File Templates.
 */
object TestRoad extends App {
  val road = new Road(IntersectionParams())
  var time = 0

  println(road.printRoad())
  road.printNearest()
  if(time % 5 == 0) road.insertCar(0)
  if(time % 8 == 0) road.switchLights()
  road.printNearest()
  println(road.printRoad())
  for(i <- Range(0,120)){
    road.timeStep()
    time += 1
    if(time % 5 == 0) road.insertCar(0)
    if(time % 8 == 0) road.switchLights()
    road.printLights()
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
