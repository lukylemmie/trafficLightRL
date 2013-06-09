package com.lempierzchalski.cs9417.ass3.engine.interface

import com.lempierzchalski.cs9417.ass3.simulation.simParameters.{IntersectionParams, LightColoursChoice, CarSpawnChoice}
import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import com.lempierzchalski.cs9417.ass3.engine.{IntersectionAmber, IntersectionBase}

/**
 * User: Pierzchalski
 * Date: 8/06/13
 * Package: com.lempierzchalski.cs9417.ass3.engine.interface
 * Project: trafficLightRL
 */
object Intersection {
  def apply(intersectionParams: IntersectionParams): SimulationIntersection = {
    intersectionParams.lightColours match {
      case RedGreen => new IntersectionBase(intersectionParams)
      case RedGreenAmber => new IntersectionAmber(intersectionParams)
    }
  }
}
