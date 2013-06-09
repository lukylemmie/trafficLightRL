package com.lempierzchalski.cs9417.ass3.simulationRuns

import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.ConstantRateLearning
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel.{IntersectionAction, ToggleLights, DoNothing}
import com.lempierzchalski.cs9417.ass3.simulation.runSim.RunSim
import com.lempierzchalski.cs9417.ass3.engine.IntersectionParams
import scala.util.Random

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */

object BaseTestSuite extends App {
  val runRepetitions = 10
  val tests: Seq[(SimParams, IntersectionParams)] = Seq(
    (SimParams(chooseActionChoice = RandomAction,
              learningRateChoice  = ConstantRateLearning(0.1),
              carSpawnChoice      = SpecDefaultCarSpawn),
    IntersectionParams()),

    (SimParams(chooseActionChoice = EpsilonGreedyAction(0.1),
              learningRateChoice  = ConstantRateLearning(0.1),
              carSpawnChoice      = SpecDefaultCarSpawn),
    IntersectionParams()),

    {val actionList: Seq[IntersectionAction] = Stream.continually(DoNothing).take(9).toSeq ++ Seq(ToggleLights)
    (SimParams(chooseActionChoice = LoopAction(actionList) ,
              learningRateChoice  = ConstantRateLearning(0.1),
              carSpawnChoice      = SpecDefaultCarSpawn),
    IntersectionParams())})

  val parameterTests: Seq[(SimParams, IntersectionParams)] = for ( epsilon <- 0.0 to(1.0, step = 0.1);
                                                                   alpha <- 0.1 to(1.0, step = 0.1)) yield {
    (SimParams(chooseActionChoice = EpsilonGreedyAction(epsilon),
                learningRateChoice = ConstantRateLearning(alpha),
                carSpawnChoice = UniformRateCarSpawn(0.11)),
      IntersectionParams())
  }

  var iteration = 0
  (tests ++ parameterTests).foreach( (sip: (SimParams, IntersectionParams)) => {
      val (simParams, intersectionParams) = sip
      for (i <- 0 until runRepetitions) {
        val randomSeedSimParams = SimParams(seed = Random.nextInt(),
                                            chooseActionChoice = simParams.chooseActionChoice,
                                            learningRateChoice = simParams.learningRateChoice,
                                            carSpawnChoice =     simParams.carSpawnChoice)
        println(f"Running iteration $iteration")
        RunSim(randomSeedSimParams, intersectionParams)
        iteration += 1
      }
    }
  )
}


