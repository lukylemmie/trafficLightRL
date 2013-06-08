package com.lempierzchalski.cs9417.ass3.simulation.simParameters

import com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel.IntersectionAction

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
sealed trait ChooseActionChoice

case object RandomAction extends ChooseActionChoice

case class RepeatAction(action: IntersectionAction) extends ChooseActionChoice
case class EpsilonGreedyAction(epsilon: Double) extends ChooseActionChoice
case class LoopAction(actionSeq: Seq[IntersectionAction]) extends ChooseActionChoice