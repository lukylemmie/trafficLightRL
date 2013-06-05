package scalaTutorials

import swing._
import event._
import java.awt.{Color}

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 5/06/13
 * Time: 6:45 PM
 * To change this template use File | Settings | File Templates.
 */

object Events extends App {
  val panel = new Panel {
    override def paint(g:Graphics2D) {

    }
    preferredSize = new Dimension(600,600)

  }

  val frame = new MainFrame{
    title = "Events"
    contents = panel
    centerOnScreen()
  }

  frame.open()
}
