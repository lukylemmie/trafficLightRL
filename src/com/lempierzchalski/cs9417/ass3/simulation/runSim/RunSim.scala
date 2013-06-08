package com.lempierzchalski.cs9417.ass3.simulation.runSim

import com.lempierzchalski.cs9417.ass3.simulation.TrafficRLSimulation
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.{LearningRateChoice, ChooseActionChoice, ConstantRateLearning, EpsilonGreedyAction}
import com.lempierzchalski.cs9417.ass3.myUtil
import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 1:23 PM
 * To change this template use File | Settings | File Templates.
 */
object RunSim {
  def apply(seed: Int,
            chooseActionChoice: ChooseActionChoice,
            learningRateChoice: LearningRateChoice,
            futureDiscountParameter: Double,
            inverseCarRate: Double) {
    val startTime = System.currentTimeMillis()
    util.Random.setSeed(seed)

    val insertWithUniformCarRate: (Int, Int) => Boolean = (time, roadIndex) => Random.nextDouble() < 1/inverseCarRate

    val sim = new TrafficRLSimulation(chooseActionChoice,
                                      learningRateChoice,
                                      futureDiscount      = futureDiscountParameter,
                                      insertCar           = insertWithUniformCarRate)


    val fileDir = "./out/data"

    val stateFileWriter  = myUtil.Util.indexedDirectoryPrintWriter(fileDir, fullFileName = "state.txt")
    val (finalRL, scores) = sim.simWithScoring(numScores = 10,
                                               timeStepsPerScore = 1000,
                                               printStateFileWriter = Some(stateFileWriter))
    stateFileWriter.close()

    val simParamFileWriter = myUtil.Util.indexedDirectoryPrintWriter(fileDir, fullFileName = "params.txt")
    simParamFileWriter.println(f"Seed:            $seed")
    simParamFileWriter.println(f"Choose Action:   $chooseActionChoice")
    simParamFileWriter.println(f"Learning Rate:   $learningRateChoice")
    simParamFileWriter.println(f"Future Discount: $futureDiscountParameter")
    simParamFileWriter.println(f"Insert Cars:     constant rate, rate = 1/$inverseCarRate")
    simParamFileWriter.close()

    val qTableFileWriter = myUtil.Util.indexedDirectoryPrintWriter(fileDir, fullFileName = "qTable.txt")
    finalRL.qTable.foreach(qTableFileWriter.println(_))
    qTableFileWriter.close()

    val scoresFileWriter = myUtil.Util.indexedDirectoryPrintWriter(fileDir, fullFileName = "scores.txt")
    scores.zipWithIndex.foreach( scoreIndexPair => scoresFileWriter.println(scoreIndexPair.swap) )
    scoresFileWriter.close()

    val endTime = System.currentTimeMillis()
    println(f"Finished: it took ${(endTime - startTime)/1000} seconds.")
  }
}
