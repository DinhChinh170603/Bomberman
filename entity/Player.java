/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entity.bomb.Bomb;
import entity.bomb.Flame;
import main.GamePanel;
import main.KeyHandler;

/**
 * @author Lenovo
 */
public class Player extends Entity {

    public int id;
    private GamePanel gp;
    private KeyHandler input;
    public List<Bomb> bombs = new ArrayList<>();
    public List<Flame> flames = new ArrayList<>();
    private int bombLength, maxBomb; // do dai - so luong
    private int movementBuffer = 0; // bo dem luu khoang cach nvat can di chuyen

    public boolean flameResist = false; // khang lua

    private boolean justTp = false; // teleport
    public boolean inBomb = false; // k bi ket player khi dat bom

    public boolean dead = false;
    public boolean finish = false;

    public Player(GamePanel gp, KeyHandler input, int id) {
        this.gp = gp;
        this.input = input;
        this.id = id;
        setDefaultValues();
        getPlayerImage();
    }

    // đọc file ảnh của nhân vật
    public void getPlayerImage() {
        try {
            if (!dead) {
                if (id == 1) {
                    image = ImageIO.read(getClass().getResourceAsStream("/res/player/player.png"));
                    image2 = ImageIO.read(getClass().getResourceAsStream("/res/player/deadplayer.png"));
                } else {
                    image = ImageIO.read(getClass().getResourceAsStream("/res/player/player2.png"));
                    image2 = ImageIO.read(getClass().getResourceAsStream("/res/player/dead.png"));
                }
            } else {
                gp.playSE(6);
                interval = 25;
                tick = 0;
                begin = 0;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // set các thông số mặc định
    public void setDefaultValues() {
        if (id == 1) {
            x = 48;
            y = 48;
            speed = 2;
            direction = "down";
            tick = 0;
            maxFrame = 4;
            begin = 0;
            interval = 10;
            bombLength = 1;
            maxBomb = 1;
            flameResist = false;
        }
        if (id == 2) {
            x = 48;
            y = 624;
            speed = 2;
            direction = "down";
            tick = 0;
            maxFrame = 4;
            begin = 0;
            interval = 10;
            bombLength = 1;
            maxBomb = 1;
            flameResist = false;
        }
    }

    public void update() {
        // check xem di ra khoi qua bom hay chua
        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                / gp.TILESIZE] != 55) {
            inBomb = false;
        }
        handleMovementsAndInputs(); // xu li di chuyen va input
        handleBombs(); // xu li cac van de cua bom
        // check xem di ra khoi cong hay chua
        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                / gp.TILESIZE] != 38) {
            justTp = false;
        }
        powerUps(); // xu li power
        dead = dead | deadYet();
        if (dead) {
            interval = 25;
        }
    }

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
            frame = image2.getSubimage(16 * tick, 0, 16, 16);
        }
        // vẽ bomb
        for (int i = 0; i < bombs.size(); i++) {
            if (!bombs.get(i).exploded)
                bombs.get(i).draw(g2);
        }

        for (int i = 0; i < flames.size(); i++) {
            flames.get(i).draw(g2);
        }
        // vẽ nhân vật
        g2.drawImage(frame, x + 4, y + 4, gp.TILESIZE - 8, gp.TILESIZE - 8, null);
    }

    private void handleMovementsAndInputs() {
        // kiểm tra xem có ấn 1 trong 4 không
        if (!dead) {
            if (id == 1) {
                if (input.up == true || input.down == true
                        || input.left == true || input.right == true || input.bomb == true) {
                    // nếu input = up thì trạng thái hoạt động là up
                    if (input.up == true) {
                        if (movementBuffer == 0) {
                            if (y % gp.TILESIZE == 0) {
                                movementBuffer += gp.TILESIZE;
                            } else {
                                movementBuffer += y % gp.TILESIZE;
                            }
                            direction = "up";
                        } else if (direction.equals("down")) {
                            movementBuffer = gp.TILESIZE - movementBuffer;
                            direction = "up";
                        }
                    }
                    if (input.down == true) {
                        if (movementBuffer == 0) {
                            if (y % gp.TILESIZE == 0) {
                                movementBuffer += gp.TILESIZE;
                            } else {
                                movementBuffer += gp.TILESIZE - y % gp.TILESIZE;
                            }
                            direction = "down";
                        } else if (direction.equals("up")) {
                            movementBuffer = gp.TILESIZE - movementBuffer;
                            direction = "down";
                        }
                    }
                    if (input.left == true) {
                        if (movementBuffer == 0) {
                            if (x % gp.TILESIZE == 0) {
                                movementBuffer += gp.TILESIZE;
                            } else {
                                movementBuffer += x % gp.TILESIZE;
                            }
                            direction = "left";
                        } else if (direction.equals("right")) {
                            movementBuffer = gp.TILESIZE - movementBuffer;
                            direction = "left";
                        }
                    }
                    if (input.right == true) {
                        if (movementBuffer == 0) {
                            if (x % gp.TILESIZE == 0) {
                                movementBuffer += gp.TILESIZE;
                            } else {
                                movementBuffer += gp.TILESIZE - x % gp.TILESIZE;
                            }
                            direction = "right";
                        } else if (direction.equals("left")) {
                            movementBuffer = gp.TILESIZE - movementBuffer;
                            direction = "right";
                        }
                    }
                    // nếu input là bomb thì set xem độ dài mảng bomb có hơn độ dài số lượng bomb
                    // max không nếu không add
                    // thêm 1 quả bomb vào list bomb
                    if (input.bomb == true) {
                        if (bombs.size() < maxBomb) {
                            // xet vi tri dat bom o tam nhan vat
                            if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2)
                                    / gp.TILESIZE][(y + gp.TILESIZE / 2)
                                            / gp.TILESIZE] == 0
                                    || GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2)
                                            / gp.TILESIZE][(y + gp.TILESIZE / 2)
                                                    / gp.TILESIZE] == 51) {
                                bombs.add(new Bomb((x + gp.TILESIZE / 2) / gp.TILESIZE * gp.TILESIZE,
                                        (y + gp.TILESIZE / 2) / gp.TILESIZE * gp.TILESIZE,
                                        bombLength, gp));
                                // doi vi tri dat bom thanh vat can
                                GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2)
                                        / gp.TILESIZE][(y + gp.TILESIZE / 2)
                                                / gp.TILESIZE] = 55;
                                inBomb = true;
                                input.bomb = false;
                                gp.playSE(1);
                            }
                        }
                    }
                }
                // va chạm ban đầu = false
                // check va chạm vs bản đồ
            } else {
                if (input.up2 == true || input.down2 == true
                        || input.left2 == true || input.right2 == true || input.bomb2 == true) {
                    // nếu input = up thì trạng thái hoạt động là up
                    if (input.up2 == true) {
                        if (movementBuffer == 0) {
                            if (y % gp.TILESIZE == 0) {
                                movementBuffer += gp.TILESIZE;
                            } else {
                                movementBuffer += y % gp.TILESIZE;
                            }
                            direction = "up";
                        } else if (direction.equals("down")) {
                            movementBuffer = gp.TILESIZE - movementBuffer;
                            direction = "up";
                        }
                    }
                    if (input.down2 == true) {
                        if (movementBuffer == 0) {
                            if (y % gp.TILESIZE == 0) {
                                movementBuffer += gp.TILESIZE;
                            } else {
                                movementBuffer += gp.TILESIZE - y % gp.TILESIZE;
                            }
                            direction = "down";
                        } else if (direction.equals("up")) {
                            movementBuffer = gp.TILESIZE - movementBuffer;
                            direction = "down";
                        }
                    }
                    if (input.left2 == true) {
                        if (movementBuffer == 0) {
                            if (x % gp.TILESIZE == 0) {
                                movementBuffer += gp.TILESIZE;
                            } else {
                                movementBuffer += x % gp.TILESIZE;
                            }
                            direction = "left";
                        } else if (direction.equals("right")) {
                            movementBuffer = gp.TILESIZE - movementBuffer;
                            direction = "left";
                        }
                    }
                    if (input.right2 == true) {
                        if (movementBuffer == 0) {
                            if (x % gp.TILESIZE == 0) {
                                movementBuffer += gp.TILESIZE;
                            } else {
                                movementBuffer += gp.TILESIZE - x % gp.TILESIZE;
                            }
                            direction = "right";
                        } else if (direction.equals("left")) {
                            movementBuffer = gp.TILESIZE - movementBuffer;
                            direction = "right";
                        }
                    }
                    if (input.bomb2 == true) {
                        if (bombs.size() < maxBomb) {
                            if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2)
                                    / gp.TILESIZE][(y + gp.TILESIZE / 2)
                                            / gp.TILESIZE] == 0
                                    || GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2)
                                            / gp.TILESIZE][(y + gp.TILESIZE / 2)
                                                    / gp.TILESIZE] == 51) {
                                bombs.add(new Bomb((x + gp.TILESIZE / 2) / gp.TILESIZE * gp.TILESIZE,
                                        (y + gp.TILESIZE / 2) / gp.TILESIZE * gp.TILESIZE,
                                        bombLength, gp));
                                GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2)
                                        / gp.TILESIZE][(y + gp.TILESIZE / 2)
                                                / gp.TILESIZE] = 55;
                                inBomb = true;
                                input.bomb2 = false;
                                gp.playSE(1);
                            }
                        }
                    }
                }
            }
            collide = false;
            gp.cChecker.checkTile(this);
            // neu dang k va cham
            if (collide == false) {
                if (movementBuffer > 0) {
                    switch (direction) {
                        case "up":
                            y -= Math.min(speed, movementBuffer);        // de movement khong bi di thua
                            movementBuffer -= Math.min(speed, movementBuffer);
                            break;
                        case "down":
                            y += Math.min(speed, movementBuffer);
                            movementBuffer -= Math.min(speed, movementBuffer);
                            break;
                        case "left":
                            x -= Math.min(speed, movementBuffer);
                            movementBuffer -= Math.min(speed, movementBuffer);
                            break;
                        case "right":
                            x += Math.min(speed, movementBuffer);
                            movementBuffer -= Math.min(speed, movementBuffer);
                            break;
                    }
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
                } else {
                    tick = 0;
                }
            } else {
                movementBuffer = 0;
                tick = 0;
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

    // xu li bom
    private void handleBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            if (!bombs.get(i).exploded) {
                bombs.get(i).update();
            } else {
                GamePanel.tileManager.mapTileNum[bombs.get(i).x / gp.TILESIZE][bombs.get(i).y / gp.TILESIZE] = 0; // huy bo vat can vo hinh
            }
            if (i >= flames.size()) { // flame tuong ung cua bom
                // nếu nổ
                if (bombs.get(i).exploded == true && bombs.get(i).added == false) { // xem khi bom no da add lua hay chua
                    gp.playSE(2);
                    flames.add(
                            new Flame(bombs.get(i).x, bombs.get(i).y, gp, bombs.get(i), gp.enemies, this.id,
                                    bombLength));
                    for (int j = 1; j <= bombLength; j++) {
                        // kiem tra cac huong co no duoc k
                        if (bombs.get(i).desLeft == false) {
                            // neu huong day khong no duoc
                            if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x
                                    - j * gp.TILESIZE)
                                    / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE]].stiff) {
                                bombs.get(i).desLeft = true;
                            }
                            // neu huong day da no roi
                            if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x
                                    - j * gp.TILESIZE)
                                    / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE]].breakable) {
                                GamePanel.tileManager.mapTileNum[(bombs.get(i).x - j * gp.TILESIZE)
                                        / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE] = gp.mapItem.get(0); // khi pha gach thi random power
                                gp.mapItem.remove(0);
                                bombs.get(i).desLeft = true;
                            }
                        }
                        if (bombs.get(i).desRight == false) {
                            if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x
                                    + j * gp.TILESIZE)
                                    / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE]].stiff) {
                                bombs.get(i).desRight = true;
                            }
                            if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x
                                    + j * gp.TILESIZE)
                                    / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE]].breakable) {
                                GamePanel.tileManager.mapTileNum[(bombs.get(i).x + j * gp.TILESIZE)
                                        / gp.TILESIZE][(bombs.get(i).y) / gp.TILESIZE] = gp.mapItem.get(0);
                                gp.mapItem.remove(0);
                                bombs.get(i).desRight = true;
                            }

                        }
                        if (bombs.get(i).desUp == false) {
                            if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x)
                                    / gp.TILESIZE][(bombs.get(i).y - j * gp.TILESIZE) / gp.TILESIZE]].stiff) {
                                bombs.get(i).desUp = true;
                            }
                            if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x)
                                    / gp.TILESIZE][(bombs.get(i).y - j * gp.TILESIZE) / gp.TILESIZE]].breakable) {
                                GamePanel.tileManager.mapTileNum[(bombs.get(i).x)
                                        / gp.TILESIZE][(bombs.get(i).y - j * gp.TILESIZE) / gp.TILESIZE] = gp.mapItem
                                                .get(0);
                                gp.mapItem.remove(0);
                                bombs.get(i).desUp = true;
                            }

                        }

                        if (bombs.get(i).desDown == false) {
                            if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x)
                                    / gp.TILESIZE][(bombs.get(i).y + j * gp.TILESIZE) / gp.TILESIZE]].stiff) {
                                bombs.get(i).desDown = true;
                            }
                            if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bombs.get(i).x)
                                    / gp.TILESIZE][(bombs.get(i).y + j * gp.TILESIZE) / gp.TILESIZE]].breakable) {
                                GamePanel.tileManager.mapTileNum[(bombs.get(i).x)
                                        / gp.TILESIZE][(bombs.get(i).y + j * gp.TILESIZE) / gp.TILESIZE] = gp.mapItem
                                                .get(0);
                                gp.mapItem.remove(0);
                                bombs.get(i).desDown = true;
                            }

                        }
                    }
                    bombs.get(i).added = true;
                }
            }
        }
        for (int i = 0; i < flames.size(); i++) {
            flames.get(i).update();
            if (flames.get(i).finish) {
                bombs.remove(i);
                flames.remove(i);
                i--;
            }
        }
    }

    private void powerUps() {
        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                / gp.TILESIZE] == 33) {
            gp.playSE(4);
            maxBomb++;
            GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                    / gp.TILESIZE] = 0;
        }
        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                / gp.TILESIZE] == 34) {
            gp.playSE(4);
            this.speed++;
            this.interval--;
            GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                    / gp.TILESIZE] = 0;
        }
        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                / gp.TILESIZE] == 35) {
            gp.playSE(4);
            bombLength++;
            GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                    / gp.TILESIZE] = 0;
        }
        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                / gp.TILESIZE] == 36) {
            // flamepass
            flameResist = true;
            gp.playSE(4);
            // maxBomb += 1;
            GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                    / gp.TILESIZE] = 0;
        }

        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                / gp.TILESIZE] == 37) {
            // portal
            if (gp.enemies.isEmpty()) {
                if (gp.pNum == 1) {
                    if (gp.level < gp.maxLevel) {
                        gp.level++;
                        gp.setupGame();
                        gp.state = gp.playState;
                        gp.playSE(4);
                        gp.stopMusic();
                        if (gp.level == 2) {
                            gp.playMusic(0);
                        }
                        if (gp.level == 3) {
                            gp.playMusic(10);
                        }
                    } else {
                        gp.stopMusic();
                        gp.state = gp.gameWinState;
                        gp.playSE(8);
                    }
                } else {
                    if (id == 1) {
                        gp.score1++;
                    } else {
                        gp.score2++;
                    }
                    gp.state = gp.gameWinState;
                    gp.stopMusic();
                    gp.playSE(8);
                }
            }

        }
        // xu li cong dich chuyen
        if (GamePanel.tileManager.mapTileNum[(x + gp.TILESIZE / 2) / gp.TILESIZE][(y + gp.TILESIZE / 2)
                / gp.TILESIZE] == 38) {
            if (!justTp) {
                gp.playSE(9);
                switch ((x + gp.TILESIZE / 2) / gp.TILESIZE) {
                    case 1:
                        x = 23 * gp.TILESIZE;
                        y = 7 * gp.TILESIZE;
                        break;
                    case 21:
                        x = 17 * gp.TILESIZE;
                        y = 12 * gp.TILESIZE;
                        break;
                    case 17:
                        x = 21 * gp.TILESIZE;
                        y = gp.TILESIZE;
                        break;
                    case 23:
                        x = 1 * gp.TILESIZE;
                        y = 11 * gp.TILESIZE;
                        break;
                }
                justTp = true;
                movementBuffer = 0;
            }
        }
    }

    // xu li va cham voi enemy
    private boolean deadYet() {
        for (int i = 0; i < gp.enemies.size(); i++) {
            if (gp.enemies.get(i).dead == false) {
                if (x + 18 <= gp.enemies.get(i).x + gp.TILESIZE && x + gp.TILESIZE >= gp.enemies.get(i).x + 18
                        && y + 18 <= gp.enemies.get(i).y + gp.TILESIZE && y + gp.TILESIZE >= gp.enemies.get(i).y + 18) {
                    return true;
                }
            }
        }
        return false;
    }

}
