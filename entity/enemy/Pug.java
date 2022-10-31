package entity.enemy;

import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Pug extends Enemy {
    private int movementBuffer = 0;

    public Pug(int x, int y, GamePanel gp) {
        super(x, y, gp);
    }

    @Override
    public void getImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/enemy/pug.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/res/enemy/deadpug.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDefaultValues() {
        speed = 2;
        tick = 0;
        maxFrame = 4;
        begin = 0;
        interval = 10;
        direction = "left";
    }

    @Override
    public void update() {
        if (!dead) {
            begin++;
            // begin > interval khoảng tg load mỗi frame
            if (begin > interval) {
                tick++;
                // nếu frame > frame max cho về ban đầu
                if (tick >= maxFrame) {
                    tick = 0;
                }
                begin = 0;
            }
            if (Math.abs(x - gp.player.getX()) / gp.TILESIZE <= 4
                    && Math.abs(y - gp.player.getY()) / gp.TILESIZE <= 4) {
                speed = 3;
                interval = 6;
            } else {
                speed = 1;
                interval = 10;
            }
            if (movementBuffer == 0) {
                int upCell = (int) 1e9, rightCell = (int) 1e9, downCell = (int) 1e9, leftCell = (int) 1e9;
                if (x / gp.TILESIZE - 1 >= 0) {
                    leftCell = BFS.find(x / gp.TILESIZE - 1,
                            y / gp.TILESIZE,
                            gp.player.getX() / gp.TILESIZE,
                            gp.player.getY() / gp.TILESIZE);
                }
                if (x / gp.TILESIZE + 1 < GamePanel.maxCols) {
                    rightCell = BFS.find(x / gp.TILESIZE + 1,
                            y / gp.TILESIZE,
                            gp.player.getX() / gp.TILESIZE,
                            gp.player.getY() / gp.TILESIZE);
                }
                if (y / gp.TILESIZE - 1 >= 0) {
                    upCell = BFS.find(x / gp.TILESIZE,
                            y / gp.TILESIZE - 1,
                            gp.player.getX() / gp.TILESIZE,
                            gp.player.getY() / gp.TILESIZE);
                }
                if (y / gp.TILESIZE + 1 < GamePanel.maxRows) {
                    downCell = BFS.find(x / gp.TILESIZE,
                            y / gp.TILESIZE + 1,
                            gp.player.getX() / gp.TILESIZE,
                            gp.player.getY() / gp.TILESIZE);
                }
                int min = (int) 1e9;

                if (leftCell < min) {
                    direction = "left";
                    min = leftCell;
                }
                if (rightCell < min) {
                    direction = "right";
                    min = rightCell;
                }
                if (upCell < min) {
                    direction = "up";
                    min = upCell;
                }
                if (downCell < min) {
                    direction = "down";
                    min = downCell;
                }
                collide = false;
                gp.cChecker.checkTile(this);
                if (!collide) {
                    movementBuffer += gp.TILESIZE;
                }
            }

            switch (direction) {
                case "left":
                    x -= Math.min(movementBuffer, speed);
                    movementBuffer -= Math.min(movementBuffer, speed);
                    break;
                case "right":
                    x += Math.min(movementBuffer, speed);
                    movementBuffer -= Math.min(movementBuffer, speed);
                    break;
                case "up":
                    y -= Math.min(movementBuffer, speed);
                    movementBuffer -= Math.min(movementBuffer, speed);
                    break;
                case "down":
                    y += Math.min(movementBuffer, speed);
                    movementBuffer -= Math.min(movementBuffer, speed);
                    break;
            }
        } else {
            begin++;
            if (begin > interval) {
                tick++;
                if (tick >= maxFrame) {
                    finish = true;
                }
                begin = 0;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!dead) {
            switch (direction) {
                case "up":
                    // getSubimage để cắt 1 hình ảnh lớn thành các frame nhỏ
                    frame = image.getSubimage(32 * tick, 0, 32, 32);
                    break;
                case "down":
                    frame = image.getSubimage(32 * tick, 64, 32, 32);
                    break;
                case "left":
                    frame = image.getSubimage(32 * tick, 96, 32, 32);
                    break;
                case "right":
                    frame = image.getSubimage(32 * tick, 32, 32, 32);
                    break;
            }

        } else {
            frame = image2.getSubimage(32 * tick, 0, 32, 32);
        }
        g2.drawImage(frame, x + 4, y + 4, gp.TILESIZE - 8, gp.TILESIZE - 8, null);
    }
}
