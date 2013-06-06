package com.lempierzchalski.cs9417.ass3.reinforcementLearner

import collection.immutable

/**
 * User: Pierzchalski
 * Date: 3/06/13
 * Package: com.lempierzchalski.cs9417.ass3.reinforcementLearner
 * Project: trafficLightRL
 */
class ReinforcementLearner[State, Action](
                                           val validActions: Set[Action],
                                           val takeActionWithReward: (State, Action) => (State, Double)
                                           ){
  var stateQ: Map[State, Double] = immutable.HashMap.empty

}
