package entity.enemy;

import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Kiki extends Enemy {
    private int movementBuffer = 0;

    public Kiki(int x, int y, GamePanel gp) {
        super(x, y, gp);
    }

    @Override
    public void getImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/enemy/sam2.png"));
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
            // xu li nhanh cham gan xa nhan vat
            if (Math.abs(x - gp.player.getX()) / gp.TILESIZE <= 6
                    && Math.abs(y - gp.player.getY()) / gp.TILESIZE <= 6) {
                speed = 3;
                interval = 6;
            } else {
                speed = 1;
                interval = 10;
            }

            if (movementBuffer == 0) {
                // tinh 4 huong co duong di toi nhan vat
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

                // so sanh va chon ra huong di ngan nhat
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
                gp.cChecker.checkTile(this); // kiem tra xem co va cham k
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
                    frame = image.getSubimage(16, 16 * tick, 16, 16);
                    break;
                case "down":
                    frame = image.getSubimage(0, 16 * tick, 16, 16);
                    break;
                case "left":
                    frame = image.getSubimage(32, 16 * tick, 16, 16);
                    break;
                case "right":
                    frame = image.getSubimage(48, 16 * tick, 16, 16);
                    break;
            }
        } else {
            frame = image.getSubimage(16 * tick, 64, 16, 16);
        }
        g2.drawImage(frame, x + 4, y + 4, gp.TILESIZE - 8, gp.TILESIZE - 8, null);
    }
}
