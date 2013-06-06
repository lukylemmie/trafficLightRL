package com.lempierzchalski.cs9417.ass3.reinforcementLearner

import collection.immutable
import util.Random

/**
 * User: Pierzchalski
 * Date: 3/06/13
 * Package: com.lempierzchalski.cs9417.ass3.reinforcementLearner
 * Project: trafficLightRL
 */
class ReinforcementLearner[State, Action](
                                          val validActions: Seq[Action],
                                          val takeActionWithReward: (State, Action) => (State, Double),
                                          val futureDiscount: Double,
                                          val learningRate: Double
                                          ){

  private var Q: Map[(State, Action), Double] = immutable.HashMap.empty

  def getQTable = Q

  private def getAction(state: State): Action = validActions(Random.nextInt(validActions.size))

  def learn(currentState: State) {
    val action = getAction(currentState)
    val (newState, reward) = takeActionWithReward(currentState, action)
    val oldQVal = Q.getOrElse((currentState, action), 0.0)
    val newQVal = validActions.map( anAction => Q.getOrElse((newState, anAction), 0.0) ).max
    val updatedQVal = (1 - learningRate) * oldQVal + learningRate * (reward + futureDiscount * newQVal)
    Q = Q.updated((currentState, action), updatedQVal)
  }

}
