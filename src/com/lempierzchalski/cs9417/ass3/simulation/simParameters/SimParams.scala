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
                      futureDiscount: Double,
                      printState: Boolean = false)

case class IntersectionParams(numberOfLanes           : Int = 1,
                              lightColours            : LightColoursChoice = RedGreen,
                              numberOfIncomingRoads   : Int = 2,
                              variableCarSpeed        : Boolean = false,
                              changeLanes             : Boolean = false,
                              crashes                 : Boolean = false,
                              nearestCarViewDepth     : Int = 8,
                              numNearestCarsViewed    : Int = 1
                               )
