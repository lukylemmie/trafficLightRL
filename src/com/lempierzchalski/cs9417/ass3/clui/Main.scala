package com.lempierzchalski.cs9417.ass3.clui

import com.lempierzchalski.cs9417.ass3.simulation.simParameters.SimParams
import com.lempierzchalski.cs9417.ass3.simulation.runSim.RunSim

/**
 * User: Pierzchalski
 * Date: 8/06/13
 * Package: com.lempierzchalski.cs9417.ass3.clui
 * Project: trafficLightRL
 */
object Main extends App {
  val quitPattern = "(?i)quit".r
  var state: NavigationState = Start
  while (state ne End) state = transition(state)

  def transition(state: NavigationState): NavigationState = state match {
    case Start => println(f"To run one of our predefined simulations, enter 'predef[0-${Data.numberPredefSims}]'.\n" +
                          "To run your own, enter 'new'.\n" +
                          "To quit, enter 'quit'."); PredefOrNew(readLine())
    case PredefOrNew(str) => {
      val predefPattern = "(?i)predef([0-9]+)".r
      val newPattern = "(?i)new".r
      str match {
        case newPattern() => NewSimulation
        case predefPattern(index) => if (0 to Data.numberPredefSims contains index.toInt) {
          Predef(index.toInt)
        } else {
          println(f"There is no predefined sim with index ${index.toInt}. Please try again.")
          PredefOrNew(readLine())
        }
        case quitPattern() => End
        case _ => println(f"Invalid input '$str'. Please enter 'predef[0-${Data.numberPredefSims}]' or 'new'.")
          PredefOrNew(readLine())
      }
    }
    case Predef(index) => assert(0 to Data.numberPredefSims contains index.toInt, f"$index out of bounds")
      println(f"Simulation $index is now running, and is outputing to a folder in './out/'.")
      RunSim(Data.predefinedSimParameters(index), Data.predefNumScores, Data.predefTimestepsPerScore)
      Start
    case NewSimulation => println("To set up your own simulation, choose from the following options,\n" +
                                  "or press return to use the default parameter.")
      End
    case End => assert(assertion = false, "Illegal state: should never transition on end state"); End
  }

}

sealed trait NavigationState

case object Start extends NavigationState
case class PredefOrNew(input: String) extends NavigationState
case class Predef(index: Int) extends NavigationState
case object NewSimulation extends NavigationState
case class ChooseActionChoice(input: String)
case object End extends NavigationState

object Data {
  val predefNumScores = 30
  val predefTimestepsPerScore = 1000
  val predefinedSimParameters: Seq[SimParams] = Seq()
  val numberPredefSims = predefinedSimParameters.length
}