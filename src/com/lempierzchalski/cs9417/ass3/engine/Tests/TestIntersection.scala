package com.lempierzchalski.cs9417.ass3.Tests

import com.lempierzchalski.cs9417.ass3.engine.IntersectionBase

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 4/06/13
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
object TestIntersection extends App {
  val intersection = new IntersectionBase

  println(intersection.printState())
  intersection.timeStep()
  println(intersection.printState())
  intersection.insertCar(0)
  println(intersection.printState())
  intersection.timeStep()
  intersection.insertCar(1)
  println(intersection.printState())

  for(i <- 0 until 20) intersection.timeStep()
  println(intersection.printState())

  intersection.insertCar(0)
  intersection.insertCar(1)
  println(intersection.printState())
  intersection.timeStep()
  intersection.insertCar(1)
  println(intersection.printState())

  for(i <- 0 until 75) intersection.timeStep()
  println(intersection.printState())
  intersection.nearestCars()

  for(i <- 0 until 25) intersection.timeStep()
  println(intersection.printState())

  intersection.switchLights()
  println(intersection.printState())
  intersection.timeStep()
  intersection.switchLights()
  println(intersection.printState())
  intersection.timeStep()
  println(intersection.printState())
  intersection.timeStep()
  println(intersection.printState())
  intersection.timeStep()
  println(intersection.printState())
  intersection.switchLights()
  println(intersection.printState())
}
