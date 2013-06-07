package com.lempierzchalski.cs9417.ass3.reinforcementLearner

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 7/06/13
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
object LearningRateStrategies {
  type QTableType[State, Action] = ReinforcementLearner.QTableType[State, Action]
  type LearningRateFunction[State, Action] = (State, Action, QTableType[State, Action]) => Double

  def constantRate[State, Action](learningRate: Double):
    LearningRateFunction[State, Action] =
    (state, action, qTable) => learningRate

  def convergingRate[State, Action]:
    LearningRateFunction[State, Action] =
    (state, action, qTable) => {
      1.0 / (2.0 + qTable.getOrElse((state, action), (0.0, 0))._2)
    }
}
