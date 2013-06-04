package com.lempierzchalski.cs9417.ass3

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 1/06/13
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
class Intersection {
  //TODO: IMPLEMENT
  val SWITCH_COOL_DOWN = 3 // time steps
  val ROAD_COUNT = 2

  var switchCoolDown = 0
  val roads = new Array[Road](ROAD_COUNT)

  roadSetup()

  def roadSetup(){
    for(i <- Range(0,ROAD_COUNT)){
      roads(i) = new Road
      roads(i).setIntersection(this)
    }
    roads(0).switchLights()
  }

  def timeStep(){
    for(road <- roads){
      road.timeStep()
    }

  }
}
