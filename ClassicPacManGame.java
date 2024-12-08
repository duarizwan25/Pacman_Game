import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class ClassicPacManGame extends JPanel {
    private static final int TILE_SIZE = 32;
    private static final int ROWS = 15;
    private static final int COLS = 21;
    private static final Color WALL_COLOR = new Color(0, 0, 128);
    private static final Color PACMAN_COLOR = Color.YELLOW;
    private static final Color DOT_COLOR = Color.WHITE;
    private static final Color WIN_HURDLE_COLOR = Color.GREEN;

    private int pacManX = 1, pacManY = 1;
    private int score = 0;
    private int lives = 3;

    Enemy e1 = new Enemy(4, 3);
    Enemy e2 = new Enemy(3, 7);
    Enemy e3 = new Enemy(5, 11);

    public static final int[][] maze = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public ClassicPacManGame() {
        setPreferredSize(new Dimension(TILE_SIZE * COLS, TILE_SIZE * ROWS));
        setBackground(Color.BLACK);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int nextX = pacManX;
                int nextY = pacManY;

                switch (key) {
                    case KeyEvent.VK_LEFT -> nextX--;
                    case KeyEvent.VK_RIGHT -> nextX++;
                    case KeyEvent.VK_UP -> nextY--;
                    case KeyEvent.VK_DOWN -> nextY++;
                }

                if (nextX >= 0 && nextX < COLS && nextY >= 0 && nextY < ROWS && maze[nextY][nextX] != 1) {
                    pacManX = nextX;
                    pacManY = nextY;
                    if (maze[pacManY][pacManX] == 0) {
                        score += 10;
                        maze[pacManY][pacManX] = -1;  // Mark the dot as eaten
                    }
                    checkWinCondition();
                }
                repaint();
            }
        });
        setFocusable(true);

        Timer enemyMovementTimer = new Timer(300, e -> {
            e1.move();
            e2.move();
            e3.move();
            checkCollision();
            repaint();
        });
        enemyMovementTimer.start();
    }

    private void checkCollision() {
        if ((pacManX == e1.x && pacManY == e1.y) ||
                (pacManX == e2.x && pacManY == e2.y) ||
                (pacManX == e3.x && pacManY == e3.y)) {
            lives--;
            if (lives == 0) {
                JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
                System.exit(0);
            } else {
                pacManX = 1;
                pacManY = 1;
            }
        }
    }

    private void checkWinCondition() {
        if (pacManX == COLS - 2 && pacManY == ROWS - 2) {
            JOptionPane.showMessageDialog(this, "You Win! Final Score: " + score);
            System.exit(0);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (maze[row][col] == 1) {
                    g.setColor(WALL_COLOR);
                    g.fillRoundRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, 12, 12);
                } else if (maze[row][col] == 0) {
                    g.setColor(DOT_COLOR);
                    g.fillOval(col * TILE_SIZE + TILE_SIZE / 4, row * TILE_SIZE + TILE_SIZE / 4, TILE_SIZE / 4, TILE_SIZE / 4);
                }
            }
        }

        g.setColor(WIN_HURDLE_COLOR);
        g.fillRoundRect((COLS - 2) * TILE_SIZE, (ROWS - 2) * TILE_SIZE, TILE_SIZE, TILE_SIZE, 12, 12);

        g.setColor(PACMAN_COLOR);
        g.fillArc(pacManX * TILE_SIZE, pacManY * TILE_SIZE, TILE_SIZE, TILE_SIZE, 45, 270);

        e1.draw(g);
        e2.draw(g);
        e3.draw(g);

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 10);
        g.drawString("Lives: " + lives, 10, 25);
    }

    static class Enemy {
        int x, y;
        Random random = new Random();

        public Enemy(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void move() {
            int direction = random.nextInt(4);
            int nextX = x, nextY = y;

            switch (direction) {
                case 0 -> nextX++;
                case 1 -> nextX--;
                case 2 -> nextY++;
                case 3 -> nextY--;
            }

            if (nextX >= 0 && nextX < COLS && nextY >= 0 && nextY < ROWS && maze[nextY][nextX] != 1) {
                x = nextX;
                y = nextY;
            }
        }

        public void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillArc(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, 45, 270);
        }
    }
}