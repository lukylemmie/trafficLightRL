package com.lempierzchalski.cs9417.ass3.engine

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 1/06/13
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
class Car(createdOn : RoadSection, startPosition : Int, startLane : Int, canChange : Boolean = false) {
  //TODO: Going to turn
  protected val DEBUG = false
  protected var lane = startLane
  protected var position = startPosition
  protected var roadSection = createdOn
  protected var waiting = false

  def isWaiting : Boolean = waiting

  def move(){
    moveOnce()
  }

  def moveOnce(){
//    println("Moving car!")
    roadSection match {
      case section : Road => {
        if(position == 0){
          section.getTrafficLight match {
            case Red => waiting = true; if(DEBUG) println("Car waiting, minus points!")
            case Green => waiting = false; section.giveCarToIntersection(lane)
            case Amber => waiting = false; section.giveCarToIntersection(lane)
          }
        } else if(section.checkPositionEmpty(lane, position = position - 1)){
          waiting = false
          section.moveCar(fromLane = lane, from = position, toLane = lane, to = position - 1)
          position -= 1
        } else if (canChange){
          var changeLane = false
          var toLane = -1
          var something = (changeLane, toLane)
          something = section.checkChangeLane(lane,position)
          changeLane = something._1
          toLane = something._2
          if(changeLane){
            waiting = false
            section.moveCar(lane, position, toLane, position - 1)
            lane = toLane
            position -= 1
          } else {
            waiting = true
          }
        } else {
          waiting = true
        }
      }
      case section : IntersectionBase => {
        if(DEBUG)println("Car is on an Intersection!")
        //TODO: movement of car in intersection for animation purposes
      }
    }
  }
}

class fastCar(createdOn : RoadSection, startPosition : Int, lane : Int) extends Car(createdOn : RoadSection, startPosition : Int, lane : Int) {
  override def move(){
    moveOnce()
    moveOnce()
  }
}
