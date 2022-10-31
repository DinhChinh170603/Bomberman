package entity.bomb;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class FlameSides extends Entity {
    GamePanel gp;
    private String axis;

    public String getAxis() {
        return axis;
    }

    public void setAxis(String axis) {
        this.axis = axis;
    }

    public FlameSides(int x, int y, GamePanel gp, String axis) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        this.axis = axis;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/bomb/fire.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tick = 0;
        maxFrame = 4;
        begin = 0;
        interval = 5;
    }

    public void update() {
        begin++;
        if (begin > interval) {
            if (tick < maxFrame - 1) {
                tick++;
            }
            begin = 0;
        }
    }

    public void draw(Graphics2D g2) {
        switch (axis) {
            case "horizontal_left":
                frame = image.getSubimage(16 * tick, 64, 16, 16); 
                break;
            case "horizontal_right":
                frame = image.getSubimage(16 * tick, 48, 16, 16); 
                break;
            case "horizontal":
                frame = image.getSubimage(16 * tick, 80, 16, 16);
                break;
            case "vertical":
                frame = image.getSubimage(16 * tick, 96, 16, 16);
                break;
            case "vertical_up":
                frame = image.getSubimage(16 * tick, 16, 16, 16);
                break;
            case "vertical_down":
                frame = image.getSubimage(16 * tick, 32, 16, 16);
                break;
        }
        g2.drawImage(frame, x, y, gp.TILESIZE, gp.TILESIZE, null);
    }

}
