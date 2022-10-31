package entity.enemy;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import main.GamePanel;

public abstract class Enemy extends Entity {
    GamePanel gp;
    String[] leftR = {"down", "right", "up"};
    String[] downR = {"left", "right", "up"};
    String[] rightR = {"down", "left", "up"};
    String[] upR = {"down", "right", "left"};
    String[] all = {"up", "down", "right", "left"};
    List<String> tmp = new ArrayList<>();
    int num; // so buoc chan
    public boolean dead = false;
    public boolean finish = false;

    public Enemy(int x, int y, GamePanel gp) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        setDefaultValues();
        getImage();
    }

    public abstract void getImage();

    public abstract void setDefaultValues();

    public abstract void update();

    public abstract void draw(Graphics2D g2);
}