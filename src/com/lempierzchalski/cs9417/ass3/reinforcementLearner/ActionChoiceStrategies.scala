package com.lempierzchalski.cs9417.ass3.reinforcementLearner

import scala.Predef._
import util.Random
import scala.collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 7/06/13
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */

object ActionChoiceStrategies {
  type QTableType[State, Action] = ReinforcementLearner.QTableType[State, Action]
  
  def RandomActionChoice[State, Action](state: State,
                            validActions: Set[Action],
                            qTable: QTableType[State, Action]): Action = {
    validActions.toSeq(Random.nextInt(validActions.size))
  }
  
  def EpsilonGreedyActionChoice[State, Action](epsilon: Double):
      (State, Set[Action], QTableType[State, Action]) => Action =
    (state, validActions, qTable) => {
      if (Random.nextDouble() < epsilon) {
        validActions.toSeq(Random.nextInt(validActions.size))
      } else {
        val validActionsSeq = validActions.toSeq
        val actionUtilities = validActionsSeq.map( (state, _) ).map( qTable.getOrElse(_, (0.0, 0))._1 )
        val actionUtilityPairs = validActionsSeq.zip(actionUtilities)
        val (chosenAction, _) = actionUtilityPairs.maxBy( _._2 )
        chosenAction
      }
    }

  def AlwaysChooseActionChoice[State, Action](action: Action):
      (State, Set[Action], QTableType[State, Action]) => Action =  (_, _, _) => action

  def LoopActionChoice[State, Action](actionOrdering: Seq[Action]):
      (State, Set[Action], QTableType[State, Action]) => Action =
  {
    val countContainer = mutable.Seq[Int](-1)
    (_, _, _) => {
      countContainer(0) += 1
      countContainer(0) %= actionOrdering.size
      actionOrdering(countContainer(0))
    }
  }
}
