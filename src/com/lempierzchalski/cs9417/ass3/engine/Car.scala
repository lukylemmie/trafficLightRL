package com.lempierzchalski.cs9417.ass3.engine

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 1/06/13
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
class Car(createdOn : RoadSection, startPosition : Int, lane : Int) {
  //TODO: Going to turn
  private val DEBUG = false
  private var position = startPosition
  private var roadSection = createdOn
  private var waiting = false

  def isWaiting : Boolean = waiting

  def move(){
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

}
