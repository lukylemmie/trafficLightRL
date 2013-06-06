package scalaTutorials.privacyTests

import scala.collection.immutable

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 6/06/13
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
class TestClass {
  private var katIDs: immutable.Set[Int] = Set(1, 2, 3, 4)
  def updateKatIDs(newID: Int) {
    katIDs = katIDs + newID
  }
  def getKatID = katIDs
}
