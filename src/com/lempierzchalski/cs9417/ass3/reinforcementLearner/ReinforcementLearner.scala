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

  private var Q: Map[(State, Action), (Double, Int)] = immutable.HashMap.empty

  def getQTable: Map[(State, Action), (Double, Int)] = Q
  
  def getQValueTable: Map[(State, Action), Double] = Q.mapValues( _._1 )

  def getQCountTable: Map[(State, Action), Int] = Q.mapValues( _._2 )

  private def getAction(state: State): Action = validActions(Random.nextInt(validActions.size))

  def learn(currentState: State) {
    val action = getAction(currentState)
    val (newState, reward) = takeActionWithReward(currentState, action)
    val (oldQReward, oldCount) = Q.getOrElse((currentState, action), (0.0, 0))
    val (newQReward, _) = validActions.map( anAction => Q.getOrElse((newState, anAction), (0.0, 0)) ).max
    val updatedQReward = (1 - learningRate) * oldQReward + learningRate * (reward + futureDiscount * newQReward)
    Q = Q.updated((currentState, action), (updatedQReward, oldCount + 1))
  }

}
