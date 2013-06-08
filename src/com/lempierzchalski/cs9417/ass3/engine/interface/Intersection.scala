package com.lempierzchalski.cs9417.ass3.engine.interface

import com.lempierzchalski.cs9417.ass3.simulation.simParameters.{LightColoursChoice, LaneTypeChoice, CarSpawnChoice}

/**
 * User: Pierzchalski
 * Date: 8/06/13
 * Package: com.lempierzchalski.cs9417.ass3.engine.interface
 * Project: trafficLightRL
 */
object Intersection {
  def apply(carSpawnChoice:         CarSpawnChoice,
            laneTypeChoice:         LaneTypeChoice,
            lightColours:           LightColoursChoice,
            numberOfIncomingRoads:  Int
            ): SimulationIntersection = {
    ???
  }
}
