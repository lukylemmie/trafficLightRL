package com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel

import com.lempierzchalski.cs9417.ass3.engine.{TrafficLightColour, Intersection}
import scala.collection.immutable
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.ReinforcementLearner
import util.Random

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
  def chooseActionWithEpsilonGreedy(state: IntersectionState,
                                    qTable: Map[(IntersectionState, IntersectionAction), (Double, Int)]
                                    ): IntersectionAction = {
    if (Random.nextDouble() < epsilonGreedyParameter) {
      validIntersectionActions.toSeq(Random.nextInt(validIntersectionActions.size))
    } else {
      val validActionsSeq = validIntersectionActions.toSeq
      val actionUtilities = validActionsSeq.map( (state, _) ).map( qTable.getOrElse(_, (0.0, 0))._1 )
      val actionUtilityPairs = validActionsSeq.zip(actionUtilities)
      val (chosenAction, _) = actionUtilityPairs.maxBy( _._2 )
      chosenAction
    }
  }

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
    chooseAction = chooseActionWithEpsilonGreedy,
    takeActionWithReward = takeIntersectionActionWithReward,
    futureDiscount,
    learningRate
  )

  def getReinforcementLearner = reinforcementLearner

  val proportionCarInserts = 10
  def sim(endTime: Int) {
    for (time <- 0 until endTime) {
      for ( i <- 0 until intersection.ROAD_COUNT) {
        if (util.Random.nextInt(proportionCarInserts) == 0) intersection.insertCar(i)
      }
      //intersection.printState()
      reinforcementLearner = reinforcementLearner.learn(getState)
    }
  }

  def simWithScoring(numScores: Int, timeStepsPerScore: Int): Seq[Int] = {
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