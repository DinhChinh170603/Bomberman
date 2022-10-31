package entity.enemy;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Chicken extends Enemy {
    public Chicken(int x, int y, GamePanel gp) {
        super(x, y, gp);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setDefaultValues() {
        // x = 1104;
        // y = 48;
        speed = 2;
        tick = 0;
        maxFrame = 4;
        begin = 0;
        interval = 7;
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
                speed = 4;
                interval = 4;
            } else {
                speed = 2;
                interval = 7;
            }
            collide = false;
            Random a = new Random();
            gp.cChecker.checkTile(this);
            if (!collide) {
                switch (direction) {
                    case "left":
                        x -= speed;
                        num--;
                        break;
                    case "right":
                        x += speed;
                        num--;
                        break;
                    case "up":
                        y -= speed;
                        num--;
                        break;
                    case "down":
                        y += speed;
                        num--;
                        break;
                }
            } else {

                if (direction.equals("left")) {
                    direction = leftR[a.nextInt(3)];
                } else if (direction.equals("down")) {
                    direction = downR[a.nextInt(3)];
                } else if (direction.equals("right")) {
                    direction = rightR[a.nextInt(3)];
                } else if (direction.equals("up")) {
                    direction = upR[a.nextInt(3)];
                }
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
    public void getImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/enemy/chicken.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/res/enemy/deadchicken.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        // vẽ nhân vật
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