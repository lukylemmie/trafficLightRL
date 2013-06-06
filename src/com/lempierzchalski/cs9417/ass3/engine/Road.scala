package com.lempierzchalski.cs9417.ass3.engine

import scala.collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 1/06/13
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */

class Road extends RoadSection {
  val ROAD_LENGTH = 100
  private val DEBUG = false
  private val lane: mutable.Seq[Option[Car]] = mutable.Seq.fill(ROAD_LENGTH)(None)
  private var trafficLight : TrafficLightColour = Red
  private var intersection : Option[Intersection] = None

  def carWaitingAtIntersection() : Boolean = {
    lane(0) match {
      case None => false
      case Some(car) => car.getWaiting
    }
  }

  def giveCarToIntersection(){
    lane(0) = None; //TODO: pass lane(1) to intersection
    if(DEBUG)println("A Car went to Intersection")
  }

  def moveCar(from : Int, to : Int){
    assert(from >= 0 && from < ROAD_LENGTH && to >= 0 && to < ROAD_LENGTH )
    if(lane(to) eq None){
      lane(to) = lane(from)
      lane(from) = None
    } else {
      if(DEBUG)println("Can't move there because there's something there!")
    }
  }

  //return True if position is empty in lane
  def checkPositionEmpty(position : Int) : Boolean = {
    assert(position >= 0 && position < ROAD_LENGTH)
    lane(position) match {
      case None => true
      case Some(_) => false
    }
  }

  def getTrafficLight : TrafficLightColour = {
    trafficLight
  }

  def nearestCar() : Option[Int] = { //TODO: Refactor?
    val MAX_RETURN = 8
    val output = lane.find(_ ne None)
    output match {
      case None => None
      case Some(optionCar) => {
        val i = lane.indexOf(optionCar)
        if (i > MAX_RETURN) None else Some(i)
      }
    }
  }
  //    if(output ne None){
  //      var i = lane.indexOf(output.get)
  //  //    println(f"Inside nearestCar: $i $output") //debug statement
  //      if(i > MAX_RETURN)
  //        None
  //      else
  //        Some(i)
  //    } else {
  //      None
  //    }

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
      case None => lane(lane.size - 1) = Some(new Car(this, lane.size - 1))
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
    for(optCar <- lane){
      optCar match {
        case None => // nothing there
        case Some(car) => car.move()
      }
    }
  }

}
