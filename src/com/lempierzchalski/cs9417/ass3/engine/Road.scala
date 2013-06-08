package com.lempierzchalski.cs9417.ass3.engine

import scala.collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 1/06/13
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */

class Road (val laneCount : Int = 1) extends RoadSection {
  val ROAD_LENGTH = 100
  private val DEBUG = false
  private val lanes: mutable.Seq[mutable.Seq[Option[Car]]] = mutable.Seq()
  protected var trafficLight : TrafficLightColour = Red
  private var intersection : Option[Intersection] = None

  initRoad()

  def initRoad (){
    for(i <- 0 until laneCount) lanes :+ mutable.Seq.fill(ROAD_LENGTH)(None)
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

  def giveCarToIntersection(laneNum : Int = 0) {
    lanes(laneNum)(0) = None; //TODO: pass lane(1) to intersection for animation purposes
    if(DEBUG)println("A Car went to Intersection")
  }

  def moveCar(fromLane : Int = 0, from : Int, toLane : Int = 0, to : Int){
    assert(from >= 0 && from < ROAD_LENGTH && to >= 0 && to < ROAD_LENGTH )
    if(lanes(toLane)(to) eq None){
        lanes(toLane)(to) = lanes(fromLane)(from)
        lanes(fromLane)(from) = None
    } else {
      if(DEBUG)println("Can't move there because there's something there!")
    }
  }

  //return True if position is empty in lane
  def checkPositionEmpty(lanePosition : Int = 0, position : Int) : Boolean = {
    assert(position >= 0 && position < ROAD_LENGTH)
    lanes(lanePosition)(position) match {
      case None => true
      case Some(_) => false
    }
  }

  def getTrafficLight : TrafficLightColour = {
    trafficLight
  }

  def nearestCar() : mutable.Seq[Option[Int]] = { //TODO: Refactor?
    val MAX_RETURN = 8
    val nearestCars : mutable.Seq[Option[Int]] = mutable.Seq()
    for(lane <- lanes){
      val nearest = lane.find(_ ne None)
      nearest match {
        case None => nearestCars :+ None
        case Some(optionCar) => {
          val i = lane.indexOf(optionCar)
          if (i > MAX_RETURN){
            nearestCars :+ None
          } else {
            nearestCars :+ Some(i)
          }
        }
      }
    }
    nearestCars
  }

  def printNearest(){
    for(position <- nearestCar()){
      position match {
        case None => println(f"Nearest car is: None")
        case Some(i) => println(f"Nearest car is: $i")
      }
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

  def insertCar(laneNum : Int = util.Random.nextInt(laneCount)) {
    val lane = lanes(laneNum)
    lane.last match {
      case None => lane(lane.size - 1) = Some(new Car(this, lane.size - 1))
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
    for(lane <- lanes){
      for(optCar <- lane){
        optCar match {
          case None => // nothing there
          case Some(car) => car.move()
        }
      }
    }
  }

}

class RoadAmber extends Road {
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