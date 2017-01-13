
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author awadb3223
 */
public class DTN_1 extends JComponent implements KeyListener {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    // GAME VARIABLES
    // Create a custom color for background/floor
    Color floorColor = new Color(204, 204, 204);
    //Create Main Player
    Rectangle player = new Rectangle(382, 500, 35, 55);
    //Create 4 niners in 1 array (row)
    Rectangle[] niners = new Rectangle[5];
    //Create Hallway
    Rectangle hallway = new Rectangle(300, 600, 300, 600);
    //SETUP Niner Variables
    //Space between each niner
    int ninerGap = 25;
    //Width of niner
    int ninerWidth = 35;
    //Height of niner
    int ninerHeight = 45;
    //Minimum distance from hallway walls
    int minDistance = 600 - ninerWidth;
    //Create control booleans
    boolean left = false;
    boolean right = false;

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE 

        //Create background
        //Change colour
        g.setColor(Color.lightGray);
        //Create background
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //Create Main Character
        //Set the colour
        g.setColor(Color.BLUE);
        //draw rect
        g.fillRect(player.x, player.y, player.width, player.height);

        //Create hallway
        //set colour
        g.setColor(Color.black);
        //draw line
        g.drawLine(200, 0, 200, 600);
        //set colour
        g.setColor(Color.black);
        //draw line
        g.drawLine(600, 0, 600, 600);

        //Create niners
        g.setColor(Color.red);
        //Fill rectangle



        // GAME DRAWING ENDS HERE
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;


        //SETUP the Niners
        int ninerX = 201;

        for (int i = 0; i < niners.length; i++) {
            ninerX = (ninerX + ninerWidth) + ninerWidth;
        }


        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 

            //MAIN PLAYER MOVEMENTS
            if (left) {
                player.x = player.x - 3;
            }
            if (right) {
                player.x = player.x + 3;
            }

            //Make Player stay inside Hallway
            if(player.x + player.width >= 600 ){
               player.x = 600 - player.width; 
            }
            
            if(player.x <= 200){
                player.x = 200;
            }
            // GAME LOGIC ENDS HERE 


            // update the drawing (calls paintComponent)

            repaint();



            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            try {
                if (deltaTime > desiredTime) {
                    //took too much time, don't wait
                    Thread.sleep(1);
                } else {
                    // sleep to make up the extra time
                    Thread.sleep(desiredTime - deltaTime);
                }
            } catch (Exception e) {
            };
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        DTN_1 game = new DTN_1();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);

        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            right = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            right = false;
        }

    }
}