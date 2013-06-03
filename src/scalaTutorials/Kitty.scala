package scalaTutorials

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 1/06/13
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
class Kitty(val weight: Int, val name: String) {
  private val doubleWeight = weight * 2
  def normalScratch(optionKitty: Option[Kitty], otherOptionKitty: Option[Kitty]): String = {
    val firstTargetWarcry = optionKitty match {
      case None => "I am scratching thin air"
      case Some(cat) => f"I am scratching ${cat.name}"
    }
    val secondTargetWarcry = otherOptionKitty match {
      case None => "I am scratching thin air"
      case Some(cat) => f"I am scratching ${cat.name}"
    }
    firstTargetWarcry ++ " and " ++ secondTargetWarcry
  }

  def funScratch(optionKitty: Option[Kitty], otherOptionKitty: Option[Kitty]): String =
  f"${
    optionKitty match {
      case None => "I am scratching thin air"
      case Some(cat) => "I am scratching %s".format(cat.name)
    }
  } and ${
    otherOptionKitty match {
      case None => "I am scratching thin air"
      case Some(cat) => "I am scratching %s".format(cat.name)
    }
  }"

  def crazyScratch(optionKitty: Option[Kitty], otherOptionKitty: Option[Kitty]): String = {
    val targets = List(optionKitty, otherOptionKitty)
    targets.map(
      _ match {
        case None => "I am scratching thin air"
        case Some(cat) => "I am scratching %s".format(cat.name)
      }
    ).reduce( _ ++ " and " ++ _ )
  }

  def crazySequenceScratch(optionKittySeq: Seq[Option[Kitty]]): String =
    optionKittySeq.map(
      _ match {
        case None => "I am scratching thin air"
        case Some(cat) => "I am scratching %s".format(cat.name)
      }
    ).reduce( _ ++ " and " ++ _ )

  def starScratch(optionKitties: Option[Kitty]*): String =
    optionKitties.map(
      _ match {
        case None => "I am scratching thin air"
        case Some(cat) => "I am scratching %s".format(cat.name)
      }
    ).reduce( _ ++ " and " ++ _ )
}
