package com.lempierzchalski.cs9417.ass3.engine

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 1/06/13
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
class Car(createdOn : RoadSection, startPosition : Int) {
  //TODO: Going to turn
  private var position = startPosition
  private var roadSection = createdOn
  private var waiting = false

  def getWaiting : Boolean = {
    waiting
  }

  def move(){
//    println("Moving car!")
    roadSection match {
      case section : Road => {
//        println("Car is on a Road!")
//        println(f"Checking position: $position")
//        println(f"section.checkPositionEmpty(${position - 1}) = ${section.checkPositionEmpty(position - 1)}")
        if(position == 0){
          section.getTrafficLight match {
            case Red => waiting = true; println("Car waiting, minus points!")
            case Green => waiting = false; section.giveCarToIntersection()
          }
        } else if(section.checkPositionEmpty(position - 1)){
          waiting = false
          section.moveCar(position, position - 1)
          position -= 1
        } else {
          waiting = true
        }
      }
      case section : Intersection => {
        println("Car is on an Intersection!")
        //TODO: movement of car in intersection
      }
    }
  }
}
