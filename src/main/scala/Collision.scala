import java.awt.{BorderLayout, Color, Dimension, FlowLayout, Graphics, Graphics2D, Rectangle}
import java.awt.event.ActionEvent
import javax.swing.{AbstractAction, JComponent, JFrame, JPanel, JScrollPane, JTextArea, OverlayLayout, Timer, WindowConstants}

object Collision {

  class RPS(x: Int, y: Int, width: Int, height: Int, identity: String) extends JComponent {

    override def paint(g: Graphics): Unit = {
      val g2d = g.asInstanceOf[Graphics2D]
      if (identity.equals("Rock")) {
        g2d.setColor(Color.BLACK)
      } else if (identity.equals("Paper")) {
        g2d.setColor(Color.CYAN)
      } else {
        g2d.setColor(Color.ORANGE)
      }

      g.fillRect(x, y, width, height)
    }
  }

  class Frame extends JFrame {
    val rock = new RPS(10, 100, 50, 50, "Rock")
    val paper = new RPS(10, 300, 50, 50, "Paper")

    override def paint(g: Graphics): Unit = {
      rock.paint(g)
      paper.paint(g)
    }

    this.setSize(500, 500)
    this.setVisible(true)
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  }

  def main(args: Array[String]): Unit = {
    new Frame
  }

}