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
  val delay = 10
  val dots = Array.fill(600)(new java.awt.Point(100 + util.Random.nextInt(400), 100 + util.Random.nextInt(400)))
  val circleRad = 10

  val panel = new Panel {
    override def paint(g:Graphics2D) {
      g.setPaint(Color.blue)
      g.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
      g.setPaint(Color.black)
      for(path <- paths) g.draw(path)
      g.draw(currentPath)
      g.drawImage(artImage,imgx,imgy,null)
      g.setPaint(Color.green)
      for(p <- dots){
        g.fill(new Ellipse2D.Double(p.x - circleRad / 2, p.y - circleRad / 2, circleRad, circleRad))
      }
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
        val shift = 1
        e.key match {
          case Key.Up =>
            if(paths.forall(!_.intersects(imgx, imgy - shift, artImage.getWidth, artImage.getHeight))){
              imgy -= shift
              repaint()
            }
          case Key.Down =>
            if(paths.forall(!_.intersects(imgx, imgy + shift, artImage.getWidth, artImage.getHeight))){
              imgy += shift
              repaint()
            }
          case Key.Left =>
            if(paths.forall(!_.intersects(imgx - shift, imgy, artImage.getWidth, artImage.getHeight))){
              imgx -= shift
              repaint()
            }
          case Key.Right =>
            if(paths.forall(!_.intersects(imgx + shift, imgy, artImage.getWidth, artImage.getHeight))){
              imgx += shift
              repaint()
            }
        }
    }
    preferredSize = new Dimension(600,600)
  }

  val timer = new javax.swing.Timer(delay,Swing.ActionListener(e => {
    for(p <- dots) {
      p.x += util.Random.nextInt(3) - 1
      p.y += util.Random.nextInt(3) - 1
    }
    panel.repaint()
  }))

  val frame = new MainFrame{
    title = "Events"
    contents = panel
    centerOnScreen()
  }

  frame.open()
  panel.requestFocus()
  timer.start()
}
