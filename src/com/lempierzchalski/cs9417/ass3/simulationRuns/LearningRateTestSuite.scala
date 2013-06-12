package com.lempierzchalski.cs9417.ass3.simulationRuns

import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import com.lempierzchalski.cs9417.ass3.clui.Data
import scala.util.Random
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.SimParams
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.ConstantRateLearning
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.EpsilonGreedyAction
import com.lempierzchalski.cs9417.ass3.engine.IntersectionParams
import com.lempierzchalski.cs9417.ass3.simulation.runSim.RunSim

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 9/06/13
 * Time: 9:39 PM
 * To change this template use File | Settings | File Templates.
 */
object LearningRateTestSuite extends App {
/*  for (learningRateChoice <- Seq(ConstantRateLearning(Data.predefConstantLearningRate), ConvergingRateLearning);
       numIterations <- 0 until Data.predefSimRepetitons){
    val simParams = SimParams(
      seed = Random.nextInt(),
      learningActionChoice = EpsilonGreedyAction(Data.predefEpsilon),
      learningRateChoice = learningRateChoice,
      carSpawnChoice = UniformRateCarSpawn(Data.predefCarSpawnProbability))
    RunSim(simParams, IntersectionParams())
  }*/
}
