package com.lempierzchalski.cs9417.ass3.engine

import scala.collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 1/06/13
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */

class Road (val intersectionParams : IntersectionParams) extends RoadSection {
  val laneCount = intersectionParams.numberOfLanes
  val ROAD_LENGTH = 100
  private val DEBUG = false
  private var lanes: mutable.Seq[mutable.Seq[Option[Car]]] = mutable.Seq()
  protected var trafficLight : TrafficLightColour = Red
  private var intersection : Option[IntersectionBase] = None

  initRoad()

  def checkChangeLane(lane : Int, position : Int) : (Boolean, Int) = {
    var canMove = false
    var toLane = -1
    val lastLane = intersectionParams.numberOfLanes - 1
    if(lane == 0){
      if(checkPositionEmpty(lane = 1, position) && checkPositionEmpty(lane = 1, position - 1)){
        canMove = true
        toLane = 1
      } else {
        canMove = false
      }
    } else if(lane == intersectionParams.numberOfLanes - 1){
      if(checkPositionEmpty(lane - 1, position) && checkPositionEmpty(lane - 1, position - 1)){
        canMove = true
        toLane = lane - 1
      } else {
        canMove = false
      }
    } else {
      if(checkPositionEmpty(lane + 1, position) && checkPositionEmpty(lane + 1, position - 1)){
        canMove = true
        toLane = lane + 1
      } else if(checkPositionEmpty(lane - 1, position) && checkPositionEmpty(lane - 1, position - 1)){
        canMove = true
        toLane = lane - 1
      } else {
        canMove = false
      }
    }
    (canMove, toLane)
  }

  def initRoad (){
    for(i <- 0 until laneCount){
      lanes = lanes :+ mutable.Seq.fill[Option[Car]](ROAD_LENGTH)(None)
    }
  }

  def countWaiting () : Int = {
    var sum = 0
    for(lane <- lanes){
      sum += lane.count( {
        case None => false
        case Some(car) => car.isWaiting
      })
    }
    sum
  }

  def carWaitingAtIntersection() : Boolean = {
    var carWaiting = false
    for(lane <- lanes){
      lane(0) match {
        case None => carWaiting |= false
        case Some(car) => carWaiting |= car.isWaiting
      }
    }
    carWaiting
  }

  def giveCarToIntersection(laneNum : Int) {
    lanes(laneNum)(0) = None; //TODO: pass lane(1) to intersection for animation purposes
    if(DEBUG)println("A Car went to Intersection")
  }

  def moveCar(fromLane : Int, from : Int, toLane : Int, to : Int){
    assert(from >= 0 && from < ROAD_LENGTH && to >= 0 && to < ROAD_LENGTH )
    if(lanes(toLane)(to) eq None){
        lanes(toLane)(to) = lanes(fromLane)(from)
        lanes(fromLane)(from) = None
    } else {
      if(DEBUG)println("Can't move there because there's something there!")
    }
  }

  //return True if position is empty in lane
  def checkPositionEmpty(lane : Int, position : Int) : Boolean = {
    assert(position >= 0 && position < ROAD_LENGTH)
    lanes(lane)(position) match {
      case None => true
      case Some(_) => false
    }
  }

  def getTrafficLight : TrafficLightColour = {
    trafficLight
  }

  def nearestCars() : Seq[Option[Int]] = {
    var nearestCars : mutable.Seq[Option[Int]] = mutable.Seq()
    var nearest : Option[Option[Car]] = None
    for(lane <- lanes){
      var tempLane = lane.filter(_ ne None)
      if(DEBUG)println(f"old = ${lane.find(_ ne None)}")
      if(DEBUG && tempLane.size > 0)println(f"tempLane(0) = ${tempLane(0)}")
      for(i <- 0 until intersectionParams.numNearestCarsViewed){
        if(tempLane.size > 0 && i < tempLane.size){
          nearest = Some(tempLane(i))
          if(DEBUG)println(f"nearest = $nearest")
        } else {
          nearest = None
        }
        nearest match {
          case None => nearestCars = nearestCars :+ None
          case Some(optionCar) => {
            val i = lane.indexOf(optionCar)
            if (i > intersectionParams.nearestCarViewDepth){
              nearestCars = nearestCars :+ None
            } else {
              nearestCars = nearestCars :+ Some(i)
            }
          }
        }
      }
    }
    if(DEBUG)println(f"nearestCars = $nearestCars")
    nearestCars
  }

  def printNearest(){
    for(position <- nearestCars()){
      position match {
        case None => println(f"Nearest car is: None")
        case Some(i) => println(f"Nearest car is: $i")
      }
    }
  }

  def setIntersection(aIntersection : Option[IntersectionBase]){
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

  def setLights(lightColour : TrafficLightColour){
    trafficLight = lightColour
  }

  def switchLights() {
    trafficLight = {
      trafficLight match {
        case Red => Green
        case Green => Red
        case Amber => println("WTF NO AMBER LIGHT POSSIBLE!!!"); Amber
      }
    }
  }

  def printLights() {
    trafficLight match{
      case Red => println("Light is Red")
      case Green => println("Light is Green")
      case Amber => println("Light is Amber")
    }
  }

  def insertCar(laneNum : Int) {
    val lane = lanes(laneNum)
    if(DEBUG)println(f"laneNum = $laneNum")
    if(DEBUG)println(f"lanes = $lanes")
    lane.last match {
      case None => lane(lane.size - 1) = Some(new Car(this, lane.size - 1, laneNum, intersectionParams.changeLanes))
      case Some(_) => println("Either Road is Full or Time Step Required!")
    }
  }

  def printRoad() : String = {
    var output : String = ""
    for(lane <- lanes){
      for (car <- lane){
        car match {
          case None => output += "-"
          case Some(_) => output += "C"
        }
      }
      output += "\n"
    }
    output
  }

  def timeStep() {
    val transposedLanes = lanes.transpose
    for(lane <- transposedLanes){
      for(optCar <- lane){
        optCar match {
          case None => // nothing there
          case Some(car) => car.move()
        }
      }
    }
  }

}

class RoadAmber(intersectionParams : IntersectionParams) extends Road(intersectionParams : IntersectionParams) {
  var time = 0

  override def switchLights() {
    (trafficLight, time) match {
      case (Red, 0) => time = Amber.duration
      case (Red, 1) => time = 0; trafficLight = Green
      case (Red, _) => time -= 1
      case (Green, _) => time = Amber.duration; trafficLight = Amber
      case (Amber, 1) => time = 0; trafficLight = Red
      case (Amber, _) => time -= 1
    }
  }
}