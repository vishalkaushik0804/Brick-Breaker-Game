package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int total_bricks = 21;
    private Timer time;

    private int delay = 1;
    private int playerX = 310;

    private int ball_pos_X = 120;
    private int ball_pos_Y = 350;

    private int ball_X_dir = -1;
    private int ball_Y_dir = -2;

    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this);
        time.start();
    }

    @Override
    public void paint(Graphics g) {
        //background
        g.setColor(Color.GRAY);
        g.fillRect(1, 1, 692, 592);

        //drawing map
        map.draw((Graphics2D) g);

        //borders
        g.setColor(Color.RED);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //scores
        g.setColor(Color.black);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Scores: " + score, 570, 30);

        //the paddle
        g.setColor(Color.RED);
        g.fillRect(playerX, 550, 100, 8);

        //the ball
        g.setColor(Color.BLUE);
        g.fillOval(ball_pos_X, ball_pos_Y, 20, 20);

        if (total_bricks <= 0) {
            play = false;
            ball_X_dir = 0;
            ball_Y_dir = 0;
            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won !! ", 260, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        if (ball_pos_Y > 570) {
            play = false;
            ball_X_dir = 0;
            ball_Y_dir = 0;
            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over!", 260, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 240, 350);

        }

        g.dispose();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (!play) {
            play = true;
            ball_pos_X = 120;
            ball_pos_Y = 350;
            ball_X_dir = -1;
            ball_Y_dir = -2;
            playerX = 310;
            score = 0;
            total_bricks = 21;
            map = new MapGenerator(3, 7);

            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        if (play) {
            if (new Rectangle(ball_pos_X, ball_pos_Y, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ball_Y_dir = -ball_Y_dir;
            }
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brick_width + 80;
                        int brickY = i * map.brick_height + 50;
                        int brick_width = map.brick_width;
                        int brick_height = map.brick_height;

                        Rectangle rect = new Rectangle(brickX, brickY, brick_width, brick_height);
                        Rectangle ballRect = new Rectangle(ball_pos_X, ball_pos_Y, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            total_bricks--;
                            score += 5;

                            if (ball_pos_X + 19 <= brickRect.x || ball_pos_X + 1 >= brickRect.x + brickRect.width) {
                                ball_X_dir = -ball_X_dir;
                            } else {
                                ball_Y_dir = -ball_Y_dir;
                            }
                            break A;
                        }
                    }
                }
            }

            ball_pos_X += ball_X_dir;
            ball_pos_Y += ball_Y_dir;

            if (ball_pos_X < 0) {
                ball_X_dir = -ball_X_dir;
            }
            if (ball_pos_Y < 0) {
                ball_Y_dir = -ball_Y_dir;
            }
            if (ball_pos_X > 670) {
                ball_X_dir = -ball_X_dir;
            }
        }
        repaint();
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }
}
