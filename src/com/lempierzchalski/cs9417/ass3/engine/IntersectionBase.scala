package com.lempierzchalski.cs9417.ass3.engine

import scala.collection.mutable
import com.lempierzchalski.cs9417.ass3.engine.interface.SimulationIntersection
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.IntersectionParams

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 1/06/13
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
class IntersectionBase (val intersectionParams: IntersectionParams) extends RoadSection with SimulationIntersection {
  //TODO: IMPLEMENT
  protected val DEBUG = false
  protected val SWITCH_COOL_DOWN = 3 // time steps

  protected var coolDown = 0
  protected var roads = List[Road]()

  roadSetup()

  def roadSetup(){
    for(i <- Range(0,intersectionParams.numberOfIncomingRoads)){
      roads = roads :+ new Road(intersectionParams.numberOfLanes)
      roads(i).setIntersection(Some(this))
      if(i % 2 == 0){
        roads(i).setLights(Green)
      }
    }
  }

  def countWaiting: Int = {
    roads.map(_.countWaiting()).sum
  }

  def isCarWaiting : Boolean = {
    !roads.forall(!_.carWaitingAtIntersection())
  }

  def timeStep(){
    for(road <- roads){
      road.timeStep()
    }
    if(coolDown > 0) coolDown -= 1
  }

  def nearestCars: Seq[Option[Int]] = {
    roads.flatMap(_.nearestCar())
  }

  def checkLights: Seq[TrafficLightColour] = {
    roads.map(_.getTrafficLight)
  }

  def getCoolDown: Int = coolDown

  def switchLights(){
    if(coolDown > 0){
      if(DEBUG)println("Can't change lights yet")
    } else {
      for(road <- roads){
        road.switchLights()
      }
      coolDown = SWITCH_COOL_DOWN
    }
  }

  def printState : String = {
    var output = ""
    output += f"\n\n"
    output += f"The state is:\n"
    output += f"coolDown = $coolDown\n"
    for(i <- Range(0, intersectionParams.numberOfIncomingRoads)){
//      for(j <- Range(0, roads(i).ROAD_LENGTH)) print(f"*")
      output += f"Road $i:\n"
      output += f"Light Colour: ${roads(i).getTrafficLight}\n"
      output += roads(i).printRoad()
//      for(j <- Range(0, roads(i).ROAD_LENGTH)) print(f"*")
    }
    output += f"nearestCars() = $nearestCars\n"
    output += f"carWaiting = $isCarWaiting\n"
    output
  }

  def insertCar(entranceIndex : Int){
    roads(entranceIndex / intersectionParams.numberOfIncomingRoads).insertCar(entranceIndex % intersectionParams.numberOfIncomingRoads)
  }

  def carEntranceCount: Int = intersectionParams.numberOfIncomingRoads * intersectionParams.numberOfLanes
}

class IntersectionAmber (intersectionParams: IntersectionParams) extends IntersectionBase (intersectionParams: IntersectionParams) {
  var lightsChanging = 0

  override def roadSetup(){
    for(i <- Range(0, intersectionParams.numberOfIncomingRoads)){
      roads = roads :+ new RoadAmber(intersectionParams.numberOfLanes)
      roads(i).setIntersection(Some(this))
      if(i % 2 == 0){
        roads(i).setLights(Green)
      }
    }
  }

  override def timeStep(){
    for(road <- roads){
      if(lightsChanging > 0) road.switchLights()
      road.timeStep()
    }
    if(lightsChanging == 1) coolDown = SWITCH_COOL_DOWN
    if(lightsChanging > 0) lightsChanging -= 1
    if(coolDown > 0) coolDown -= 1
  }

  override def switchLights(){
    if(coolDown > 0 || lightsChanging > 0){
      if(DEBUG)println("Can't change lights yet")
    } else {
      for(road <- roads){
        road.switchLights()
      }
      lightsChanging = Amber.duration
    }
  }

  override def printState: String = {
    var output = ""
    output += f"\n\n"
    output += f"The state is:\n"
    output += f"coolDown = $coolDown\n"
    output += f"lightsChanging = $lightsChanging\n"
    for(i <- Range(0, intersectionParams.numberOfIncomingRoads)){
      //      for(j <- Range(0, roads(i).ROAD_LENGTH)) print(f"*")
      output += f"Road $i:\n"
      output += f"Light Colour: ${roads(i).getTrafficLight}\n"
      output += roads(i).printRoad()
      //      for(j <- Range(0, roads(i).ROAD_LENGTH)) print(f"*")
    }
    output += f"nearestCars() = ${nearestCars.flatten}\n"
    output += f"carWaiting = $isCarWaiting\n"
    output
  }
}