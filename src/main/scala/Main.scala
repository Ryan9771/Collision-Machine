import java.awt.{BorderLayout, Color, Dimension, FlowLayout, Graphics, Rectangle}
import java.awt.event.ActionEvent
import javax.swing.{AbstractAction, JComponent, JFrame, JScrollPane, JTextArea, Timer, WindowConstants}

object Main {

  class RPSObject(x: Int, y: Int, width: Int, height: Int, var identity: String) extends JComponent {
    setBounds(x, y, width, height)
    setOpaque(true)

    def setIdentity(newIdentity: String): Unit = {
      this.identity = newIdentity
      repaint()
    }

    override def paintComponent(g: Graphics): Unit = {
      super.paintComponent(g)
      g.setColor(Color.BLACK)
      g.drawString(identity, x + width / 2, y + height / 2)
    }
  }

  def main(args: Array[String]): Unit = {
    val frame = new JFrame("Rock Paper Scissors")
    frame.setSize(800, 600)
    frame.setLayout(new FlowLayout)
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    // Create the rock, paper, and scissors objects and add them to the JFrame
    val rock = new RPSObject(0, 200, 100, 100, "Rock")
    val paper = new RPSObject(0, 500, 100, 100, "Paper")
    val scissors = new RPSObject(0, 700, 100, 100, "Scissors")

    frame.add(rock)
    frame.add(paper)
    frame.add(scissors)

    // Create the timer and start it
    val speed = 5
    val timer = new Timer(30, new AbstractAction() {
      override def actionPerformed(e: ActionEvent): Unit = {
        rock.setLocation(rock.getX + speed, rock.getY)
        paper.setLocation(paper.getX + speed, paper.getY)
        scissors.setLocation(scissors.getX + speed, scissors.getY)

        if (rock.getBounds(new Rectangle).intersects(paper.getBounds(new Rectangle))) {
          rock.setIdentity("Paper")
        } else if (rock.getBounds(new Rectangle).intersects(scissors.getBounds(new Rectangle))) {
          scissors.setIdentity("Rock")
        } else if (paper.getBounds(new Rectangle).intersects(rock.getBounds(new Rectangle))) {
          rock.setIdentity("Paper")
        } else if (paper.getBounds(new Rectangle).intersects(scissors.getBounds(new Rectangle))) {
          paper.setIdentity("Scissors")
        } else if (scissors.getBounds(new Rectangle).intersects(paper.getBounds(new Rectangle))) {
          paper.setIdentity("Scissors")
        } else if (scissors.getBounds(new Rectangle).intersects(rock.getBounds(new Rectangle))) {
          scissors.setIdentity("Rock")
        }
      }
    })

    timer.start()

    // Show the JFrame
    frame.setVisible(true)
  }

}