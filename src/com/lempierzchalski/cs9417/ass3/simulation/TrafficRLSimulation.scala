package com.lempierzchalski.cs9417.ass3.simulation

import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.{ReinforcementLearner, LearningRateStrategies, ActionChoiceStrategies}
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel.{TrafficModelAdapter, ToggleLights, DoNothing, IntersectionAction}
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.LoopAction
import com.lempierzchalski.cs9417.ass3.engine.{TrafficLightColour, Intersection}
import com.lempierzchalski.cs9417.ass3.myUtil.Util

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */

class TrafficRLSimulation (val chooseActionChoice: ChooseActionChoice,
                           val learningRateChoice: LearningRateChoice,
                           val futureDiscount: Double) {

  type TrafficReinforcementLearner  = TrafficModelAdapter.TrafficReinforcementLearner

  val validActions = IntersectionAction.allActions

  val chooseAction = chooseActionChoice match {
    case RandomAction                 => ActionChoiceStrategies.RandomActionChoice(validActions)
    case LoopAction(actions)          => ActionChoiceStrategies.LoopActionChoice(actions)
    case RepeatAction(someAction)     => ActionChoiceStrategies.AlwaysChooseActionChoice(someAction)
    case EpsilonGreedyAction(epsilon) => ActionChoiceStrategies.EpsilonGreedyActionChoice(epsilon)
  }

  val learningRate = learningRateChoice match {
    case ConstantRateLearning(learningRateParameter)  => LearningRateStrategies.constantRate(learningRateParameter)
    case ConvergingRateLearning                       => LearningRateStrategies.convergingRate
  }

  val intersection = new Intersection()

  val takeActionWithReward = TrafficModelAdapter.takeIntersectionActionWithReward(intersection)

  private var RL: TrafficReinforcementLearner = ReinforcementLearner.construct(
    validActions,
    chooseAction,
    takeActionWithReward,
    futureDiscount,
    learningRate
  )

  def reinforcementLearner = RL

  def simWithScoring(numScores: Int,
                     timeStepsPerScore: Int,
                     insertCar: (Int, Int) => Boolean,
                     debug: Boolean = false): (TrafficReinforcementLearner, Seq[Int], Option[String]) = {
    var stateOutput = ""
    var time = 0
    val scores = for (scoreBatch <- 0 until numScores) yield {
      var score = 0
      for (timeStep <- 0 until timeStepsPerScore) {
        for ( roadIndex <- 0 until intersection.ROAD_COUNT) {
          if (insertCar(time, roadIndex)) intersection.insertCar(roadIndex)
        }
        RL     = RL.learn(TrafficModelAdapter.getState(intersection))
        score -= intersection.countWaiting()
        time  += 1
        if (debug) stateOutput += f"Time: $time\n" ++ intersection.printState()
      }
      score
    }
    (reinforcementLearner, scores, if (debug) Some(stateOutput) else None )
  }
}
