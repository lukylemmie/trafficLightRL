package com.lempierzchalski.cs9417.ass3.simulation

import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.{ReinforcementLearner, LearningRateStrategies, ActionChoiceStrategies}
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel.{TrafficModelAdapter, IntersectionAction}
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.LoopAction
import com.lempierzchalski.cs9417.ass3.engine.interface.{SimulationIntersection, Intersection}
import java.io.PrintWriter
import com.lempierzchalski.cs9417.ass3.engine.IntersectionParams

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */

class TrafficRLSimulation (val simParams              : SimParams,
                           val intersectionParams     : IntersectionParams) {

  type State                        = TrafficModelAdapter.IntersectionState
  type Action                       = IntersectionAction
  type TrafficReinforcementLearner  = TrafficModelAdapter.TrafficReinforcementLearner

  val validActions = IntersectionAction.allActions

  def translateActionChoice(actionChoice: ChooseActionChoice):
  ActionChoiceStrategies.ActionChoiceFunction[State, Action] = actionChoice match {
    case RandomAction                 => ActionChoiceStrategies.RandomActionChoice[State, Action](validActions)
    case LoopAction(actions)          => ActionChoiceStrategies.LoopActionChoice[State, Action](actions)
    case RepeatAction(someAction)     => ActionChoiceStrategies.AlwaysChooseActionChoice[State, Action](someAction)
    case EpsilonGreedyAction(epsilon) => ActionChoiceStrategies.EpsilonGreedyActionChoice[State, Action](epsilon)
    case BestAction                   => ActionChoiceStrategies.BestChoice[State, Action]
  }

  val learningChooseAction = translateActionChoice(simParams.learningActionChoice)

  val finalChooseAction = translateActionChoice(simParams.finalActionChoice)

  val learningRate = simParams.learningRateChoice match {
    case ConstantRateLearning(learningRateParameter)  => LearningRateStrategies.constantRate[State, Action](learningRateParameter)
    case ConvergingRateLearning                       => LearningRateStrategies.convergingRate[State, Action]
  }

  val carSpawn = simParams.carSpawnChoice match {
    case UniformRateCarSpawn(probability) => CarSpawnChoice.UniformRate(probability)
    case SpecDefaultCarSpawn => CarSpawnChoice.SpecDefault
  }

  def simWithScoring(printStateFileWriter: Option[PrintWriter] = None):
  (TrafficReinforcementLearner, Seq[Int], Seq[Int]) = {

    var time = 0

    def runSim(numScores: Int, rl: TrafficReinforcementLearner, intersection: SimulationIntersection):
    (TrafficReinforcementLearner, Seq[Int]) = {
      var myRL = rl
      val scores =
        for (scoreBatch <- 0 until simParams.learningPeriodNumScores) yield {
          var score = 0
          for (timeStep <- 0 until simParams.timeStepsPerScore) {
            for ( roadIndex <- 0 until intersection.carEntranceCount) {
              if (carSpawn(time, roadIndex)) intersection.insertCar(roadIndex)
            }
            myRL = myRL.learn(TrafficModelAdapter.getState(intersection))
            score -= intersection.countWaiting
            time += 1
            printStateFileWriter match {
              case None => {}
              case Some(fileWriter) => fileWriter.println(f"Time: $time\n" ++ intersection.printState)
            }
          }
          score
        }
      (myRL, scores)
    }

    val learningIntersection = Intersection(intersectionParams)
    val learningTakeActionWithReward = TrafficModelAdapter.takeIntersectionActionWithReward(learningIntersection)
    val learningRL = ReinforcementLearner.construct(
      validActions,
      learningChooseAction,
      learningTakeActionWithReward,
      simParams.futureDiscount,
      learningRate
    )

    val (finalLearningRL, learningScores) = runSim(simParams.learningPeriodNumScores, learningRL, learningIntersection)


    val assessmentIntersection = Intersection(intersectionParams)
    val finalTakeActionWithReward = TrafficModelAdapter.takeIntersectionActionWithReward(assessmentIntersection)
    val assessmentRL = ReinforcementLearner(
      validActions,
      finalChooseAction,
      finalTakeActionWithReward,
      simParams.futureDiscount,
      learningRate = LearningRateStrategies.constantRate[State, Action](0.0),
      qTable = finalLearningRL.qTable
    )

    val (_, assessmentScores) = runSim(simParams.numAssessmentScores, assessmentRL, assessmentIntersection)

    (finalLearningRL, learningScores, assessmentScores)
  }
}
