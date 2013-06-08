package com.lempierzchalski.cs9417.ass3.simulation.simParameters

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */

sealed trait LearningRateChoice

case class ConstantRateLearning(learningRate: Double) extends LearningRateChoice
case object ConvergingRateLearning extends LearningRateChoice
