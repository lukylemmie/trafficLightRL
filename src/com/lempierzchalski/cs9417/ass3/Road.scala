package com.lempierzchalski.cs9417.ass3

import scala.collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 1/06/13
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */

class Road {
  val ROAD_LENGTH = 100
  private var lane : mutable.Queue[Option[Car]] = mutable.Queue.fill(ROAD_LENGTH)(None)
  private var trafficLight : TrafficLightColour = Red
  private var intersection : Option[Intersection] = None

  def getTrafficLight() : TrafficLightColour = {
    trafficLight
  }

  def nearestCar() : Option[Int] = { //TODO: Refactor?
    val MAX_RETURN = 8
    var output = lane.find(_ ne None)
    if(output ne None){
      var i = lane.indexOf(output.get)
  //    println(f"Inside nearestCar: $i $output") //debug statement
      if(i > MAX_RETURN)
        None
      else
        Option(i)
    } else {
      None
    }
  }

  def printNearest(){
    nearestCar() match {
      case None => println(f"Nearest car is: None")
      case Some(i) => println(f"Nearest car is: $i")
    }
  }

  def setIntersection(aIntersection : Option[Intersection]){
    aIntersection match {
      case None => println("No intersection found")
      case Some(_) => intersection = aIntersection
    }
  }

  def checkIntersection() : Boolean = {
    intersection match {
      case Some(_) => true
      case None => false
    }
  }

  def switchLights() {
    trafficLight = {
      trafficLight match {
        case Red => Green
        case Green => Red
      }
    }
  }

  def printLights() {
    trafficLight match{
      case Red => println("Light is Red")
      case Green => println("Light is Green")
    }
  }

  def insertCar() {
    lane.last match {
      case None => lane(lane.size - 1) = Some(new Car())
      case Some(_) => println("Either Road is Full or Time Step Required!")
    }
  }

  def printRoad() {
    for (car <- lane){
      car match {
        case None => printf("-")
        case Some(_) => printf("C")
      }
    }
    println()
  }

  def timeStep() {
    lane.head match{
      case None => {
        lane.dequeue()
        lane += None
      }
      case Some(_) => {
        trafficLight match {
          case Green => {
            lane.dequeue() //TODO: put car onto intersection when implemented
            lane += None
          }
          case Red => {
            var i = lane.indexOf(None)
            lane = lane.take(i) ++ lane.drop(i + 1)
            lane += None
            //println("Debug: car at red light")
          }
        }
      }
    }
  }
}
