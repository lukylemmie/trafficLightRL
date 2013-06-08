package com.lempierzchalski.cs9417.ass3.simulation.simParameters

import scala.util.Random

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 8/06/13
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */
sealed trait CarSpawnChoice

case class UniformRate(probability: Double) extends CarSpawnChoice
case object SpecDefault extends CarSpawnChoice

object CarSpawnChoice {
  def UniformRate(probability: Double): (Int, Int) => Boolean = (_, _) => Random.nextDouble() < probability
  def SpecDefault: (Int, Int) => Boolean = (time, _) => time % (Random.nextInt(10) + 5) == 0
}
