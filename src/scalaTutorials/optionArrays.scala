package scalaTutorials

import scala.collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 3/06/13
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
object optionArrays extends App {
  val ROAD_LENGTH = 10
  var lane = new Array[Option[Int]](ROAD_LENGTH)
  val lane2 = Array.fill[Option[Int]](ROAD_LENGTH)(None)

  for(car <- lane){
    print(f"$car ")
  }

  println()

  for(i <- Range(0,lane size)) {
    lane(i) = Some(1)
  }

  lane = lane.map( _ => Some(4))

  for(car <- lane){
    car match {
      case None => print (f"0 ")
      case Some(thing) => print (f"$thing " )
    }
  }

  println()

  for(car <- lane2){
    car match {
      case None => print (f"0 ")
      case Some(thing) => print (f"$thing " )
    }
  }
}
