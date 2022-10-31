/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Lenovo
 */
public class Entity {
    protected int x, y;
    protected int speed;
    // public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2,
    public BufferedImage image, frame,image2,frame2;
    public String direction;
    public int tick;
    public int maxFrame;
    public int begin;
    public int interval;
    public Rectangle solidArea;
    public boolean collide = false;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    
}
