package entity.enemy;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Random;

public class Slime3 extends Enemy {
    public Slime3(int x, int y, GamePanel gp) {
        super(x, y, gp);
    }

    @Override
    public void getImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Slime3.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/res/enemy/deadslime3.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setDefaultValues() {
        // x = 1104;
        // y = 48;
        speed = 2;
        tick = 0;
        maxFrame = 3;
        begin = 0;
        interval = 6;
        direction = "left";
        num = 48;
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
            collide = false;
            Random a = new Random();
            gp.cChecker.checkTile(this);
            if (!collide && num > 0) {
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
            }
            if (collide) {

                if (direction.equals("left")) {
                    direction = leftR[a.nextInt(3)];
                } else if (direction.equals("down")) {
                    direction = downR[a.nextInt(3)];
                } else if (direction.equals("right")) {
                    direction = rightR[a.nextInt(3)];
                } else if (direction.equals("up")) {
                    direction = upR[a.nextInt(3)];
                }
            } else if (num == 0) {
                direction = all[a.nextInt(4)];
                num = 48;
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
                    frame = image.getSubimage(90 * tick, 100 * 3, 90, 100);
                    break;
                case "down":
                    frame = image.getSubimage(90 * tick, 0, 90, 100);
                    break;
                case "left":
                    frame = image.getSubimage(90 * tick, 100, 90, 100);
                    break;
                case "right":
                    frame = image.getSubimage(90 * tick, 100 * 2, 90, 100);
                    break;
            }

        } else {
            frame = image2.getSubimage(90 * tick, 0, 90, 100);
        }
        g2.drawImage(frame, x + 4, y + 4, gp.TILESIZE - 8, gp.TILESIZE - 8, null);
    }
}
