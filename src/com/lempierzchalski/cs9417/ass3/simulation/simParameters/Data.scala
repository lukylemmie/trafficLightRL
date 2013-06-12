package com.lempierzchalski.cs9417.ass3.simulation.simParameters

import com.lempierzchalski.cs9417.ass3.engine.IntersectionParams
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel.{ToggleLights, DoNothing, IntersectionAction}

/**
 * User: Pierzchalski
 * Date: 12/06/13
 * Package: com.lempierzchalski.cs9417.ass3.simulation.simParameters
 * Project: ${PROJECT_NAME}
 */
object Data {
  val quitPattern = "(?i)quit.*".r
  val predefEpsilon = 0.1
  val predefNumAssessmentScores = 50
  val predefTimestepsPerScore = 1000
  val predefSimRepetitons = 10
  val predefinedSimParameters: Seq[(SimParams, IntersectionParams)] = Seq()
  val numberPredefSims = predefinedSimParameters.length
  val predefLoopAction: ChooseActionChoice = { val actionList: Seq[IntersectionAction] = Stream.continually(DoNothing).take(9).toSeq ++ Seq(ToggleLights); LoopAction(actionList) }
  val predefConstantLearningRate = 0.1
  val predefCarSpawnProbability = 0.11
  val predefFutureDiscount = 0.9
  val predefLearningPeriodScores = 50
}
