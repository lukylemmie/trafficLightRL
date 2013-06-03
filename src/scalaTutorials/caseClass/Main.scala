package scalaTutorials.caseClass

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 3/06/13
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
object Main extends App {
  val dList: List[Direction] = List(Left, Left, Right, Left)
  println(
    dList.map(
      {
        case Left => "rong turnr"
        case Right => "fuarks"
      }
    )
  )
}
