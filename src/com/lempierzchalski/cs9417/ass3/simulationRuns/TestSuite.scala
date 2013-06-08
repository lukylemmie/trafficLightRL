package com.lempierzchalski.cs9417.ass3.simulationRuns

import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.ConstantRateLearning
import com.lempierzchalski.cs9417.ass3.simulationRuns.TestParam
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel.{IntersectionAction, ToggleLights, DoNothing}
import com.lempierzchalski.cs9417.ass3.simulation.runSim.RunSim

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
object TestSuite extends App {
  val runRepetitions = 10
  val numScores = 100
  val timeStepsPerScore = 1000
  val tests: Seq[TestParam] = Seq(
    TestParam(seed=0, chooseActionChoice = RandomAction, learningRateChoice = ConstantRateLearning(0.1),
              carSpawnChoice = SpecDefault, futureDiscount = 0.9),
    TestParam(seed=1, chooseActionChoice = EpsilonGreedyAction(0.1), learningRateChoice = ConstantRateLearning(0.1),
              carSpawnChoice = SpecDefault, futureDiscount = 0.9),
    {
      val actionSeq: Seq[IntersectionAction] = Stream.continually(DoNothing).take(9).toSeq ++ Seq(ToggleLights)
      TestParam(seed=2, chooseActionChoice = LoopAction(actionSeq) , learningRateChoice = ConstantRateLearning(0.1),
                carSpawnChoice = SpecDefault, futureDiscount = 0.9)
    },
    TestParam(seed=1, chooseActionChoice = EpsilonGreedyAction(0.2), learningRateChoice = ConstantRateLearning(0.9),
              carSpawnChoice = SpecDefault, futureDiscount = 0.9)
  )
  tests.foreach({
    case TestParam(seed, chooseActionChoice, learningRateChoice, carSpawnChoice, futureDiscount) =>
      for (_ <- 0 until runRepetitions) RunSim(seed, chooseActionChoice, learningRateChoice, carSpawnChoice, futureDiscount)
  })
}

case class TestParam(seed: Int, chooseActionChoice: ChooseActionChoice, learningRateChoice: LearningRateChoice,
                     carSpawnChoice: CarSpawnChoice, futureDiscount: Double)
