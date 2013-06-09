package com.lempierzchalski.cs9417.ass3.engine.Tests

import com.lempierzchalski.cs9417.ass3.engine.{IntersectionBase, IntersectionParams}

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 9/06/13
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */
object TestCarsCanChangeLanes extends App {
  val intersectionParams = new IntersectionParams(numberOfLanes = 3, changeLanes = true)
  val intersection = new IntersectionBase(intersectionParams)
  var time = 0

  println(intersection.printState)
  intersection.timeStep()
  time += 1
  println(intersection.printState)
  intersection.insertCar(0)
  println(intersection.printState)
  intersection.timeStep()
  time += 1
  intersection.insertCar(1)
  println(intersection.printState)

  for(i <- 0 until 20) {
    intersection.timeStep()
    time += 1
    if(time % 12 == 0) intersection.insertCar(0)
    if(time % 3 == 0) intersection.insertCar(1)
    if(time % 14 == 0) intersection.insertCar(2)
    if(time % 11 == 0) intersection.insertCar(3)
    if(time % 2 == 0) intersection.insertCar(4)
    if(time % 11 == 0) intersection.insertCar(5)
    println(intersection.printState)
  }
  println(intersection.printState)

  intersection.insertCar(0)
  intersection.insertCar(1)
  println(intersection.printState)
  intersection.timeStep()
  intersection.insertCar(1)
  println(intersection.printState)

  for(i <- 0 until 75) {
    intersection.timeStep()
    time += 1
    if(time % 12 == 0) intersection.insertCar(0)
    if(time % 3 == 0) intersection.insertCar(1)
    if(time % 14 == 0) intersection.insertCar(2)
    if(time % 11 == 0) intersection.insertCar(3)
    if(time % 2 == 0) intersection.insertCar(4)
    if(time % 11 == 0) intersection.insertCar(5)
    println(intersection.printState)
  }
  println(intersection.printState)
  intersection.nearestCars

  for(i <- 0 until 25) {
    intersection.timeStep()
    time += 1
    if(time % 12 == 0) intersection.insertCar(0)
    if(time % 3 == 0) intersection.insertCar(1)
    if(time % 14 == 0) intersection.insertCar(2)
    if(time % 11 == 0) intersection.insertCar(3)
    if(time % 2 == 0) intersection.insertCar(4)
    if(time % 11 == 0) intersection.insertCar(5)
    println(intersection.printState)
  }
  println(intersection.printState)

  intersection.switchLights()
  println(intersection.printState)
  intersection.timeStep()
  time += 1
  intersection.switchLights()
  println(intersection.printState)
  intersection.timeStep()
  time += 1
  println(intersection.printState)
  intersection.timeStep()
  time += 1
  println(intersection.printState)
  intersection.timeStep()
  time += 1
  println(intersection.printState)
  intersection.switchLights()
  println(intersection.printState)

  for(i <- 0 until 1000) {
    intersection.timeStep()
    time += 1
    if(time % 12 == 0) intersection.insertCar(0)
    if(time % 3 == 0) intersection.insertCar(1)
    if(time % 14 == 0) intersection.insertCar(2)
    if(time % 11 == 0) intersection.insertCar(3)
    if(time % 2 == 0) intersection.insertCar(4)
    if(time % 11 == 0) intersection.insertCar(5)
    if(time % 40 == 0) intersection.switchLights()
    println(intersection.printState)
  }

}
