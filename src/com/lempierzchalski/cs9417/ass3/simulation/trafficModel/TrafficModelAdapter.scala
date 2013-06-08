package com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel

import com.lempierzchalski.cs9417.ass3.engine.{TrafficLightColour, Intersection}
import scala.collection.immutable
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.{LearningRateStrategies, ActionChoiceStrategies, ReinforcementLearner}
import com.lempierzchalski.cs9417.ass3.myUtil.Util
import com.lempierzchalski.cs9417.ass3.engine.interface.SimulationIntersection

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 6/06/13
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */

object TrafficModelAdapter {

  type NearestCarsState             = Seq[Option[Int]]
  type TrafficLightsState           = Seq[TrafficLightColour]
  type LightsCooldownState          = Int
  type IntersectionState            = (NearestCarsState, TrafficLightsState, LightsCooldownState)
  type TrafficReinforcementLearner  = ReinforcementLearner[IntersectionState, IntersectionAction]

  def getState(intersection: SimulationIntersection): IntersectionState = {
    (intersection.nearestCars, intersection.checkLights, intersection.getCooldown)
  }

  def takeIntersectionActionWithReward(intersection: SimulationIntersection):
      (IntersectionState, IntersectionAction) => (IntersectionState, Double) = {
    (state, action) => {
      action match {
        case DoNothing => intersection.timeStep()
        case ToggleLights => intersection.switchLights(); intersection.timeStep()
      }
      (getState(intersection), if (intersection.isCarWaiting) -1 else 0)
    }
  }
}