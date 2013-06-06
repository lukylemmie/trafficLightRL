package com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel

import com.lempierzchalski.cs9417.ass3.engine.{Intersection, Red, Green, TrafficLightColour}

import collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 6/06/13
 * Time: 6:48 PM
 * To change this template use File | Settings | File Templates.
 */
object StateSpaceEnumerator extends App {

  val trafficModelAdapter = new TrafficModelAdapter(new Intersection())

  val set: mutable.Set[(trafficModelAdapter.type#IntersectionState, IntersectionAction)] = mutable.Set()
  val nearestCarsStates = ((0 to 8).map(Some(_)) :+ None).toSeq
  val trafficLightStates = Seq(Seq(Red, Green), Seq(Green, Red))
  val lightsCooldownStates = (0 to 2).toSeq
  val actions = Seq(DoNothing, ToggleLights)

  for (nearestCarState1 <- nearestCarsStates; nearestCarState2 <- nearestCarsStates;
       trafficLightState <- trafficLightStates; lightsCooldownState <- lightsCooldownStates;
       action <- actions) {
    val nearestCarsState = Seq(nearestCarState1, nearestCarState2)
    val state: (trafficModelAdapter.type#IntersectionState, IntersectionAction) = ((nearestCarsState, trafficLightState, lightsCooldownState), action)
    set += state
  }
}