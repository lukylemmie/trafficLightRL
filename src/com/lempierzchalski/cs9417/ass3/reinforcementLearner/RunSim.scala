package com.lempierzchalski.cs9417.ass3.reinforcementLearner

import com.lempierzchalski.cs9417.ass3.engine.Intersection
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel.TrafficModelAdapter

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 6/06/13
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
object RunSim extends App {
  val trafficModelAdapter = new TrafficModelAdapter(new Intersection())
  trafficModelAdapter.sim()
  for (kv <- trafficModelAdapter.getQTable) {
    println(kv._1, kv._2)
  }
}
