package com.lempierzchalski.cs9417.ass3.engine.Tests

import com.lempierzchalski.cs9417.ass3.engine.{IntersectionMultiLanes, IntersectionBase}

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 9/06/13
 * Time: 12:40 AM
 * To change this template use File | Settings | File Templates.
 */
object TestMultiLaneIntersection extends App {
  val intersection = new IntersectionMultiLanes
  var time = 0

  println(intersection.printState())
  intersection.timeStep()
  time += 1
  println(intersection.printState())
  intersection.insertCar(0)
  println(intersection.printState())
  intersection.timeStep()
  time += 1
  intersection.insertCar(1)
  println(intersection.printState())

  for(i <- 0 until 20) {
    intersection.timeStep()
    time += 1
    if(time % 4 == 0) intersection.insertCar(0)
    if(time % 5 == 0) intersection.insertCar(1)
    println(intersection.printState())
  }
  println(intersection.printState())

  intersection.insertCar(0)
  intersection.insertCar(1)
  println(intersection.printState())
  intersection.timeStep()
  intersection.insertCar(1)
  println(intersection.printState())

  for(i <- 0 until 75) {
    intersection.timeStep()
    time += 1
    if(time % 4 == 0) intersection.insertCar(0)
    if(time % 5 == 0) intersection.insertCar(1)
    println(intersection.printState())
  }
  println(intersection.printState())
  intersection.nearestCars()

  for(i <- 0 until 25) {
    intersection.timeStep()
    time += 1
    if(time % 4 == 0) intersection.insertCar(0)
    if(time % 5 == 0) intersection.insertCar(1)
    println(intersection.printState())
  }
  println(intersection.printState())

  intersection.switchLights()
  println(intersection.printState())
  intersection.timeStep()
  time += 1
  intersection.switchLights()
  println(intersection.printState())
  intersection.timeStep()
  time += 1
  println(intersection.printState())
  intersection.timeStep()
  time += 1
  println(intersection.printState())
  intersection.timeStep()
  time += 1
  println(intersection.printState())
  intersection.switchLights()
  println(intersection.printState())

  for(i <- 0 until 100) {
    intersection.timeStep()
    time += 1
    if(time % 4 == 0) intersection.insertCar(0)
    if(time % 5 == 0) intersection.insertCar(1)
    if(time % 20 == 0) intersection.switchLights()
    println(intersection.printState())
  }
}
