package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    Random random = new Random();
    private int randomEats;
    private final int SIZEX = 864;
    private final int SIZEY = 384;
    private final int SIZE_DOT = 48;
    private final int ALL_DOTS = 200;
    private int[] snakePositionX = new int[ALL_DOTS];
    private int[] snakePositionY = new int[ALL_DOTS];
    private int dots;
    private int eatPositionX;
    private int eatPositionY;
    private Image[] head = new Image[4]; // head rotation r, l, u, d
    private Image tail;
    private Image[] eat = new Image[4];
    private Image background;
    private Image gameOver;
    private Timer timer;
    private boolean isInGame = true;
    private Direction direction = Direction.RIGHT;

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    public GameField() {
        loadImages();
        initializeGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    public void loadImages() {
        // eats rotation
        ImageIcon eatFish = new ImageIcon("fish.png");
        ImageIcon eatMeat = new ImageIcon("meat.png");
        ImageIcon eatMouse = new ImageIcon("mouse.png");
        ImageIcon eatChicken = new ImageIcon("chicken.png");
        eat[0] = eatFish.getImage();
        eat[1] = eatMeat.getImage();
        eat[2] = eatMouse.getImage();
        eat[3] = eatChicken.getImage();

        // head rotation
        ImageIcon headRight = new ImageIcon("nyanright.png");
        ImageIcon headLeft = new ImageIcon("nyanleft.png");
        ImageIcon headUP = new ImageIcon("nyanup.png");
        ImageIcon headDown = new ImageIcon("nyandown.png");
        head[0] = headRight.getImage();
        head[1] = headLeft.getImage();
        head[2] = headUP.getImage();
        head[3] = headDown.getImage();

        // cat tail
        ImageIcon catTail = new ImageIcon("rainbow.png");
        tail = catTail.getImage();

        // background and game over screen
        ImageIcon background = new ImageIcon("space.gif");
        this.background = background.getImage();
        ImageIcon gameOver = new ImageIcon("gameover.gif");
        this.gameOver = gameOver.getImage();
    }

    public void initializeGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            snakePositionX[i] = 144 - i * SIZE_DOT;
            snakePositionY[i] = 144;
        }
        timer = new Timer(400, this);
        timer.start();
        newEat();
    }

    public void newEat() {
        eatPositionX = random.nextInt(19) * SIZE_DOT;
        eatPositionY = random.nextInt(9) * SIZE_DOT;
        randomEats = random.nextInt(4);
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            snakePositionX[i] = snakePositionX[i - 1];
            snakePositionY[i] = snakePositionY[i - 1];
        }

        if (direction == Direction.LEFT) {
            snakePositionX[0] -= SIZE_DOT;
        }
        if (direction == Direction.RIGHT) {
            snakePositionX[0] += SIZE_DOT;
        }
        if (direction == Direction.UP) {
            snakePositionY[0] -= SIZE_DOT;
        }
        if (direction == Direction.DOWN) {
            snakePositionY[0] += SIZE_DOT;
        }
    }

    public void eatChecker() {
        if (snakePositionX[0] == eatPositionX && snakePositionY[0] == eatPositionY) {
            dots++;
            newEat();
        }
    }

    public void checkBarrier() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && snakePositionX[0] == snakePositionX[i] &&
                    snakePositionY[0] == snakePositionY[i]) {
                isInGame = false;
            }
            if (snakePositionX[0] > SIZEX) {
                isInGame = false;
            }
            if (snakePositionX[0] < 0) {
                isInGame = false;
            }
            if (snakePositionY[0] > SIZEY) {
                isInGame = false;
            }
            if (snakePositionY[0] < 0) {
                isInGame = false;
            }
        }
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && direction != Direction.RIGHT) {
                direction = Direction.LEFT;
            }
            if (key == KeyEvent.VK_RIGHT && direction != Direction.LEFT) {
                direction = Direction.RIGHT;
            }

            if (key == KeyEvent.VK_UP && direction != Direction.DOWN) {
                direction = Direction.UP;
            }
            if (key == KeyEvent.VK_DOWN && direction != Direction.UP) {
                direction = Direction.DOWN;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isInGame) {
            eatChecker();
            checkBarrier();
            move();
        }
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isInGame) {
            g.drawImage(background, 0, 0, this);
            g.drawImage(eat[randomEats], eatPositionX, eatPositionY, this);
            for (int i = 1; i < dots - 1; i++) {
                if (direction == Direction.RIGHT) {
                    g.drawImage(head[0], snakePositionX[0], snakePositionY[0], this);
                }
                if (direction == Direction.LEFT) {
                    g.drawImage(head[1], snakePositionX[0], snakePositionY[0], this);
                }
                if (direction == Direction.UP) {
                    g.drawImage(head[2], snakePositionX[0], snakePositionY[0], this);
                }
                if (direction == Direction.DOWN) {
                    g.drawImage(head[3], snakePositionX[0], snakePositionY[0], this);
                }
                g.drawImage(tail, snakePositionX[i], snakePositionY[i], this);
            }
        }else{
            setBackground(Color.black);
            g.drawImage(gameOver, 225, 50, this);
        }
    }
}
