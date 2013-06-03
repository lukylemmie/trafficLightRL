package scalaTutorials

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 31/05/13
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
object arrayTabulateMagic extends App {
  println("Hello world!")
  val x = Array.tabulate(3,5)( (row, col) => row * col )
  for (row <- x; col <- row) {
    println(col)
  }
}
