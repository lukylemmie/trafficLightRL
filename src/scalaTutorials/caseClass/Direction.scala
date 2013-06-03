package scalaTutorials.caseClass

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 3/06/13
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */


sealed trait Direction

case object Left extends Direction
case object Right extends Direction
case object Straight extends Direction
