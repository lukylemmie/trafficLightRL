package com.lempierzchalski.cs9417.ass3.clui

/**
 * User: Pierzchalski
 * Date: 8/06/13
 * Package: com.lempierzchalski.cs9417.ass3.clui
 * Project: trafficLightRL
 */
object Main extends App {
  val numberPredefSims = 10
  val quitPattern = "(?i)quit".r
  var state: NavigationState = Start
  while (state ne End) state = transition(state)

  def transition(state: NavigationState): NavigationState = state match {
    case Start => println(f"To run one of our predefined simulations, enter 'predef[0-$numberPredefSims]'.\n" +
                          "To run your own, enter 'new'.\n" +
                          "To quit, enter 'quit'."); PredefOrNew(readLine())
    case PredefOrNew(str) => {
      val predefPattern = "(?i)predef([0-9]+)".r
      val newPattern = "(?i)new".r
      str match {
        case newPattern() => NewSimulation
        case predefPattern(index) => if (0 to numberPredefSims contains index.toInt) {
          Predef(index.toInt)
        } else {
          println(f"There is no predefined sim with index ${index.toInt}. Please try again.")
          PredefOrNew(readLine())
        }
        case quitPattern() => End
        case _ => println(f"Invalid input '$str'. Please enter 'predef[0-$numberPredefSims]' or 'new'.")
          PredefOrNew(readLine())
      }
    }
  }

}

sealed trait NavigationState

case object Start extends NavigationState
case class PredefOrNew(inputO: String) extends NavigationState
case class Predef(index: Int) extends NavigationState
case object NewSimulation extends NavigationState
case object End extends NavigationState
