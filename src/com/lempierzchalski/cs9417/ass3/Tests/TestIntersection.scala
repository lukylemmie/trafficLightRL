package com.lempierzchalski.cs9417.ass3.Tests

import com.lempierzchalski.cs9417.ass3.Intersection

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 4/06/13
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
object TestIntersection extends App {
  val intersection = new Intersection

  intersection.printState()
  intersection.timeStep()
  intersection.printState()
  intersection.insertCar(0)
  intersection.printState()
  intersection.timeStep()
  intersection.insertCar(1)
  intersection.printState()

  for(i <- 0 until 20) intersection.timeStep()
  intersection.printState()

  intersection.insertCar(0)
  intersection.insertCar(1)
  intersection.printState()
  intersection.timeStep()
  intersection.insertCar(1)
  intersection.printState()

  for(i <- 0 until 80) intersection.timeStep()
  intersection.printState()

  for(i <- 0 until 20) intersection.timeStep()
  intersection.printState()

  intersection.switchLights()
  intersection.printState()
  intersection.timeStep()
  intersection.switchLights()
  intersection.printState()
  intersection.timeStep()
  intersection.timeStep()
  intersection.timeStep()
  intersection.printState()
  intersection.switchLights()
  intersection.printState()
}
