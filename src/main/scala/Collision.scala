import java.awt._
import java.awt.event.ActionEvent
import javax.swing._
import scala.util.Random

object Collision {

  val DIMENSION: Int = 20

  class Box(x: Int, y: Int, width: Int, height: Int, var colour: Color) extends Rectangle {

    private var h: Int = x
    private var v: Int = y
    private val w: Int = width
    private val hg: Int = height

    val rand = new Random()

    private var speedX: Int = rand.between(3, 8)
    private var speedY: Int = rand.between(3, 8)

    def draw(g: Graphics): Unit = {
      g.setColor(colour)
      g.fillRect(h, v, width, height)
    }

    def shift(): Unit = {
      this.h += speedX
      this.v += speedY
    }

    def flipSpeedX(): Unit = {
      this.speedX *= -1
    }

    def flipSpeedY(): Unit = {
      this.speedY *= -1
    }

    def changeColour(col: Color): Unit = {
      colour = col
    }

    override def getX: Double = h
    override def getY: Double = v
    def getColour: Color = colour
    override def getWidth: Double = w
    override def getHeight: Double = hg

    def overlaps(r: Box): Boolean = {

      var tw = this.width
      var th = this.height
      var rw = r.getWidth
      var rh = r.getHeight
      if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0)
        return false
      val tx = this.h
      val ty = this.v
      val rx = r.getX
      val ry = r.getY
      rw += rx
      rh += ry
      tw += tx
      th += ty
      //      overflow || intersect//      overflow || intersect
      (rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry)
    }
  }

  class Frame(width: Int, height: Int) extends JFrame {

    private val rpsList = new Array[Box](30)
    val rand = new Random()

    var counter: Int = 0
    for (i <- rpsList.indices) {
      val xCord = rand.nextInt(width - DIMENSION)
      val yCord = rand.nextInt(height - DIMENSION)

      if (counter == 0) {
        rpsList(i) = new Box(xCord, yCord, DIMENSION, DIMENSION, Color.RED)
      } else if (counter == 1) {
        rpsList(i) = new Box(xCord, yCord, DIMENSION, DIMENSION, Color.GREEN)
      } else {
        rpsList(i) = new Box(xCord, yCord, DIMENSION, DIMENSION, Color.BLUE)
      }

      if (counter < 2) {
        counter += 1
      } else {
        counter = 0
      }

    }

    setSize(width,height)

    override def paint(g: Graphics): Unit = {
      val img: Image = createImage(width, height)
      val graphics: Graphics = img.getGraphics
      graphics.clearRect(0, 0,width,height)
      rpsList.foreach(i => i.draw(graphics))
      g.drawImage(img, 0, 0, this)
    }

    setTitle("Collision Machine")
    setLocationRelativeTo(null)
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    setVisible(true)

    val timer = new Timer(50, null)
    timer.addActionListener(new AbstractAction() {
      override def actionPerformed(e: ActionEvent): Unit = {

        rpsList.foreach { x =>

          x.shift()

          val xHorizEnd = x.getX + x.getWidth
          if (x.getX < 0 || xHorizEnd > width) {
            x.flipSpeedX()
          }

          val xVertEnd = x.getY + x.getHeight
          if (x.getY < 10 || xVertEnd > height) {
            x.flipSpeedY()
          }

          repaint()
        }

        if (!rpsList.exists(i => rpsList(0).getColour != i.getColour)) {
          timer.stop()
          rpsList(0).getColour match {
            case Color.RED => setTitle("RED has WON!!")
            case Color.GREEN => setTitle("GREEN has WON")
            case _ => setTitle("BLUE has WON!")
          }
        }


        //  R    P     S
        // RED GREEN BLUE
        for (i <- rpsList.indices) {
          for (j <- i until rpsList.length) {
            if (rpsList(i).overlaps(rpsList(j))) {

              if (rpsList(i).getColour == Color.RED) {
                if (rpsList(j).getColour == Color.GREEN) {
                  rpsList(i).changeColour(Color.GREEN)
                } else if (rpsList(j).getColour == Color.BLUE) {
                  rpsList(j).changeColour(Color.RED)
                }
              } else if (rpsList(i).getColour == Color.GREEN) {
                if (rpsList(j).getColour == Color.RED) {
                  rpsList(j).changeColour(Color.GREEN)
                } else if (rpsList(j).getColour == Color.BLUE) {
                  rpsList(i).changeColour(Color.BLUE)
                }
              } else { // Colour is BLUE
                if (rpsList(j).getColour == Color.RED) {
                  rpsList(i).changeColour(Color.RED)
                } else if (rpsList(j).getColour == Color.GREEN) {
                  rpsList(j).changeColour(Color.BLUE)
                }
              }

            }
          }
        }
      }
    })



    timer.start()
  }

  def main(args: Array[String]): Unit = {
    new Frame(800,800)
  }


}