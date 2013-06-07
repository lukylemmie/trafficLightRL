package com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel

import com.lempierzchalski.cs9417.ass3.engine.{TrafficLightColour, Intersection}
import scala.collection.immutable
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.{ActionChoiceStrategies, ReinforcementLearner}

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 6/06/13
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */

class TrafficModelAdapter(val intersection: Intersection,
                          epsilonGreedyParameter: Double,
                          futureDiscount: Double,
                          learningRate: Double) {
  type NearestCarsState = Seq[Option[Int]]
  type TrafficLightsState = Seq[TrafficLightColour]
  type LightsCooldownState = Int
  type IntersectionState = (NearestCarsState, TrafficLightsState, LightsCooldownState)

  def getState: IntersectionState = {
    (intersection.nearestCars(), intersection.checkLights(), intersection.getCoolDown)
  }

  val validIntersectionActions = immutable.Set[IntersectionAction](DoNothing, ToggleLights)

  def takeIntersectionActionWithReward(state: IntersectionState,
                                       action: IntersectionAction
                                       ): (IntersectionState, Double) = {
    action match {
      case DoNothing => intersection.timeStep()
      case ToggleLights => intersection.switchLights(); intersection.timeStep()
    }
    val newState = getState
    val reward = if(intersection.isCarWaiting) -1 else 0
    (newState, reward)
  }

  private var reinforcementLearner = ReinforcementLearner.construct[IntersectionState, IntersectionAction](
    validActions = validIntersectionActions,
    chooseAction = ActionChoiceStrategies.EpsilonGreedyActionChoice(epsilon = epsilonGreedyParameter),
    takeActionWithReward = takeIntersectionActionWithReward,
    futureDiscount,
    learningRate
  )

  def getReinforcementLearner = reinforcementLearner

  def sim(endTime: Int, proportionCarInserts: Int) {
    for (time <- 0 until endTime) {
      for ( i <- 0 until intersection.ROAD_COUNT) {
        if (util.Random.nextInt(proportionCarInserts) == 0) intersection.insertCar(i)
      }
      reinforcementLearner = reinforcementLearner.learn(getState)
    }
  }

  def simWithScoring(numScores: Int, timeStepsPerScore: Int, proportionCarInserts: Int): Seq[Int] = {
    for (scoreBatch <- 0 until numScores) yield {
      var score = 0
      for (timeStep <- 0 until timeStepsPerScore) {
        for ( i <- 0 until intersection.ROAD_COUNT) {
          if (util.Random.nextInt(proportionCarInserts) == 0) intersection.insertCar(i)
        }
        reinforcementLearner = reinforcementLearner.learn(getState)
        score -= intersection.countWaiting()
      }
      score
    }
  }
}