import java.awt._
import java.awt.event.ActionEvent
import javax.swing._
import scala.util.Random

object Collision {

  private val DIMENSION: Int = 20
  private val SCREEN_SIZE: Int = 800

  /**
   * A Box class to represent a moving square on the screen.
   */
  class Box(x: Int, y: Int, width: Int, height: Int, var colour: Color) extends Rectangle {

    // Stores own coordinate and dimension values
    private var h: Int = x
    private var v: Int = y
    private val w: Int = width
    private val hg: Int = height

    // Generates random speed and direction values for the Box object on the screen
    private val rand = new Random()
    private var speedX: Int = rand.between(-8, 8)
    private var speedY: Int = rand.between(-8, 8)

    /**
     * Draws the Box object on the screen.
     */
    def draw(g: Graphics): Unit = {
      g.setColor(colour)
      g.fillRect(h, v, width, height)
    }

    /**
     * Moves the Box object to the next position based on the current direction and speed.
     */
    def shift(): Unit = {
      this.h += speedX
      this.v += speedY
    }

    /**
     * Changes the horizontal direction of the Box object.
     */
    def flipSpeedX(): Unit = {
      this.speedX *= -1
    }

    /**
     * Changes the vertical direction of the Box object.
     */
    def flipSpeedY(): Unit = {
      this.speedY *= -1
    }

    // Getter methods
    override def getX: Double = h
    override def getY: Double = v
    def getColour: Color = colour
    override def getWidth: Double = w
    override def getHeight: Double = hg

    /**
     * Determines if 2 Box objects overlaps based on their bounds.
     */
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

    /**
     * A method which battles 2 Box objects, which are assumed to have intersected, based on the following logic:
     *    Red dominates Blue
     *    Blue dominates Green
     *    Green dominates Red
     */
    def battle(b: Box): Unit = {
      this.colour match {
        // This box is RED
        case Color.RED =>
          if (b.colour == Color.GREEN) {
            this.colour = Color.GREEN
          } else if (b.colour == Color.BLUE) {
            b.colour = Color.RED
          }

        // This box is GREEN
        case Color.GREEN =>
          if (b.colour == Color.RED) {
            b.colour = Color.GREEN
          } else if (b.colour == Color.BLUE) {
            this.colour = Color.BLUE
          }

        // This box is BLUE
        case _ =>
          if (b.colour == Color.RED) {
            this.colour = Color.RED
          } else if (b.colour == Color.GREEN) {
            b.colour = Color.BLUE
          }
      }
    }
  }

  /**
   * A Frame that extends JFrame to produce a screen for the boxes to move on.
   */
  class Frame(width: Int, height: Int) extends JFrame {

    // Stores a list of 30 Box objects
    private val boxes = new Array[Box](30)
    private val rand = new Random()

    private var counter: Int = 0
    for (i <- boxes.indices) {
      // Generates a random x and y coordinate for each Box object
      val xCord = rand.nextInt(width - DIMENSION)
      val yCord = rand.nextInt(height - DIMENSION)

      // Repeatedly assigns 3 colours (Red, Green, Blue) one after the other based on the value of counter
      if (counter == 0) {
        boxes(i) = new Box(xCord, yCord, DIMENSION, DIMENSION, Color.RED)
      } else if (counter == 1) {
        boxes(i) = new Box(xCord, yCord, DIMENSION, DIMENSION, Color.GREEN)
      } else {
        boxes(i) = new Box(xCord, yCord, DIMENSION, DIMENSION, Color.BLUE)
      }

      // Resets the counter every 2 increments to facilitate colour assignments
      if (counter < 2) {
        counter += 1
      } else {
        counter = 0
      }
    }

    // Sets the size of the screen
    setSize(width,height)

    /**
     * Responsible for drawing the screen, along with each Box object on the screen.
     */
    override def paint(g: Graphics): Unit = {
      val img: Image = createImage(width, height)
      val graphics: Graphics = img.getGraphics
      graphics.clearRect(0, 0,width,height)
      boxes.foreach(i => i.draw(graphics))
      g.drawImage(img, 0, 0, this)
    }

    // Default settings and title for the screen
    setTitle("Collision Machine")
    setLocationRelativeTo(null)
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    setVisible(true)

    // Performs updates to boxes every 50ms
    private val timer = new Timer(50, null)
    timer.addActionListener(new AbstractAction() {
      override def actionPerformed(e: ActionEvent): Unit = {

        boxes.foreach { x =>
          // Moves each box
          x.shift()

          // Performs a change of horizontal direction if the box reaches the horizontal edge of the screen
          val xHorizEnd = x.getX + x.getWidth
          if (x.getX < 0 || xHorizEnd > width) {
            x.flipSpeedX()
          }

          // Performs a change of vertical direction if the box reaches the vertical edge of the screen
          val xVertEnd = x.getY + x.getHeight
          if (x.getY < 10 || xVertEnd > height) {
            x.flipSpeedY()
          }

          // Redraws all the boxes
          repaint()
        }

        // Checks if all boxes are of the same colour, which would then cease any further movement by stopping the timer
        if (!boxes.exists(i => boxes(0).getColour != i.getColour)) {
          timer.stop()
          boxes(0).getColour match {
            case Color.RED => setTitle("RED has WON!!")
            case Color.GREEN => setTitle("GREEN has WON")
            case _ => setTitle("BLUE has WON!")
          }
        }

        // If any boxes intersect, they then battle
        for (i <- boxes.indices) {
          for (j <- i until boxes.length) {
            if (boxes(i).overlaps(boxes(j))) {
              boxes(i).battle(boxes(j))
            }
          }
        }
      }
    })

    timer.start()
  }

  def main(args: Array[String]): Unit = {
    new Frame(SCREEN_SIZE,SCREEN_SIZE)
  }
}