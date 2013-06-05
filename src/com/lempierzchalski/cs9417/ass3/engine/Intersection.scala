package com.lempierzchalski.cs9417.ass3.engine

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 1/06/13
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
class Intersection extends RoadSection {
  //TODO: IMPLEMENT
  private val SWITCH_COOL_DOWN = 3 // time steps
  private val ROAD_COUNT = 2

  private var coolDown = 0
  private val roads = new Array[Road](ROAD_COUNT)
  private var score = 1000;

  roadSetup()

  def roadSetup(){
    for(i <- Range(0,ROAD_COUNT)){
      roads(i) = new Road
      roads(i).setIntersection(Some(this))
      if(i % 2 == 0){
        roads(i).switchLights()
      }
    }
  }

  def timeStep(){
    for(road <- roads){
      road.timeStep()
      if(road.carWaitingAtIntersection()){
        score -= 1
      }
    }
    if(coolDown > 0) coolDown -= 1
  }

  def nearestCar(i : Int) : Option[Int] = {
    roads(i).nearestCar()
  }

  def checkLight(i: Int) : TrafficLightColour = {
    roads(i).getTrafficLight
  }

  def getCoolDown: Int = {
    coolDown
  }

  def switchLights(){
    if(coolDown > 0){
      println("Can't change lights yet")
    } else {
      for(road <- roads){
        road.switchLights()
      }
      coolDown = SWITCH_COOL_DOWN
    }
  }

  def printState(){
    println()
    println()
    println(f"The current score( $score ) and state is:")
    println(f"coolDown = $coolDown")
    for(i <- Range(0, ROAD_COUNT)){
//      for(j <- Range(0, roads(i).ROAD_LENGTH)) print(f"*")
      println(f"Road $i:")
      roads(i).printRoad()
      println(f"Light Colour: ${roads(i).getTrafficLight}")
//      for(j <- Range(0, roads(i).ROAD_LENGTH)) print(f"*")
    }
  }

  def insertCar(i : Int){
    roads(i).insertCar()
  }
}
