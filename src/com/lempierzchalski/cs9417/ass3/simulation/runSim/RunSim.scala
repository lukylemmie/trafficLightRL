package com.lempierzchalski.cs9417.ass3.simulation.runSim

import com.lempierzchalski.cs9417.ass3.simulation.TrafficRLSimulation
import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import com.lempierzchalski.cs9417.ass3.myUtil
import scala.Some
import com.lempierzchalski.cs9417.ass3.engine.IntersectionParams

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 1:23 PM
 * To change this template use File | Settings | File Templates.
 */
object RunSim {
  def apply(simParams           : SimParams,
            intersectionParams  : IntersectionParams,
            printState          : Boolean = false) {

    val seed = simParams.seed

    val startTime = System.currentTimeMillis()

    util.Random.setSeed(seed)

    val sim = new TrafficRLSimulation(simParams, intersectionParams)

    val fileDir = "./out/sim/data"

    val paramFileWriter = myUtil.Util.indexedDirectoryPrintWriter(fileDir, fullFileName = "params.txt")
    paramFileWriter.println(simParams)
    paramFileWriter.println(intersectionParams)
    paramFileWriter.close()

    val stateFileWriter  = myUtil.Util.indexedDirectoryPrintWriter(fileDir, fullFileName = "state.txt")
    val (finalRL, learningScores, assessmentScores) = sim.simWithScoring(printStateFileWriter = if (printState) Some(stateFileWriter) else None)
    if (!printState) stateFileWriter.println("Output suppressed")
    stateFileWriter.close()

    val qTableFileWriter = myUtil.Util.indexedDirectoryPrintWriter(fileDir, fullFileName = "qTable.txt")
    finalRL.qTable.foreach(qTableFileWriter.println(_))
    qTableFileWriter.close()

    val learningScoresFileWriter = myUtil.Util.indexedDirectoryPrintWriter(fileDir, fullFileName = "learningScores.txt")
    learningScores.zipWithIndex.foreach( scoreIndexPair => learningScoresFileWriter.println(scoreIndexPair.swap) )
    learningScoresFileWriter.close()

    val assessmentScoresFileWriter = myUtil.Util.indexedDirectoryPrintWriter(fileDir, fullFileName = "assessmentScores.txt")
    assessmentScores.zipWithIndex.foreach( scoreIndexPair => assessmentScoresFileWriter.println(scoreIndexPair.swap) )
    assessmentScoresFileWriter.close()

    val endTime = System.currentTimeMillis()
    println(f"Finished: it took ${(endTime - startTime)/1000} seconds.")
  }
}
