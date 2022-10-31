package entity.bomb;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.Player;
import entity.enemy.Enemy;
import main.GamePanel;

public class Flame extends Entity {
    GamePanel gp;
    Player player;

    List<Enemy> enemies;

    Bomb bomb;
    public boolean finish = false;
    private int totalSides = 0;

    private List<FlameSides> sides = new ArrayList<>();

    public boolean flagLeft = false, flagRight = false, flagUp = false, flagDown = false;

    public int x1, x2, x3, x4, y1, y2, y3, y4;

    private int id;

    public int getTotalSides() {
        return totalSides;
    }

    public void setTotalSides(int totalSides) {
        this.totalSides = totalSides;
    }

    public Flame(int x, int y, GamePanel gp, Bomb bomb, List<Enemy> enemies, int id, int bombLength) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        this.bomb = bomb;
        this.enemies = enemies;
        this.id = id;
        tick = 0;
        maxFrame = 4;
        begin = 0;
        interval = 5;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/bomb/fire.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        generateFlameSides(gp, bomb, bombLength);
    }

    public void update() {
        // chay animation
        begin++;
        if (begin > interval) {
            tick++;
            if (tick >= maxFrame) {
                finish = true;
            }
            begin = 0;
        }
        execution();
    }

    private void execution() {
        // xu li va cham voi tam lua
        if (!gp.player.flameResist) {
            if (gp.player.getX() + 20 <= this.getX() + gp.TILESIZE
                    && gp.player.getX() + gp.TILESIZE >= this.getX() + 20
                    && gp.player.getY() + 20 <= this.getY() + gp.TILESIZE
                    && gp.player.getY() + gp.TILESIZE >= this.getY() + 20) {
                gp.player.dead = true;
            }
        }
        if (gp.pNum == 2) {
            if (!gp.player2.flameResist) {
                if (gp.player2.getX() + 20 <= this.getX() + gp.TILESIZE
                        && gp.player2.getX() + gp.TILESIZE >= this.getX() + 20
                        && gp.player2.getY() + 20 <= this.getY() + gp.TILESIZE
                        && gp.player2.getY() + gp.TILESIZE >= this.getY() + 20) {
                    gp.player2.dead = true;
                }
            }
        }
        // xu li va cham voi canh lua
        for (int i = 0; i < sides.size(); i++) {
            sides.get(i).update();
            if (!gp.player.flameResist) {
                if (gp.player.getX() + 20 <= sides.get(i).getX() + gp.TILESIZE
                        && gp.player.getX() + gp.TILESIZE >= sides.get(i).getX() + 20
                        && gp.player.getY() + 20 <= sides.get(i).getY() + gp.TILESIZE
                        && gp.player.getY() + gp.TILESIZE >= sides.get(i).getY() + 20) {
                    gp.player.dead = true;
                }
            }
            if (gp.pNum == 2) {
                if (!gp.player2.flameResist) {
                    if (gp.player2.getX() + 20 <= sides.get(i).getX() + gp.TILESIZE
                            && gp.player2.getX() + gp.TILESIZE >= sides.get(i).getX() + 20
                            && gp.player2.getY() + 20 <= sides.get(i).getY() + gp.TILESIZE
                            && gp.player2.getY() + gp.TILESIZE >= sides.get(i).getY() + 20) {
                        gp.player2.dead = true;
                    }
                }
            }
            // xu li va cham cua lua voi entity
            for (int j = 0; j < enemies.size(); j++) {
                if (enemies.get(j).dead == false) {
                    if (enemies.get(j).getX() + 20 <= sides.get(i).getX() + gp.TILESIZE
                            && enemies.get(j).getX() + gp.TILESIZE >= sides.get(i).getX() + 20
                            && enemies.get(j).getY() + 20 <= sides.get(i).getY() + gp.TILESIZE
                            && enemies.get(j).getY() + gp.TILESIZE >= sides.get(i).getY() + 20) {
                        enemies.get(j).dead = true;
                        enemies.get(j).tick = 0;
                        enemies.get(j).begin = 0;
                        enemies.get(j).interval = 10;
                        if (gp.pNum == 2) {
                            if (id == 1) {
                                gp.score1++;
                            } else {
                                gp.score2++;
                            }
                        }
                    }
                }
            }
            // xu li no lan
            for (int j = 0; j < gp.player.bombs.size(); j++) {
                if (gp.player.bombs.get(j).getX() == sides.get(i).getX()
                        && gp.player.bombs.get(j).getY() == sides.get(i).getY()) {
                    gp.player.bombs.get(j).exploded = true;
                }
            }
            if (gp.pNum == 2) {
                for (int j = 0; j < gp.player2.bombs.size(); j++) {
                    if (gp.player2.bombs.get(j).getX() == sides.get(i).getX()
                            && gp.player2.bombs.get(j).getY() == sides.get(i).getY()) {
                        gp.player2.bombs.get(j).exploded = true;
                    }
                }
            }

        }
    }

    public void draw(Graphics2D g2) {
        frame = image.getSubimage(16 * tick, 0, 16, 16);
        g2.drawImage(frame, x, y, gp.TILESIZE, gp.TILESIZE, null);
        for (int i = 0; i < sides.size(); i++) {
            sides.get(i).draw(g2);
        }
    }

    // ve lua (dua tren handlesbomb trong player)
    private void generateFlameSides(GamePanel gp, Bomb bomb, int bombLength) {
        boolean desUp = false, desDown = false, desLeft = false, desRight = false;

        for (int i = 1; i <= bombLength; i++) {
            if (desLeft == false) {
                if (!GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX() + 12 - i * gp.TILESIZE)
                        / gp.TILESIZE][bomb.getY()
                                / gp.TILESIZE]].stiff) {
                    if (i < bombLength) {
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX() - i * gp.TILESIZE)
                                / gp.TILESIZE][(bomb.getY())
                                        / gp.TILESIZE]].breakable) {
                            sides.add(
                                    new FlameSides(bomb.getX() - i * gp.TILESIZE, bomb.getY(), gp,
                                            "horizontal_left"));
                        } else {
                            sides.add(
                                    new FlameSides(bomb.getX() - i * gp.TILESIZE, bomb.getY(), gp,
                                            "horizontal"));
                        }
                    } else {
                        sides.add(
                                new FlameSides(bomb.getX() - i * gp.TILESIZE, bomb.getY(), gp,
                                        "horizontal_left"));
                    }
                    if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX() - i * gp.TILESIZE)
                            / gp.TILESIZE][bomb.getY()
                                    / gp.TILESIZE]].breakable) {
                        desLeft = true;
                    }
                } else {
                    desLeft = true;
                }
            }
            if (desRight == false) {
                if (!GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX() + i * gp.TILESIZE)
                        / gp.TILESIZE][bomb.getY()
                                / gp.TILESIZE]].stiff) {
                    if (i < bombLength) {
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX() + i * gp.TILESIZE)
                                / gp.TILESIZE][(bomb.getY())
                                        / gp.TILESIZE]].breakable) {
                            sides.add(
                                    new FlameSides(bomb.getX() + i * gp.TILESIZE, bomb.getY(), gp,
                                            "horizontal_right"));
                        } else {
                            sides.add(
                                    new FlameSides(bomb.getX() + i * gp.TILESIZE, bomb.getY(), gp,
                                            "horizontal"));
                        }
                    } else {
                        sides.add(
                                new FlameSides(bomb.getX() + i * gp.TILESIZE, bomb.getY(), gp,
                                        "horizontal_right"));
                    }
                    if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX() + i * gp.TILESIZE)
                            / gp.TILESIZE][bomb.getY()
                                    / gp.TILESIZE]].breakable) {
                        desRight = true;
                    }
                } else {
                    desRight = true;
                }
            }
            if (desDown == false) {
                if (!GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX())
                        / gp.TILESIZE][(bomb.getY() + i * gp.TILESIZE)
                                / gp.TILESIZE]].stiff) {
                    if (i < bombLength) {
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX())
                                / gp.TILESIZE][(bomb.getY() + i * gp.TILESIZE)
                                        / gp.TILESIZE]].breakable) {
                            sides.add(
                                    new FlameSides(bomb.getX(), bomb.getY() + i * gp.TILESIZE, gp,
                                            "vertical_down"));
                        } else {
                            sides.add(
                                    new FlameSides(bomb.getX(), bomb.getY() + i * gp.TILESIZE, gp,
                                            "vertical"));
                        }

                    } else {
                        sides.add(
                                new FlameSides(bomb.getX(), bomb.getY() + i * gp.TILESIZE, gp,
                                        "vertical_down"));
                    }
                    if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX())
                            / gp.TILESIZE][(bomb.getY() + i * gp.TILESIZE)
                                    / gp.TILESIZE]].breakable)
                        desDown = true;
                } else {
                    desDown = true;
                }
            }
            if (desUp == false) {
                if (!GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX())
                        / gp.TILESIZE][(bomb.getY() - i * gp.TILESIZE)
                                / gp.TILESIZE]].stiff) {
                    if (i < bombLength) {
                        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX())
                                / gp.TILESIZE][(bomb.getY() - i * gp.TILESIZE)
                                        / gp.TILESIZE]].breakable) {
                            sides.add(
                                    new FlameSides(bomb.getX(), bomb.getY() - i * gp.TILESIZE, gp,
                                            "vertical_up"));
                        } else {
                            sides.add(
                                    new FlameSides(bomb.getX(), bomb.getY() - i * gp.TILESIZE, gp,
                                            "vertical"));
                        }
                    } else {
                        sides.add(
                                new FlameSides(bomb.getX(), bomb.getY() - i * gp.TILESIZE, gp,
                                        "vertical_up"));
                    }
                    if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[(bomb.getX())
                            / gp.TILESIZE][(bomb.getY() - i * gp.TILESIZE)
                                    / gp.TILESIZE]].breakable)
                        desUp = true;
                } else {
                    desUp = true;
                }
            }
        }
    }
}
