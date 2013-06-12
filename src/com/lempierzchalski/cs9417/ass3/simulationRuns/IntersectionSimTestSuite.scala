package com.lempierzchalski.cs9417.ass3.simulationRuns

import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import scala.util.Random
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.SimParams
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.ConstantRateLearning
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.UniformRateCarSpawn
import com.lempierzchalski.cs9417.ass3.engine.IntersectionParams
import com.lempierzchalski.cs9417.ass3.simulation.runSim.RunSim

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 9/06/13
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
object IntersectionSimTestSuite extends App {
/*  for (numLanes <- 1 to 3;
       colours <- Seq(RedGreen, RedGreenAmber);
       numIncomingRoads <- Seq(2, 4);
       nearestCarViewDepth <- Seq(8, 4);
       numNearestCarsViewed <- Seq(1, 2, 3);
       advancedCarBehaviour <- Seq(true, false);
       numIterations <- 0 until Data.predefSimRepetitons / 2){
    val simParams = SimParams(
      seed = Random.nextInt(),
      learningActionChoice = EpsilonGreedyAction(Data.predefEpsilon),
      learningRateChoice = ConstantRateLearning(Data.predefConstantLearningRate),
      carSpawnChoice = UniformRateCarSpawn(Data.predefCarSpawnProbability),
      numAssessmentScores = Data.predefNumAssessmentScores * 2)
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
    RunSim(simParams, intersectionParams, printState = false)
  }*/

  val fancyIntersectionsTests: Seq[(SimParams, IntersectionParams)] = {
    for (numLanes <- Seq(2);
         numRoads <- Seq(4);
         colours <- Seq(RedGreenAmber);
         finalActionChoice <- Seq(RandomAction, BestAction);
         nearestCarViewDepth <- Seq(2, 8);
         numNearestViewed <- Seq(1);
         advancedCarBehaviour <- Seq(false);
         numIterations <- 0 until Data.predefSimRepetitons) yield {
      (SimParams(
        seed = Random.nextInt(),
        learningActionChoice = RandomAction,
        finalActionChoice = finalActionChoice,
        learningRateChoice = ConstantRateLearning(Data.predefConstantLearningRate),
        carSpawnChoice = UniformRateCarSpawn(Data.predefCarSpawnProbability),
        numAssessmentScores = Data.predefNumAssessmentScores),

      IntersectionParams(
        numberOfLanes = numLanes,
        lightColours = colours,
        numberOfIncomingRoads = numRoads,
        nearestCarViewDepth     = nearestCarViewDepth,
        numNearestCarsViewed    = numNearestViewed
      ))
    }
  }

  val fancyTrafficTests: Seq[(SimParams, IntersectionParams)] = {
    for (numLanes <- Seq(2);
         numRoads <- Seq(2);
         colours <- Seq(RedGreenAmber);
         finalActionChoice <- Seq(RandomAction, BestAction);
         nearestCarViewDepth <- Seq(4);
         numNearestViewed <- Seq(1);
         advancedCarBehaviour <- Seq(true, false);

         numIterations <- 0 until Data.predefSimRepetitons) yield {
      (SimParams(
        learningActionChoice = RandomAction,
        finalActionChoice = finalActionChoice,
        learningRateChoice = ConstantRateLearning(Data.predefConstantLearningRate),
        carSpawnChoice = UniformRateCarSpawn(Data.predefCarSpawnProbability),
        learningPeriodNumScores = 2 * Data.predefLearningPeriodScores),
      IntersectionParams(
        numberOfLanes = numLanes,
        lightColours  = colours,
        numberOfIncomingRoads   = numRoads,
        variableCarSpeed        = advancedCarBehaviour,
        changeLanes            = advancedCarBehaviour,
        crashes                 = advancedCarBehaviour,
        nearestCarViewDepth     = nearestCarViewDepth,
        numNearestCarsViewed    = numNearestViewed
      ))
    }
  }

  var testIndex = 0
  for (sip <- fancyIntersectionsTests ++ fancyTrafficTests) {
    print(f"performing test $testIndex")
    testIndex += 1
    val (sp, ip) = sip
    RunSim(sp, ip)
  }


}
