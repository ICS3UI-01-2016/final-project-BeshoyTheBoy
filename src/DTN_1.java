
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;
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
    //CREATE 4 ARRAYS OF BLOCKS
    //ARRAY 1
    Rectangle[] niners = new Rectangle[7];
    //ARRAY 2
    Rectangle[] niners2 = new Rectangle[7];
    //ARRAY 3
    Rectangle[] niners3 = new Rectangle[7];
    //ARRAY 4
    Rectangle[] niners4 = new Rectangle[7];
    //SETUP Niner Variables
    //Space between each niner
    int ninerGap = 25;
    //Width of niner
    int ninerWidth = 35;
    //Height of niner
    int ninerHeight = 45;
    //Minimum distance from hallway walls
    int minDistance = 600 - ninerWidth;
    //Speed of Game
    int speed = 2;
    //Create control booleans
    boolean left = false;
    boolean right = false;
    //for score keeping
    boolean[] passedRow = new boolean[5];
    int score = 0;
    Font scoreFont = new Font("Arial", Font.ITALIC, 42);
    //create a random spot in order to take out a spot in the 5 squares, For every array
    int spot = (int) (Math.random() * 6);
    int spot2 = (int) (Math.random() * 6);
    int spot3 = (int) (Math.random() * 6);
    int spot4 = (int) (Math.random() * 6);
    //Load Images
    BufferedImage stickFigure = loadImage("player.png");
    BufferedImage ninersPNG = loadImage("Niner.png");
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
        //g.setColor(Color.BLUE);
        //draw rect
        //g.fillRect(player.x, player.y, player.width, player.height);
        g.drawImage(stickFigure, player.x, player.y, player.width, player.height, null);
        //Create hallway
        //set colour
        g.setColor(Color.black);
        //draw line
        g.drawLine(200, 0, 200, 600);
        //set colour
        g.setColor(Color.black);
        //draw line
        g.drawLine(600, 0, 600, 600);
        //score
        g.setColor(Color.white);
        g.setFont(scoreFont);
        g.drawString("" + score / 60, WIDTH - 170, 570);

        //Create niners
        //g.setColor(Color.red);
        //create for loop to generate niners for every Array
        for (int i = 0; i < niners.length; i++) {
            if (i != spot) {
                //g.fillRect(niners[i].x, niners[i].y, ninerWidth, ninerHeight);
                g.drawImage(ninersPNG, niners[i].x, niners[i].y, ninerWidth, ninerHeight, null);
            }
        }

        for (int i = 0; i < niners2.length; i++) {
            if (i != spot2) {
                //g.fillRect(niners2[i].x, niners2[i].y, ninerWidth, ninerHeight);
                g.drawImage(ninersPNG, niners2[i].x, niners2[i].y, ninerWidth, ninerHeight, null);
            }
        }

        for (int i = 0; i < niners3.length; i++) {
            if (i != spot3) {
                //g.fillRect(niners3[i].x, niners3[i].y, ninerWidth, ninerHeight);
                g.drawImage(ninersPNG, niners3[i].x, niners3[i].y, ninerWidth, ninerHeight, null);
            }
        }

        for (int i = 0; i < niners4.length; i++) {
            if (i != spot4) {
                //g.fillRect(niners4[i].x, niners4[i].y, ninerWidth, ninerHeight);
                g.drawImage(ninersPNG, niners4[i].x, niners4[i].y, ninerWidth, ninerHeight, null);
            }
        }
        // GAME DRAWING ENDS HERE
    }

    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            File file = new File(filename);
            img = ImageIO.read(file);
        } catch (Exception e) {
            //if there is error, print
            e.printStackTrace();
        }
        return img;
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        //create an X variable to create the first niner
        int startX = 201;

        //create for loop to generate niners
        for (int i = 0; i < niners.length; i++) {
            startX = 201 + (ninerWidth + ninerGap) * i;
            niners[i] = new Rectangle(startX, 150, ninerWidth, ninerHeight);
        }

        for (int i = 0; i < niners2.length; i++) {
            startX = 201 + (ninerWidth + ninerGap) * i;
            niners2[i] = new Rectangle(startX, -150, ninerWidth, ninerHeight);
        }

        for (int i = 0; i < niners3.length; i++) {
            startX = 201 + (ninerWidth + ninerGap) * i;
            niners3[i] = new Rectangle(startX, -450, ninerWidth, ninerHeight);
        }

        for (int i = 0; i < niners4.length; i++) {
            startX = 201 + (ninerWidth + ninerGap) * i;
            niners4[i] = new Rectangle(startX, -750, ninerWidth, ninerHeight);
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
            if (player.x + player.width >= 600) {
                player.x = 600 - player.width;
            }

            if (player.x <= 200) {
                player.x = 200;
            }

            //SCORE
            for (int i = 0; i < niners.length; i++) {
                score++;
            }

            //Make level scroll by duping niners and moving them down
            for (int i = 0; i < niners.length; i++) {
                niners[i].y = niners[i].y + speed;
                if (niners[i].y == 800) {
                    niners[i].y = niners4[i].y - 300;
                }
            }

            for (int i = 0; i < niners2.length; i++) {
                niners2[i].y = niners2[i].y + speed;
                if (niners2[i].y == 800) {
                    niners2[i].y = niners[i].y - 300;
                }
            }

            for (int i = 0; i < niners3.length; i++) {
                niners3[i].y = niners3[i].y + speed;
                if (niners3[i].y == 800) {
                    niners3[i].y = niners2[i].y - 300;
                }
            }

            for (int i = 0; i < niners4.length; i++) {
                niners4[i].y = niners4[i].y + speed;
                if (niners4[i].y == 800) {
                    niners4[i].y = niners3[i].y - 300;
                }
            }

            // did the player hit a niner?
            //go through all niners
            //ROW 1
            for (int i = 0; i < niners.length; i++) {
                // did the player hit a niner?
                if (player.intersects(niners[i])) {
                    done = true;
                } else if (player.intersects(niners[spot])) {
                    done = false;
                }
            }
            //ROW 2
            for (int i = 0; i < niners2.length; i++) {
                // did the player hit a niner?
                if (player.intersects(niners2[i])) {
                    done = true;
                } else if (player.intersects(niners2[spot2])) {
                    done = false;
                }
            }
            //ROW 3
            for (int i = 0; i < niners3.length; i++) {
                // did the player hit a niner?
                if (player.intersects(niners3[i])) {
                    done = true;
                } else if (player.intersects(niners3[spot3])) {
                    done = false;
                }
            }
            //ROW 4
            for (int i = 0; i < niners4.length; i++) {
                // did the player hit a niner?
                if (player.intersects(niners4[i])) {
                    done = true;
                } else if (player.intersects(niners4[spot4])) {
                    done = false;
                }
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
