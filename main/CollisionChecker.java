/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Entity;
import entity.Player;

/**
 *
 * @author Lenovo
 */
public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // bên trái nhất của entity
        int leftmost = entity.getX() + 1;
        // bên phải nhất của entity
        int rightmost = entity.getX() + gp.TILESIZE - 1;
        int top = entity.getY() + 1;
        int bottom = entity.getY() + gp.TILESIZE - 1;

        // xác định vị trí bên trái nhất ở cột nào
        int leftCol = leftmost / gp.TILESIZE;
        // xác định vị trí bên phải nhất ở cột nà0
        int rightCol = rightmost / gp.TILESIZE;
        // xác định trên đầu thuộc dòng nào
        int topRow = top / gp.TILESIZE;
        // xác định dưới thân thuộc dòng nào
        int botRow = bottom / gp.TILESIZE;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                // vì trục tọa độ hướng xuống nên muốn đi lên thì trên đầu (Oy - 2) sau đó / size ô
                // xác định topRow đang ở dòng nào
                topRow = (top - 2) / gp.TILESIZE;
                // lấy giá trị của mapTile tại tọa độ [leftCol][topRow]
                // check 2 o truoc mat khi nhan vat dang o giua 2 o
                tileNum1 = GamePanel.tileManager.mapTileNum[leftCol][topRow];
                tileNum2 = GamePanel.tileManager.mapTileNum[rightCol][topRow];
                // nếu item này có xảy ra va chạm k đc đi tiếp
                if (GamePanel.tileManager.tiles[tileNum1].collision == true
                        || GamePanel.tileManager.tiles[tileNum2].collision == true) {
                    // va chạm nhân vật = true
                    if (entity instanceof Player) {
                        Player tmp = (Player) entity;
                        if (tileNum1 == 55 && tmp.inBomb) {

                        } else
                            entity.collide = true;
                    } else {
                        entity.collide = true;
                    }
                }
                break;
            case "down":
                botRow = (bottom + 2) / gp.TILESIZE;
                tileNum1 = GamePanel.tileManager.mapTileNum[leftCol][botRow];
                tileNum2 = GamePanel.tileManager.mapTileNum[rightCol][botRow];
                if (GamePanel.tileManager.tiles[tileNum1].collision == true
                        || GamePanel.tileManager.tiles[tileNum2].collision == true) {
                    if (entity instanceof Player) {
                        Player tmp = (Player) entity;
                        if (tileNum1 == 55 && tmp.inBomb) {

                        } else
                            entity.collide = true;
                    } else {
                        entity.collide = true;
                    }
                }
                break;
            case "left":
                leftCol = (leftmost - 2) / gp.TILESIZE;
                tileNum1 = GamePanel.tileManager.mapTileNum[leftCol][topRow];
                tileNum2 = GamePanel.tileManager.mapTileNum[leftCol][botRow];
                if (GamePanel.tileManager.tiles[tileNum1].collision == true
                        || GamePanel.tileManager.tiles[tileNum2].collision == true) {
                    if (entity instanceof Player) {
                        Player tmp = (Player) entity;
                        if (tileNum1 == 55 && tmp.inBomb) {

                        } else
                            entity.collide = true;
                    } else {
                        entity.collide = true;
                    }
                }
                break;
            case "right":
                rightCol = (rightmost + 2) / gp.TILESIZE;
                tileNum1 = GamePanel.tileManager.mapTileNum[rightCol][topRow];
                tileNum2 = GamePanel.tileManager.mapTileNum[rightCol][botRow];
                if (GamePanel.tileManager.tiles[tileNum1].collision == true
                        || GamePanel.tileManager.tiles[tileNum2].collision == true) {
                    if (entity instanceof Player) {
                        Player tmp = (Player) entity;
                        if (tileNum1 == 55 && tmp.inBomb) {

                        } else
                            entity.collide = true;
                    } else {
                        entity.collide = true;
                    }
                }
                break;
        }

    }
}
