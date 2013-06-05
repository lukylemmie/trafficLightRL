package scalaTutorials

import swing._
import event._
import java.awt.{Color}
import java.awt.geom._

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 5/06/13
 * Time: 6:45 PM
 * To change this template use File | Settings | File Templates.
 */

object Events extends App {
  var paths = List[GeneralPath]()
  var currentPath = new GeneralPath()
  val artImage = javax.imageio.ImageIO.read(new java.io.File("./src/img/Dot.png"))
  var imgx = 0
  var imgy = 0

  val panel = new Panel {
    override def paint(g:Graphics2D) {
      g.setPaint(Color.blue)
      g.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
      g.setPaint(Color.black)
      for(path <- paths) g.draw(path)
      g.draw(currentPath)
      g.drawImage(artImage,imgx,imgy,null)
    }
    listenTo(mouse.clicks, mouse.moves,keys)
    reactions += {
      case e : MousePressed =>
        requestFocus()
        currentPath.moveTo(e.point.x, e.point.y)
        repaint()
      case e : MouseDragged =>
        currentPath.lineTo(e.point.x, e.point.y)
        repaint()
      case e : MouseReleased =>
        currentPath.lineTo(e.point.x, e.point.y)
        paths ::= currentPath
        currentPath = new GeneralPath()
        repaint()
      case e : MouseEntered =>
        requestFocus()
      case e : KeyPressed =>
        e.key match {
          case Key.Up =>
          case Key.Down =>
          case Key.Left =>
          case Key.Right =>
        }
    }
    preferredSize = new Dimension(600,600)
  }

  val frame = new MainFrame{
    title = "Events"
    contents = panel
    centerOnScreen()
  }

  frame.open()
  panel.requestFocus()
}
