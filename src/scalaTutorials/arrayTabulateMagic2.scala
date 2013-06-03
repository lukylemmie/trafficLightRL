package scalaTutorials

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 31/05/13
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
object arrayTabulateMagic2 extends App {
  println("Hello world!")
  val x = Array.tabulate(3,5)( (row, col) => (row + 1) * (col + 1) )
  for (row <- x) {
    for (col <- row) {
      print(f"$col%2d ")
    }
    println()
  }
}
