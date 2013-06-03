package com.lempierzchalski.cs9417.ass3

import scala.collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 1/06/13
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */
class Road {
  val ROAD_LENGTH = 100
  var lane: mutable.Queue[Option[Car]] = mutable.Queue.fill(ROAD_LENGTH)(None)
  var trafficLight : TrafficLightColour = Red

  def switchLights() {
    trafficLight = {
      trafficLight match {
        case Red => Green
        case Green => Red
      }
    }
  }

  def printLights() {
    trafficLight match{
      case Red => println("Light is Red")
      case Green => println("Light is Green")
    }
  }

  def insertCar() {
    lane.last match {
      case None => lane(lane.size - 1) = Some(new Car())
      case Some(_) => println("Road is full!")
    }
  }

  def printRoad() {
    for (car <- lane){
      car match {
        case None => printf("-")
        case Some(_) => printf("C")
      }
    }
    println()
  }

  def timeStep() {
    lane.head match{
      case None => {
        lane.dequeue()
        lane += None
      }
      case Some(_) => {
        trafficLight match {
          case Green => {
            lane.dequeue()
            lane += None
          } //TODO: put car onto intersection when implemented
          case Red => {
            var i = lane.indexOf(None)
            lane = lane.take(i - 1) ++ lane.drop(i)
          }
        }
      }
    }
  }
}
