package com.lempierzchalski.cs9417.ass3.engine

import scala.collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 1/06/13
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
class Intersection extends RoadSection {
  //TODO: IMPLEMENT
  private val DEBUG = false
  private val SWITCH_COOL_DOWN = 3 // time steps
  val ROAD_COUNT = 2

  private var coolDown = 0
  private var roads = List[Road]()

  roadSetup()

  def roadSetup(){
    for(i <- Range(0,ROAD_COUNT)){
      roads = roads :+ new Road()
      roads(i).setIntersection(Some(this))
      if(i % 2 == 0){
        roads(i).switchLights()
      }
    }
  }

  def countWaiting() : Int = {
    roads.map(_.countWaiting()).sum
  }

  def isCarWaiting : Boolean = {
    var carWaiting = false
    for(road <- roads){
      if(road.carWaitingAtIntersection()){
        carWaiting = true
      }
    }
    carWaiting
  }

  def timeStep(){
    for(road <- roads){
      road.timeStep()
    }
    if(coolDown > 0) coolDown -= 1
  }

  def nearestCars() : Seq[Option[Int]] = {
    roads.map(_.nearestCar())
  }

  def checkLights() : Seq[TrafficLightColour] = {
    roads.map(_.getTrafficLight)
  }

  def getCoolDown: Int = {
    coolDown
  }

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

  def printState() : String = {
    var output = ""
    output += f"\n\n"
    output += f"The state is:\n"
    output += f"coolDown = $coolDown\n"
    for(i <- Range(0, ROAD_COUNT)){
//      for(j <- Range(0, roads(i).ROAD_LENGTH)) print(f"*")
      output += f"Road $i:\n"
      output += roads(i).printRoad()
      output += f"Light Colour: ${roads(i).getTrafficLight}\n"
//      for(j <- Range(0, roads(i).ROAD_LENGTH)) print(f"*")
    }
    output += f"nearestCars() = ${nearestCars()}\n"
    output += f"carWaiting = $isCarWaiting\n"
    output
  }

  def insertCar(i : Int){
    roads(i).insertCar()
  }
}
