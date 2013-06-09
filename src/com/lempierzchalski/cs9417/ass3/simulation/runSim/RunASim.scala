package com.lempierzchalski.cs9417.ass3.simulation.runSim

import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import com.lempierzchalski.cs9417.ass3.clui.Data
import com.lempierzchalski.cs9417.ass3.engine.IntersectionParams
import scala.util.Random
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.SimParams
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.EpsilonGreedyAction
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.ConstantRateLearning
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.UniformRateCarSpawn
import com.lempierzchalski.cs9417.ass3.engine.IntersectionParams

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 9/06/13
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
object RunASim extends App {
  for (numLanes <- 1 to 3;
       colours <- Seq(RedGreen, RedGreenAmber);
       numIncomingRoads <- Seq(2, 4);
       nearestCarViewDepth <- Seq(8, 4);
       numNearestCarsViewed <- Seq(1, 2, 3);
       advancedCarBehaviour <- Seq(true, false);
       numIterations <- 0 until Data.predefSimRepetitons){
    val simParams = SimParams(
      seed = Random.nextInt(),
      chooseActionChoice = EpsilonGreedyAction(Data.predefEpsilon),
      learningRateChoice = ConstantRateLearning(Data.predefConstantLearningRate),
      carSpawnChoice = UniformRateCarSpawn(Data.predefCarSpawnProbability))
    val intersectionParams = IntersectionParams(
      numberOfLanes = numLanes,
      lightColours = colours,
      numberOfIncomingRoads = numIncomingRoads,
      nearestCarViewDepth = nearestCarViewDepth,
      numNearestCarsViewed = numNearestCarsViewed,
      changeLanes = advancedCarBehaviour,
      crashes = false,
      variableCarSpeed = false
    )
    RunSim(simParams, intersectionParams, printState = true)
  }
}