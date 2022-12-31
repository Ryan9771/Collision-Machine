import java.awt._
import java.awt.event.ActionEvent
import javax.swing._
import scala.util.Random

object Collision {

  class Box(x: Int, y: Int, width: Int, height: Int, colour: Color) extends Rectangle {

    private var h: Int = x
    private var v: Int = y
    private val w: Int = width
    private val hg: Int = height

    val rand = new Random()

    private var speedX: Int = rand.between(5, 16)
    private var speedY: Int = rand.between(5, 16)

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

    def getHoriz: Int = h
    def getVert: Int = v
    override def getWidth: Double = w
    override def getHeight: Double = hg
  }

  class Frame(width: Int, height: Int) extends JFrame {

    private val rpsList = new Array[Box](30)
    val rand = new Random()

    var counter: Int = 0
    for (i <- rpsList.indices) {
      val xCord = rand.nextInt(width - 25)
      val yCord = rand.nextInt(height - 25)

      if (counter == 0) {
        rpsList(i) = new Box(xCord, yCord, 25, 25, Color.RED)
      } else if (counter == 1) {
        rpsList(i) = new Box(xCord, yCord, 25, 25, Color.GREEN)
      } else {
        rpsList(i) = new Box(xCord, yCord, 25, 25, Color.BLUE)
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
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    setVisible(true)

    val timer = new Timer(50, null)
    timer.addActionListener(new AbstractAction() {
      override def actionPerformed(e: ActionEvent): Unit = {

        rpsList.foreach { x =>
          x.shift()

          val xHorizEnd = x.getHoriz + x.getWidth
          if (x.getHoriz < 0 || xHorizEnd > width) {
            x.flipSpeedX()
          }

          val xVertEnd = x.getVert + x.getHeight
          if (x.getVert < 10 || xVertEnd > height) {
            x.flipSpeedY()
          }

          repaint()
        }
      }
    })

    timer.start()
  }

  def main(args: Array[String]): Unit = {
    new Frame(500,500)
  }


}