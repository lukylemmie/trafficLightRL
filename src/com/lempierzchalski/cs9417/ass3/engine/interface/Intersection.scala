package com.lempierzchalski.cs9417.ass3.engine.interface

import com.lempierzchalski.cs9417.ass3.simulation.simParameters.{LightColoursChoice, LaneTypeChoice, CarSpawnChoice}
import com.lempierzchalski.cs9417.ass3.engine.IntersectionBase

/**
 * User: Pierzchalski
 * Date: 8/06/13
 * Package: com.lempierzchalski.cs9417.ass3.engine.interface
 * Project: trafficLightRL
 */
object Intersection {
  def apply(numberOfLanes           : Int,
            lightColours            : LightColoursChoice,
            numberOfIncomingRoads   : Int,
            variableCarSpeed        : Boolean,
            changeLanes             : Boolean,
            crashes                 : Boolean,
            nearestCarViewDepth     : Int,
            numNearestCarsViewed    : Int
            ): SimulationIntersection = {
    new IntersectionBase
  }
}
