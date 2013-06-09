package com.lempierzchalski.cs9417.ass3.simulation

import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.{ReinforcementLearner, LearningRateStrategies, ActionChoiceStrategies}
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel.{TrafficModelAdapter, IntersectionAction}
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.LoopAction
import com.lempierzchalski.cs9417.ass3.engine.interface.Intersection
import java.io.PrintWriter

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */

class TrafficRLSimulation (val chooseActionChoice:     ChooseActionChoice,
                           val learningRateChoice:     LearningRateChoice,
                           val carSpawnChoice:         CarSpawnChoice,
                           val numberOfLanes:          Int,
                           val lightColours:           LightColoursChoice,
                           val numberOfIncomingRoads:  Int,
                           val futureDiscount:         Double) {

  type State                        = TrafficModelAdapter.IntersectionState
  type Action                       = IntersectionAction
  type TrafficReinforcementLearner  = TrafficModelAdapter.TrafficReinforcementLearner

  val validActions = IntersectionAction.allActions

  val chooseAction = chooseActionChoice match {
    case RandomAction                 => ActionChoiceStrategies.RandomActionChoice[State, Action](validActions)
    case LoopAction(actions)          => ActionChoiceStrategies.LoopActionChoice[State, Action](actions)
    case RepeatAction(someAction)     => ActionChoiceStrategies.AlwaysChooseActionChoice[State, Action](someAction)
    case EpsilonGreedyAction(epsilon) => ActionChoiceStrategies.EpsilonGreedyActionChoice[State, Action](epsilon)
  }

  val learningRate = learningRateChoice match {
    case ConstantRateLearning(learningRateParameter)  => LearningRateStrategies.constantRate[State, Action](learningRateParameter)
    case ConvergingRateLearning                       => LearningRateStrategies.convergingRate[State, Action]
  }

  val carSpawn = carSpawnChoice match {
    case UniformRateCarSpawn(probability) => CarSpawnChoice.UniformRate(probability)
    case SpecDefaultCarSpawn => CarSpawnChoice.SpecDefault
  }

  val intersection = Intersection(laneTypeChoice:         LaneTypeChoice,
                                  lightColours:           LightColoursChoice,
                                  numberOfIncomingRoads:  Int)

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
                     printStateFileWriter: Option[PrintWriter] = None):
                         (TrafficReinforcementLearner, Seq[Int]) = {
    var time = 0
    val scores = for (scoreBatch <- 0 until numScores) yield {
      var score = 0
      for (timeStep <- 0 until timeStepsPerScore) {
        for ( roadIndex <- 0 until intersection.carEntranceCount) {
          if (carSpawn(time, roadIndex)) intersection.insertCar(roadIndex)
        }
        RL     = RL.learn(TrafficModelAdapter.getState(intersection))
        score -= intersection.countWaiting
        time  += 1
        printStateFileWriter match {
          case None => {}
          case Some(fileWriter) => fileWriter.println(f"Time: $time\n" ++ intersection.printState)
        }
      }
      score
    }
    (reinforcementLearner, scores)
  }
}
