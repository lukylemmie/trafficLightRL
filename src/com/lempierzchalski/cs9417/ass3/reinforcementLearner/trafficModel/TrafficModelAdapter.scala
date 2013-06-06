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
    val newState = getState
    val reward = if(intersection.isCarWaiting) -1 else 0
    (newState, reward)
  }

  private val reinforcementLearner = new ReinforcementLearner[IntersectionState, IntersectionAction](
    validActions, takeActionWithReward, futureDiscount = 0.9, learningRate = 0.1
  )

  val proportionCarInserts = 10
  def sim(endTime: Int) {
    for (time <- 0 until endTime) {
      for ( i <- 0 until intersection.ROAD_COUNT) {
        if (util.Random.nextInt(proportionCarInserts) == 0) intersection.insertCar(i)
      }
      //intersection.printState()
      reinforcementLearner.learn(getState)
    }
  }

  def getQTable = reinforcementLearner.getQTable
  def getQValueTable = reinforcementLearner.getQValueTable
  def getQCountTable = reinforcementLearner.getQCountTable
}