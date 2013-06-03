package scalaTutorials

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 28/05/13
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
object Main extends App {
  val kat = new Kitty(name = "Hello", weight = 23)
  val targetKat = new Kitty(name = "Bob", weight = -12)
  val targetKat2 = new Kitty(name = "Steve", weight = 10000)
  println(f"Normal Scratch: ${kat.normalScratch(None, Some(targetKat)) }")
  println(f"Fun Scratch: ${kat.funScratch(None, Some(targetKat)) }")
  println(f"Crazy Scratch: ${kat.crazyScratch(None, Some(targetKat)) }")
  println(f"Crazy Sequence Scratch: ${kat.crazySequenceScratch(Seq(None, Some(targetKat), Some(targetKat2))) }")
  println(f"Star Scratch: ${kat.starScratch(None, Some(targetKat), Some(targetKat2)) }")
}
