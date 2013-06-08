package com.lempierzchalski.cs9417.ass3.simulation.simParameters

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
sealed trait LaneTypeChoice

case object Normal extends LaneTypeChoice
case class MultiLane(numLanes: Int = 1) extends LaneTypeChoice