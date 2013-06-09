package com.lempierzchalski.cs9417.ass3.simulation.simParameters

import scala.util.Random
import com.lempierzchalski.cs9417.ass3.clui.Data

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 7:24 PM
 * To change this template use File |   Settings |   File Templates.
 */
case class SimParams( seed: Int = Random.nextInt(),
                      chooseActionChoice: ChooseActionChoice,
                      learningRateChoice: LearningRateChoice,
                      carSpawnChoice: CarSpawnChoice,
                      futureDiscount: Double = Data.predefFutureDiscount,
                      numScores: Int = Data.predefNumScores,
                      timeStepsPerScore: Int = Data.predefTimestepsPerScore) {
  override def toString: String = {
    f"""Sim parameters:
       |  seed:                    $seed
       |  chooseActionChoice:      $chooseActionChoice
       |  learningRateChoice:      $learningRateChoice
       |  carSpawnChoice:          $carSpawnChoice
       |  futureDiscount:          $futureDiscount
       |  numScores:               $numScores
       |  timeStepsPerScore:       $timeStepsPerScore""".stripMargin
  }
}


