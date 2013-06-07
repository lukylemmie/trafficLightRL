package scalaTutorials.privacyTests

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 6/06/13
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
object Main extends App {
  val test = new TestClass
  val myKatId = test.getKatID
  myKatId + 10
  println(myKatId)
  test.hello = 3
  println(test.hello)
}
