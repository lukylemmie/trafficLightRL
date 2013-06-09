package com.lempierzchalski.cs9417.ass3.simulation.simParameters

import scala.util.Random

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 7:24 PM
 * To change this template use File | Settings | File Templates.
 */
case class SimParams( seed: Int = Random.nextInt(),
                      chooseActionChoice: ChooseActionChoice,
                      learningRateChoice: LearningRateChoice,
                      carSpawnChoice: CarSpawnChoice,
                      numLanes: Int = 1,
                      lightColours: LightColoursChoice = RedGreen,
                      numberOfIncomingRoads: Int = 2,
                      futureDiscount: Double = 0.9,
                      printState: Boolean = false)
