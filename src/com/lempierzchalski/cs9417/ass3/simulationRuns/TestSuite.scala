package com.lempierzchalski.cs9417.ass3.simulationRuns

import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.ConstantRateLearning
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel.{IntersectionAction, ToggleLights, DoNothing}
import com.lempierzchalski.cs9417.ass3.simulation.runSim.RunSim
import scala.util.Random

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */

object TestSuite extends App {
  val runRepetitions = 1
  val numScores = 30
  val timeStepsPerScore = 1000
  val tests: Seq[SimParams] = Seq(
    SimParams(chooseActionChoice  = RandomAction,
              learningRateChoice  = ConstantRateLearning(0.1),
              carSpawnChoice      = SpecDefault),

    SimParams(chooseActionChoice  = EpsilonGreedyAction(0.1),
              learningRateChoice  = ConstantRateLearning(0.1),
              carSpawnChoice      = SpecDefault),

    {val actionList: Seq[IntersectionAction] = Stream.continually(DoNothing).take(9).toSeq ++ Seq(ToggleLights)
    SimParams(chooseActionChoice  = LoopAction(actionList) ,
              learningRateChoice  = ConstantRateLearning(0.1),
              carSpawnChoice      = SpecDefault)},

    SimParams(chooseActionChoice  = EpsilonGreedyAction(0.2),
              learningRateChoice  = ConstantRateLearning(0.1),
              carSpawnChoice      = SpecDefault)
  )
  tests.foreach({
    case SimParams( seed:                   Int,
                    chooseActionChoice:     ChooseActionChoice,
                    learningRateChoice:     LearningRateChoice,
                    carSpawnChoice:         CarSpawnChoice,
                    laneTypeChoice:         LaneTypeChoice,
                    lightColours:           LightColoursChoice,
                    numberOfIncomingRoads:  Int,
                    futureDiscount:         Double,
                    printState:             Boolean) =>
      for (_ <- 0 until runRepetitions) RunSim( seed,
                                                chooseActionChoice,
                                                learningRateChoice,
                                                carSpawnChoice,
                                                laneTypeChoice,
                                                lightColours,
                                                numberOfIncomingRoads,
                                                futureDiscount,
                                                printState,
                                                numScores,
                                                timeStepsPerScore)
  })
}


