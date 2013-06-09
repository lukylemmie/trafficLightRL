package com.lempierzchalski.cs9417.ass3.clui

import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel.{ToggleLights, DoNothing, IntersectionAction}
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.SimParams

/**
 * User: Pierzchalski
 * Date: 8/06/13
 * Package: com.lempierzchalski.cs9417.ass3.clui
 * Project: trafficLightRL
 */
object Main extends App {
  var state: NavigationState = Start
  while (state ne End) state = transition(state)

  def transition(state: NavigationState): NavigationState = state match {
    case Start                                      => Start.transition
    case predefOrNew: PredefOrNew                   => predefOrNew.transition
    case predef: Predef                             => predef.transition
    case NewSimulation                              => NewSimulation.transition
    case choosingChooseAction: ChoosingChooseAction => choosingChooseAction.transition
    case choosingLearningRate: ChoosingLearningRate => choosingLearningRate.transition
    case End                                        => End.transition
  }

}

object Data {
  val quitPattern = "(?i)quit.*".r
  val predefNumScores = 30
  val predefTimestepsPerScore = 1000
  val predefinedSimParameters: Seq[SimParams] = Seq()
  val numberPredefSims = predefinedSimParameters.length
  val predefLoopAction: ChooseActionChoice = { val actionList: Seq[IntersectionAction] = Stream.continually(DoNothing).take(9).toSeq ++ Seq(ToggleLights); LoopAction(actionList) }
  val predefConstantLearningRate = 0.1
  val predefFutureDiscount = 0.9
}