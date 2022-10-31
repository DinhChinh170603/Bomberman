package entity.bomb;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Bomb extends Entity {
    int length;
    long timeToExplode = 2000;
    long put, clock;
    GamePanel gp;
    public boolean exploded = false, added = false;
    public boolean desRight = false, desLeft = false, desUp = false, desDown = false;

    public Bomb(int x, int y, int length, GamePanel gp) {
        this.length = length;
        this.x = x;
        this.y = y;
        this.gp = gp;
        tick = 0;
        maxFrame = 3;
        // begin đại diện thời gian đã trôi qua, đến khi nó bằng interval tức là 1 frame
        // mới cần thay thế frame cũ
        begin = 0;
        interval = 8;
        put = System.currentTimeMillis();
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/bomb/bomb2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // chay animation
        clock = System.currentTimeMillis();
        // thời gian để boom nổ
        if (clock - put < timeToExplode) {
            begin++;
            if (begin > interval) {
                tick++;
                if (tick >= maxFrame) {
                    tick = 0;
                }
                begin = 0;
            }
        } else {
            exploded = true;

        }
    }

    public void draw(Graphics2D g2) {
        // vẽ bomb
        frame = image.getSubimage(16 * tick, 0, 16, 16);
        g2.drawImage(frame, x, y, gp.TILESIZE, gp.TILESIZE, null);

    }
}
