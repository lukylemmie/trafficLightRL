package com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel

import com.lempierzchalski.cs9417.ass3.engine.{TrafficLightColour, Intersection}
import scala.collection.immutable
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.ReinforcementLearner

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 6/06/13
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */

class TrafficModelAdapter(val intersection: Intersection) {
  type NearestCarsState = Seq[Option[Int]]
  type TrafficLightsState = Seq[TrafficLightColour]
  type LightsCooldownState = Int
  type IntersectionState = (NearestCarsState, TrafficLightsState, LightsCooldownState)

  def getState: IntersectionState = {
    (intersection.nearestCars(), intersection.checkLights(), intersection.getCoolDown)
  }

  val validActions = immutable.Seq[IntersectionAction](DoNothing, ToggleLights)
  def takeActionWithReward(state: IntersectionState, action: IntersectionAction): (IntersectionState, Double) = {
    action match {
      case DoNothing => intersection.timeStep()
      case ToggleLights => intersection.switchLights(); intersection.timeStep()
    }
    val reward = if(intersection.isCarWaiting) -1 else 0
    val newState = getState
    (newState, reward)
  }

  private val reinforcementLearner = new ReinforcementLearner[IntersectionState, IntersectionAction](
    validActions, takeActionWithReward, futureDiscount = 0.9, learningRate = 0.1
  )

  val proportionCarInserts = 3
  def sim() {
    for (time <- 0 until 1000) {
      if (util.Random.nextInt(proportionCarInserts) == 0 ) intersection.insertCar(util.Random.nextInt(2))
      reinforcementLearner.learn(getState)
    }
  }

  def getQTable = reinforcementLearner.getQTable
}