package com.lempierzchalski.cs9417.ass3.clui

import com.lempierzchalski.cs9417.ass3.simulation.runSim.RunSim
import com.lempierzchalski.cs9417.ass3.simulation.simParameters._
import com.lempierzchalski.cs9417.ass3.reinforcementLearner.trafficModel.ToggleLights
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.RepeatAction
import com.lempierzchalski.cs9417.ass3.simulation.simParameters.EpsilonGreedyAction

/**
 * User: Pierzchalski
 * Date: 9/06/13
 * Package: com.lempierzchalski.cs9417.ass3.clui
 * Project: trafficLightRL
 */

sealed trait NavigationState {
  def transition: NavigationState
}

case object Start extends NavigationState {
  def transition: NavigationState = {
    println(  f"To run one of our predefined simulations, enter 'predef[0-${Data.numberPredefSims}]'.\n" +
      "To run your own, enter 'new'.\n" +
      "To quit, enter 'quit'.")
    PredefOrNew(readLine())
  }
}

case class PredefOrNew(input: String) extends NavigationState {
  def transition: NavigationState = {
    val predefPattern = "(?i)predef([0-9]+)".r
    val newPattern = "(?i)new".r
    input match {
      case newPattern() => NewSimulation
      case predefPattern(index) =>
        if (0 to Data.numberPredefSims contains index.toInt) {
          Predef(index.toInt)
        } else {
          println(f"There is no predefined sim with index ${index.toInt}. Please try again.")
          PredefOrNew(readLine())
        }
      case Data.quitPattern() => End
      case _ => println(f"Invalid input '$input'. Please enter 'predef[0-${Data.numberPredefSims}]' or 'new'.")
        PredefOrNew(readLine())
    }
  }
}

case class Predef(index: Int) extends NavigationState {
  def transition: NavigationState = {
    assert(0 to Data.numberPredefSims contains index.toInt, f"$index out of bounds")
    println(f"Simulation $index is now running, and is outputing to a folder in './out/'.")
    RunSim(Data.predefinedSimParameters(index), Data.predefNumScores, Data.predefTimestepsPerScore)
    Start
  }
}

case object NewSimulation extends NavigationState {
  def transition: NavigationState = {
    println("""To set up your own simulation, choose from the following options,
              |or press return to use the default parameter (in this case 't').
              |First, choose a method for the RL algorithm to choose actions.
              |Enter one of the following options:
              |Epsilon-Greedy with parameter: 'eg.[0-9]+'
              |Random action:                 'r'
              |Toggle lights every 10 steps:  't'
              |Try toggle lights every step:  'e'
            """.stripMargin)
    ChoosingChooseAction(readLine())
  }
}

case class ChoosingChooseAction(input: String) extends NavigationState {
  def transition: NavigationState = {
    val epsilonGreedyMatch = "(?i)eg(\\.[0-9]+)".r
    val cacOpt: Option[ChooseActionChoice] = input match {
      case epsilonGreedyMatch(epsilon)  => Some(EpsilonGreedyAction(epsilon.toDouble))
      case "r"                          => Some(RandomAction)
      case "t" | ""                     => Some(Data.predefLoopAction)
      case "e"                          => Some(RepeatAction(ToggleLights))
      case _                            => None
    }
    cacOpt match {
      case Some(chooseActionChoice) =>  println(
                                        """Next, choose a learning rate, or press enter to use the
                                          |default parameter, in this case 'cnst.1'.
                                          |Constant learning rate:    'cnst.[0-9]+'
                                          |Converging learning rate:  'cnvg'
                                        """.stripMargin)
                                        ChoosingLearningRate(chooseActionChoice, readLine())
      case None                     =>  input match {
        case Data.quitPattern() =>  End
        case _                  =>  println(f"Invalid input $input.")
                                    NewSimulation
      }
    }
  }
}

case class ChoosingLearningRate(cac: ChooseActionChoice, input:String) extends NavigationState{
  def transition: NavigationState = {
    val constantRateMatch = "(?i)cnst(\\.[0-9]+)".r
    val clr: Option[LearningRateChoice] = input match {
      case constantRateMatch(rate)  => Some(ConstantRateLearning(rate.toDouble))
      case "cnvg"                   => Some(ConvergingRateLearning)
      case ""                       => Some(ConstantRateLearning(Data.predefConstantLearningRate))
      case _                        => None
    }
    clr match {
      case Some(learningRateChoice) =>  println(
                                        """Next, choose a ___.
                                          |magic magic magic.
                                        """.stripMargin)
                                        ChoosingCarSpawn(cac, learningRateChoice, readLine())
      case None => input match {
        case Data.quitPattern => End
        case _                => println(f"Invalid input $input.")
                                 ChoosingLearningRate(cac, readLine())
      }
    }
  }
}

case class ChoosingCarSpawn(cac: ChooseActionChoice,
                            clr: LearningRateChoice,
                            input: String) extends NavigationState {
  def transition: NavigationState = ???
}

case object End extends NavigationState {
  def transition: NavigationState = {
    assert(assertion = false, "Illegal state: should never transition on end state")
    End
  }
}
