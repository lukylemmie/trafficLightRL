package com.lempierzchalski.cs9417.ass3.reinforcementLearner

import com.lempierzchalski.cs9417.ass3.engine.Intersection
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel.{DoNothing, ToggleLights, TrafficModelAdapter}
import com.lempierzchalski.cs9417.ass3.myUtil.Util

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 6/06/13
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
object RunSim extends App {
  val trafficModelAdapter = new TrafficModelAdapter(
      intersection = new Intersection(),
      epsilonGreedyParameter = 0.1,
      learningRateParameter = 0.1,
      futureDiscountParameter = 0.9)
  val (reinforcementLearner, scores) = trafficModelAdapter.simWithScoring(
      numScores = 1,
      timeStepsPerScore = 1000,
      proportionCarInserts = 5,
      myChooseAction = ActionChoiceStrategies.RandomActionChoice(Set(DoNothing, ToggleLights)),
      debug = true)
  val qTablePrintWriter = Util.indexedFilePrintWriter(fileDir = "./out/", fileName = "qTable", fileType = ".txt")
  for (kv <- reinforcementLearner.qTable) {qTablePrintWriter.println(kv)}
  qTablePrintWriter.close()
  val scorePrintWriter = Util.indexedFilePrintWriter(fileDir = "./out./", fileName = "score", fileType = ".txt")
  for ((score, index) <- scores.zipWithIndex) {scorePrintWriter.println((index, score))}
  scorePrintWriter.close()
}
