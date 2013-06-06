package com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 6/06/13
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
sealed trait IntersectionAction

case object ToggleLights extends IntersectionAction
case object DoNothing extends IntersectionAction
