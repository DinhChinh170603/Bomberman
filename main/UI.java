package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

// xu ly tat ca giao dien ng dung
// menu,in chu,pause,option...
public class UI {
    private GamePanel gp;
    private Graphics2D g2;
    private Font arial_40;

    private BufferedImage menuImage;
    private BufferedImage endgame;
    public int commandNumber = 0;
    public int subState = 0; // number of State

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        try {
            menuImage = ImageIO.read(getClass().getResourceAsStream("/res/menu.jpg"));
            endgame = ImageIO.read(getClass().getResourceAsStream("/res/endgame.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        // set g2 to use in anothermethods
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        // menu
        if (gp.state == gp.titleState) {
            drawTitleScreen(g2);
        }
        if (gp.state == gp.playState) {
            drawScore(g2);
        }
        if (gp.state == gp.optionsState) {
            drawOptionsScreen();
        }
        if (gp.state == gp.gameOverState) {
            drawGameOverScreen();
        }
        if (gp.state == gp.gameWinState) {
            drawGameStateWin();
        }
    }

    // public void drawPauseScreen(Graphics2D g2) {
    // this.g2 = g2;
    // g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
    // String text = "PAUSED";
    // int x = getForCenteredtext(text);
    //
    // int y = gp.WINDOW_HEIGHT / 2;
    //
    // g2.drawString(text, x, y);
    // }

    private void drawTitleScreen(Graphics2D g2) {
        // Ten Game
        // draw menu
        g2.drawImage(menuImage, 0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT, null);
        // draw game name


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
        String text = "1 PLAYER";
        int x = align(text);
        int y = gp.TILESIZE * 6;
        if (commandNumber == 0) {
            g2.setColor(Color.red);
            g2.drawString(">", x - gp.TILESIZE - 24, y + 43);
            g2.setColor(Color.white);
            g2.drawString(">", x - gp.TILESIZE - 20, y + 43);

        }

        x = align(text);
        y += gp.TILESIZE * 2;
        if (commandNumber == 1) {
            g2.setColor(Color.red);
            g2.drawString(">", x - gp.TILESIZE - 34, y + 50);
            g2.setColor(Color.white);
            g2.drawString(">", x - gp.TILESIZE - 30, y + 50);

        }

        text = "QUIT";
        x = align(text);
        y += gp.TILESIZE * 2;
        // g2.drawString(text, x, y);
        if (commandNumber == 2) {
            g2.setColor(Color.red);
            g2.drawString(">", x - gp.TILESIZE - 14, y + 65);
            g2.setColor(Color.white);
            g2.drawString(">", x - gp.TILESIZE - 10, y + 65);
        }
    }

    private void drawOptionsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        // design
        int frameX = gp.TILESIZE * 8;
        int frameY = gp.TILESIZE;
        int frameWidth = gp.TILESIZE * 8;
        int frameHeight = gp.TILESIZE * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                options_top(frameX, frameY);
                break;
            // case 1: /* full screen */
            // break;
            case 1:
                options_control(frameX, frameY);
                break;
            case 2:
                options_endGameConfirmation(frameX, frameY);
                break;
        }

        gp.input.enterPressed = false;
    }

    private void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        // option:
        String text = "Options";
        textX = align(text) - 30;
        textY = frameY + gp.TILESIZE;
        g2.drawString(text, textX, textY);


        // music
        textX = frameX + gp.TILESIZE;
        textY += gp.TILESIZE * 2;
        // textY += gp.TILESIZE;
        g2.drawString("Music", textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
        }

        // se
        textY += gp.TILESIZE;
        g2.drawString("SE", textX, textY);
        if (commandNumber == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        // control
        textY += gp.TILESIZE;
        g2.drawString("Control", textX, textY);
        if (commandNumber == 2) {
            g2.drawString(">", textX - 25, textY);
            if (gp.input.enterPressed == true) { // go to control
                subState = 1;
                commandNumber = 0;
            }
        }

        // end
        textY += gp.TILESIZE;
        g2.drawString("End Game", textX, textY);
        if (commandNumber == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.input.enterPressed == true) {
                subState = 2;
                commandNumber = 0;
            }
        }

        // back
        textY += gp.TILESIZE * 2;
        g2.drawString("Back", textX, textY);
        if (commandNumber == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.input.enterPressed == true) {
                gp.state = gp.playState;
                commandNumber = 0;
            }
        }



        // volume +/-
        textX = frameX + gp.TILESIZE * 5 - 12;
        textY = frameY + gp.TILESIZE * 2 + 24;
        // textY += gp.TILESIZE;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 120, 24); // (120/5=24)
        int volumeWidth = 24 * gp.sound.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // se +/-
        textY += gp.TILESIZE;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 120, 24);
        int volWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volWidth, 24);
    }

    private void drawSubWindow(int x, int y, int width, int height) {

        // draw black rectangle
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    // tutorial
    private void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Control";
        textX = align(text) - 30;
        textY = frameY + gp.TILESIZE;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.TILESIZE;
        textY += gp.TILESIZE * 2;
        g2.drawString("Move", textX, textY);

        textY += gp.TILESIZE;
        g2.drawString("Bomb", textX, textY);

        textY += gp.TILESIZE;
        g2.drawString("Options", textX, textY);

        // tutorial
        textX = frameX + gp.TILESIZE * 6 - gp.TILESIZE;
        textY = frameY + gp.TILESIZE * 3;
        g2.drawString("WASD", textX, textY);

        textY += gp.TILESIZE;
        g2.drawString("J", textX, textY);

        textY += gp.TILESIZE;
        g2.drawString("P", textX, textY);

        // back
        textX = frameX + gp.TILESIZE + 30;
        textY = frameY + gp.TILESIZE * 9;
        g2.drawString("Back", textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.input.enterPressed == true) {
                subState = 0;
                commandNumber = 2;
            }
        }
    }

    // out?
    private void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.TILESIZE;
        int textY = frameY + gp.TILESIZE * 2;

        String s = "Quit the game \nand return to the \ntitle screen?";

        for (String line : s.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // YES
        String textYes = "Yes";
        textX = align(textYes) - gp.TILESIZE;
        textY += gp.TILESIZE * 3;
        g2.drawString(textYes, textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.input.enterPressed == true) {
                subState = 0;
                gp.state = gp.titleState;
                gp.stopMusic();
                gp.playMusic(3);
                gp.setupGame();
            }
        }

        // NO
        String textNo = "No";
        textY += gp.TILESIZE;
        g2.drawString(textNo, textX, textY);
        if (commandNumber == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.input.enterPressed == true) {
                subState = 0;
                commandNumber = 3;
            }
        }
    }

    private void drawGameOverScreen() {
        // make dark
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT);

        int x;
        int y;
        String text;

        // make shadow
        if (gp.pNum == 2) {
            g2.drawImage(endgame, 0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT, null);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70f));
           text = "The game ended in a draw!!!";
        } else {
            g2.drawImage(endgame, 0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT, null);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80f));
            text = "Game Over";
        }
        g2.setColor(Color.black);
        x = align(text);
        y = gp.TILESIZE * 4;
        g2.drawString(text, x, y);

        // main
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // retry
        g2.setFont(g2.getFont().deriveFont(45f));
        text = "Retry";
        x = align(text);
        y += gp.TILESIZE * 3;
        g2.drawString(text, x - 20, y);
        if (commandNumber == 0) {
            g2.drawString(">", x - 60, y);
        }

        // back to menu
        text = "Menu";
        x = align(text);
        y += 55;
        g2.drawString(text, x - 20, y);
        if (commandNumber == 1) {
            g2.drawString(">", x - 60, y);
        }
    }

    private void drawGameStateWin() {
        // make dark
        g2.setColor(new Color(77, 75, 75, 150));
        g2.fillRect(0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT);
        if (gp.pNum == 2) {
            if (gp.score1 > gp.score2) {
                int x;
                int y;
                String text;
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60f));

                // make shadow
                text = "PLAYER 1 WIN!!!";
                g2.setColor(Color.white);
                x = align(text);
                y = gp.TILESIZE * 4;
                g2.drawString(text, x, y);

                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45f));
                text = "Player 1 score: " + gp.score1 * 100;
                g2.setColor(Color.white);
                x = align(text);
                y += gp.TILESIZE * 2;
                g2.drawString(text, x, y);

                text = "Player 2 score: " + gp.score2 * 100;
                g2.setColor(Color.white);
                x = align(text);
                y += gp.TILESIZE * 2;
                g2.drawString(text, x, y);

                g2.setFont(g2.getFont().deriveFont(45f));
                text = "Play again";
                x = align(text);
                y += gp.TILESIZE * 2;
                g2.drawString(text, x, y);
                if (commandNumber == 0) {
                    g2.drawString(">", x - 40, y);
                }

                // back to menu
                text = "Menu";
                x = align(text);
                y += 55;
                g2.drawString(text, x, y);
                if (commandNumber == 1) {
                    g2.drawString(">", x - 40, y);
                }
            } else if (gp.score1 < gp.score2) {
                int x;
                int y;
                String text;
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60f));

                // make shadow
                text = "PLAYER 2 WIN!!!";
                g2.setColor(Color.white);
                x = align(text);
                y = gp.TILESIZE * 4;
                g2.drawString(text, x, y);

                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45f));
                text = "Player 1 score: " + gp.score1 * 100;
                g2.setColor(Color.white);
                x = align(text);
                y += gp.TILESIZE * 2;
                g2.drawString(text, x, y);

                text = "Player 2 score: " + gp.score2 * 100;
                g2.setColor(Color.white);
                x = align(text);
                y += gp.TILESIZE * 2;
                g2.drawString(text, x, y);

                g2.setFont(g2.getFont().deriveFont(45f));
                text = "Play again";
                x = align(text);
                y += gp.TILESIZE * 2;
                g2.drawString(text, x, y);
                if (commandNumber == 0) {
                    g2.drawString(">", x - 40, y);
                }

                // back to menu
                text = "Menu";
                x = align(text);
                y += 55;
                g2.drawString(text, x, y);
                if (commandNumber == 1) {
                    g2.drawString(">", x - 40, y);
                }
            } else {
                int x;
                int y;
                String text;
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 54f));

                // make shadow
                text = "The game ended in a draw";
                g2.setColor(Color.white);
                x = align(text);
                y = gp.TILESIZE * 4;
                g2.drawString(text, x, y);

                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45f));
                text = "Player 1 score: " + gp.score1 * 100;
                g2.setColor(Color.white);
                x = align(text);
                y += gp.TILESIZE * 2;
                g2.drawString(text, x, y);

                text = "Player 2 score: " + gp.score2 * 100;
                g2.setColor(Color.white);
                x = align(text);
                y += gp.TILESIZE * 2;
                g2.drawString(text, x, y);

                g2.setFont(g2.getFont().deriveFont(45f));
                text = "Play again";
                x = align(text);
                y += gp.TILESIZE * 2;
                g2.drawString(text, x, y);
                if (commandNumber == 0) {
                    g2.drawString(">", x - 40, y);
                }

                // back to menu
                text = "Menu";
                x = align(text);
                y += 55;
                g2.drawString(text, x, y);
                if (commandNumber == 1) {
                    g2.drawString(">", x - 40, y);
                }
            }
        } else {
            int x;
            int y;
            String text;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80f));

            // make shadow
            text = "YOU WIN";
            g2.setColor(Color.black);
            x = align(text);
            y = gp.TILESIZE * 4;
            g2.drawString(text, x, y);

            // main
            g2.setColor(Color.pink);
            g2.drawString(text, x - 4, y - 4);

            // retry
            g2.setFont(g2.getFont().deriveFont(50f));
            text = "Play again";
            x = align(text);
            y += gp.TILESIZE * 4;
            g2.drawString(text, x, y);
            if (commandNumber == 0) {
                g2.drawString(">", x - 40, y);
            }

            // back to menu
            text = "Menu";
            x = align(text);
            y += 55;
            g2.drawString(text, x, y);
            if (commandNumber == 1) {
                g2.drawString(">", x - 40, y);
            }
        }
    }

    private void drawScore(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25f));
        String text = "Player 1 score: " + gp.score1 * 100;
        g2.setColor(Color.white);
        g2.drawString(text, gp.TILESIZE * 5, 32);

        text = "Player 2 score: " + gp.score2 * 100;
        g2.setColor(Color.white);
        g2.drawString(text, gp.TILESIZE * 12, 32);
    }

    // Get middle pos of the screen.
    private int align(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = GamePanel.WINDOW_WIDTH / 2 - length / 2;
        return x;
    }
}
