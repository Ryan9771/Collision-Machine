import java.awt._
import java.awt.event.ActionEvent
import javax.swing._

object Collision {

  class Box(x: Int, y: Int, width: Int, height: Int, colour: Color) extends Rectangle {

    private var h: Int = x
    private var v: Int = y
    private val w: Int = width
    private val hg: Int = height

    def draw(g: Graphics): Unit = {
      g.setColor(colour)
      g.fillRect(h, v, width, height)
    }

    def shift(horizontal: Int, vertical: Int): Unit = {
      this.h += horizontal
      this.v += vertical
    }

    def getHoriz: Int = h
    def getVert: Int = v
    override def getWidth: Double = w
    override def getHeight: Double = hg
  }

  class Frame(width: Int, height: Int) extends JFrame {

    private var rock = new Box(10,100,50,50, Color.BLUE)
    private var paper = new Box(10, 300, 50, 50, Color.GRAY)
    setSize(width,height)

    override def paint(g: Graphics): Unit = {
      val img: Image = createImage(width, height)
      val graphics: Graphics = img.getGraphics
      graphics.clearRect(0, 0,width,height)
      rock.draw(graphics)
      paper.draw(graphics)
      g.drawImage(img, 0, 0, this)
    }

    setTitle("Collision Machine")
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    setVisible(true)

    var speedX = 7
    var speedY = 6
    val timer = new Timer(50, null)
    timer.addActionListener(new AbstractAction() {
      override def actionPerformed(e: ActionEvent): Unit = {

        rock.shift(speedX, speedY)

        val rockHorizEnd = rock.getHoriz + rock.getWidth
        if (rock.getHoriz < 0 || rockHorizEnd > width) {
          speedX *= -1
        }

        val rockVertEnd = rock.getVert + rock.getHeight
        if (rock.getVert < 10 || rockVertEnd > height) {
          speedY *= -1
        }

        repaint()
      }
    })

    timer.start()

  }

  def main(args: Array[String]): Unit = {
    new Frame(500,500)
  }


}