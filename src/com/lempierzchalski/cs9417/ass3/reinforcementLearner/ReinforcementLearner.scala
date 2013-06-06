package com.lempierzchalski.cs9417.ass3.reinforcementLearner

import collection.immutable
import util.Random

/**
 * User: Pierzchalski
 * Date: 3/06/13
 * Package: com.lempierzchalski.cs9417.ass3.reinforcementLearner
 * Project: trafficLightRL
 */
case class ReinforcementLearner[State, Action](
                                                validActions: Seq[Action],
                                                takeActionWithReward: (State, Action) => (State, Double),
                                                futureDiscount: Double,
                                                learningRate: Double,
                                                qTable: Map[(State, Action), (Double, Int)]
                                                ){
  
  def qValueTable: Map[(State, Action), Double] = qTable.mapValues( _._1 )

  def qCountTable: Map[(State, Action), Int] = qTable.mapValues( _._2 )

  private def getAction(state: State): Action = validActions(Random.nextInt(validActions.size))

  def learn(currentState: State): ReinforcementLearner[State, Action] = {
    val action = getAction(currentState)
    val (newState, reward) = takeActionWithReward(currentState, action)
    val (oldQReward, oldCount) = qTable.getOrElse((currentState, action), (0.0, 0))
    val (newQReward, _) = validActions.map( anAction => qTable.getOrElse((newState, anAction), (0.0, 0)) ).max
    val updatedQReward = (1 - learningRate) * oldQReward + learningRate * (reward + futureDiscount * newQReward)
    val newQTable = qTable.updated((currentState, action), (updatedQReward, oldCount + 1))
    ReinforcementLearner(validActions, takeActionWithReward, futureDiscount, learningRate, newQTable)
  }

}

object ReinforcementLearner{
  def construct[State, Action](validActions: Seq[Action],
                           takeActionWithReward: (State, Action) => (State, Double),
                           futureDiscount: Double,
                           learningRate: Double): ReinforcementLearner[State, Action] = {
    ReinforcementLearner(validActions, takeActionWithReward, futureDiscount, learningRate, qTable = Map.empty)
  }
}
