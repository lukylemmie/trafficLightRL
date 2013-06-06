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
  private var carWaiting = false

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

  def isCarWaiting : Boolean = {
    carWaiting
  }

  def timeStep(){
    for(road <- roads){
      road.timeStep()
      carWaiting = false
      if(road.carWaitingAtIntersection()){
        carWaiting = true
      }
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

  def printState(){
    println()
    println()
    println(f"The state is:")
    println(f"coolDown = $coolDown")
    for(i <- Range(0, ROAD_COUNT)){
//      for(j <- Range(0, roads(i).ROAD_LENGTH)) print(f"*")
      println(f"Road $i:")
      roads(i).printRoad()
      println(f"Light Colour: ${roads(i).getTrafficLight}")
//      for(j <- Range(0, roads(i).ROAD_LENGTH)) print(f"*")
    }
    println(nearestCars())
  }

  def insertCar(i : Int){
    roads(i).insertCar()
  }
}
