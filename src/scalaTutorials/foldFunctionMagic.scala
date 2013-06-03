package scalaTutorials

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 1/06/13
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
object foldFunctionMagic extends App {
  val ls = List[Int](1, 3, 4, -2, 7)
  val startValue = 0
  def sum(a: Int, b:Int): Int = a + b
  val sumImplicit = ls.fold(startValue)( _ + _ )
  val sumExplicit = ls.fold(startValue)( sum )

  println(f"explict: $sumExplicit, implict: $sumImplicit")
}
