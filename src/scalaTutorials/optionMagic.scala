package scalaTutorials

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 31/05/13
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */
object optionMagic extends App {
  println("Hello world!")
  val opt = Option[String]("Hello")
  opt match {
    case Some(str) => println(str)
    case None => println("OMFG NO STRING NOOOOO")
  }
}
