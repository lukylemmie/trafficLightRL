package com.lempierzchalski.cs9417.ass3.simulation.runSim

import com.lempierzchalski.cs9417.ass3.simulation.TrafficRLSimulation
import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import com.lempierzchalski.cs9417.ass3.myUtil
import util.Random
import scala.Some

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 1:23 PM
 * To change this template use File | Settings | File Templates.
 */
object RunSim {
  def apply(seed:                   Int,
            chooseActionChoice:     ChooseActionChoice,
            learningRateChoice:     LearningRateChoice,
            carSpawnChoice:         CarSpawnChoice,
            laneTypeChoice:         LaneTypeChoice,
            lightColours:           LightColoursChoice,
            numberOfIncomingRoads:  Int,
            futureDiscount:         Double,
            printState:             Boolean,
            numScores:              Int,
            timeStepsPerScore:      Int) {

    val startTime = System.currentTimeMillis()

    util.Random.setSeed(seed)

    val sim = new TrafficRLSimulation(chooseActionChoice:     ChooseActionChoice,
                                      learningRateChoice:     LearningRateChoice,
                                      carSpawnChoice:         CarSpawnChoice,
                                      laneTypeChoice:         LaneTypeChoice,
                                      lightColours:           LightColoursChoice,
                                      numberOfIncomingRoads:  Int,
                                      futureDiscount:         Double)

    val fileDir = "./out/data"

    val stateFileWriter  = myUtil.Util.indexedDirectoryPrintWriter(fileDir, fullFileName = "state.txt")
    val (finalRL, scores) = sim.simWithScoring(numScores,
                                               timeStepsPerScore,
                                               printStateFileWriter = if (printState) Some(stateFileWriter) else None)
    if (!printState) stateFileWriter.println("Output suppressed")
    stateFileWriter.close()

    val simParamFileWriter = myUtil.Util.indexedDirectoryPrintWriter(fileDir, fullFileName = "params.txt")
    simParamFileWriter.println(f"seed:                    $seed")
    simParamFileWriter.println(f"chooseActionChoice:      $chooseActionChoice")
    simParamFileWriter.println(f"learningRateChoice:      $learningRateChoice")
    simParamFileWriter.println(f"carSpawnChoice:          $carSpawnChoice")
    simParamFileWriter.println(f"laneTypeChoice:          $laneTypeChoice")
    simParamFileWriter.println(f"lightColours:            $lightColours")
    simParamFileWriter.println(f"numberOfIncomingRoads:   $numberOfIncomingRoads")
    simParamFileWriter.println(f"futureDiscount:          $futureDiscount")
    simParamFileWriter.println(f"printState:              $printState")
    simParamFileWriter.println(f"numScores:               $numScores")
    simParamFileWriter.println(f"timeStepsPerScore:       $timeStepsPerScore")
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
