import java.awt._
import java.awt.event.ActionEvent
import javax.swing._

object Collision {

  class Box(x: Int, y: Int, width: Int, height: Int, colour: Color) extends Rectangle {

    private var h: Int = x
    private var v: Int = y
    private var w: Int = width
    private var hg: Int = height

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
  }

  class Frame(width: Int, height: Int) extends JFrame {

    var rock = new Box(10,100,50,50, Color.BLUE)
    var paper = new Box(10, 300, 50, 50, Color.GRAY)
    setSize(width,height)


    override def paint(g: Graphics): Unit = {
      var img: Image = createImage(width, height)
      var graphics: Graphics = img.getGraphics
      graphics.clearRect(0, 0,width,height)
      rock.draw(graphics)
      paper.draw(graphics)
      g.drawImage(img, 0, 0, this)
    }

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    setVisible(true)

    val speed = 5
    val timer = new Timer(50, null)
    timer.addActionListener(new AbstractAction() {
      override def actionPerformed(e: ActionEvent): Unit = {

        var rockPos = rock.getHoriz + speed + rock.getWidth
        var paperPos = paper.getHoriz + speed + paper.getWidth
        printf("(rockPos: %f), (paperPos: %f), (width: %f) \n", rockPos, paperPos, rock.getWidth)

        if (rockPos > 0 && rockPos < width) {
          rock.shift(speed, 0)
        } else {
          timer.stop()
        }

        if (paperPos > 0 && paperPos < width) {
          paper.shift(speed, 0)
        } else {
          timer.stop()
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