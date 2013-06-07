package com.lempierzchalski.cs9417.ass3.reinforcementLearner

import com.lempierzchalski.cs9417.ass3.engine.Intersection
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel.TrafficModelAdapter
import java.io.{FileWriter, PrintWriter, File}

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 6/06/13
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
object RunSim extends App {
  val trafficModelAdapter = new TrafficModelAdapter(intersection = new Intersection(),
                                                    epsilonGreedyParameter = 0.1,
                                                    learningRate = 0.1,
                                                    futureDiscount = 0.9)
  trafficModelAdapter.sim(endTime = 1000000, proportionCarInserts = 10)
  val fileName = {
    var i = 0
    var fileName = ""
    while({fileName = f"./out/data$i.txt"; val f = new File(fileName); f.exists()}) {
      i += 1
    }
    fileName
  }
  val fileWriter = new PrintWriter(new FileWriter(fileName))
  for (kv <- trafficModelAdapter.getReinforcementLearner.qTable) {fileWriter.println(kv)}//; println(kv)}
  fileWriter.close()
}
