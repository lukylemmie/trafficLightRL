package com.lempierzchalski.cs9417.ass3.engine

import com.lempierzchalski.cs9417.ass3.engine.interface.SimulationIntersection
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.{RedGreen, LightColoursChoice}

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
      roads = roads :+ new Road(intersectionParams)
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
    roads.flatMap(_.nearestCars())
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

  def insertCar(entranceIndex : Int/*, carSpawnProportions : CarSpawnProportions*/){
    if(DEBUG)println(f"entranceIndex = $entranceIndex")
    if(DEBUG)println(f"entranceIndex / intersectionParams.numberOfIncomingRoads = ${entranceIndex / intersectionParams.numberOfIncomingRoads}")
    if(DEBUG)println(f"entranceIndex % intersectionParams.numberOfIncomingRoads = ${entranceIndex % intersectionParams.numberOfIncomingRoads}")
    roads(entranceIndex / intersectionParams.numberOfLanes).insertCar(entranceIndex % intersectionParams.numberOfLanes)
  }

  def carEntranceCount: Int = intersectionParams.numberOfIncomingRoads * intersectionParams.numberOfLanes
}

class IntersectionAmber (intersectionParams: IntersectionParams) extends IntersectionBase (intersectionParams: IntersectionParams) {
  var lightsChanging = 0

  override def roadSetup(){
    for(i <- Range(0, intersectionParams.numberOfIncomingRoads)){
      roads = roads :+ new RoadAmber(intersectionParams)
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

case class IntersectionParams(numberOfLanes           : Int = 1,
                              lightColours            : LightColoursChoice = RedGreen,
                              numberOfIncomingRoads   : Int = 2,
                              variableCarSpeed        : Boolean = false,
                              changeLanes             : Boolean = false,
                              crashes                 : Boolean = false,
                              nearestCarViewDepth     : Int = 8,
                              numNearestCarsViewed    : Int = 1) {
  override def toString: String = {
    f"""Intersection parameters:
       |  numberOfLanes:            $numberOfLanes
       |  lightColours:             $lightColours
       |  numberOfIncomingRoads:    $numberOfIncomingRoads
       |  variableCarSpeed:         $variableCarSpeed
       |  changeLanes:              $changeLanes
       |  crashes:                  $crashes
       |  nearestCarViewDepth:      $nearestCarViewDepth
       |  numNearestCarsViewed:     $numNearestCarsViewed""".stripMargin
  }
}