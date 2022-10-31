package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles; // mảng chứa các thông số txt tương ứng của các item
    public int mapTileNum[][]; // mảng 2 chiều để chứa vị trí và giá trị thông số txt tương ứng của các item

    public TileManager(GamePanel gp, int level) {
        this.gp = gp;
        tiles = new Tile[57];
        mapTileNum = new int[GamePanel.maxCols][GamePanel.maxRows];

        getTileImage();
        loadMap("/res/map/map" + level + ".txt");
        // loadMap("/res/map/map2.txt", 1);
    }

    public void getTileImage() {
        try {
            // mỗi item tương ứng vs 1 giá trị đã đc mặc định
            tiles[0] = new Tile();
            tiles[0].image = null;
            if (gp.level == 3) {
                tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/datmap32.png"));
            }
            tiles[0].collision = false; // va chạm = false

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/brick.png"));
            tiles[1].collision = true; // va chạm = true
            tiles[1].breakable = true;
            
            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/002.png"));
            tiles[2].collision = true;
            tiles[2].breakable = true;
            
            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/003.png"));
            tiles[3].collision = true;
            tiles[3].stiff = true;


            tiles[33] = new Tile();
            tiles[33].image = ImageIO.read(getClass().getResourceAsStream("/res/powerups/powerup_bombs.png"));
            // tiles[3].collision = true;
            // tiles[3].breakable = false;

            tiles[34] = new Tile();
            tiles[34].image = ImageIO.read(getClass().getResourceAsStream("/res/powerups/powerup_speed.png"));
            // tiles[4].collision = true;
            // tiles[4].breakable = false;

            tiles[35] = new Tile();
            tiles[35].image = ImageIO.read(getClass().getResourceAsStream("/res/powerups/powerup_flames.png"));
            // tiles[5].collision = true;
            // tiles[5].breakable = false;

            tiles[36] = new Tile();
            tiles[36].image = ImageIO.read(getClass().getResourceAsStream("/res/powerups/powerup_flamepass.png"));
            // tiles[6].collision = true;
            // tiles[6].breakable = false;

            tiles[37] = new Tile();
            tiles[37].image = ImageIO.read(getClass().getResourceAsStream("/res/powerups/portal.png"));
            // tiles[7].collision = true;
            // tiles[7].breakable = false;

            for (int i = 10; i <= 15; i++) {
                tiles[i] = new Tile();
                tiles[i].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/0" + i + ".png"));
            }

            tiles[16] = new Tile();
            tiles[16].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/016.png"));
            tiles[16].collision = true;
            tiles[16].stiff = true;

            for (int i = 17; i <= 32; i++) {
                tiles[i] = new Tile();
                tiles[i].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/0" + i + ".png"));
                tiles[i].collision = true;
                tiles[i].stiff = true;
            }
            tiles[32].stiff = false;
            tiles[32].breakable = true;

            for (int i = 38; i <= 40; i++) {
                tiles[i] = new Tile();
                tiles[i].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/0" + i + ".png"));
                tiles[i].collision = true;
            }
            tiles[38].collision = false;
            tiles[39].stiff = true;
            
            for (int i = 41; i <= 48; i++) {
                tiles[i] = new Tile();
                tiles[i].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/0" + i + ".png"));
                tiles[i].collision = true;
                tiles[i].stiff = true;
            }
            tiles[43].stiff = false;
            tiles[43].breakable = true;

            tiles[48].stiff = false;
            tiles[48].breakable = true;

            // map 3

            tiles[51] = new Tile();
            tiles[51].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/datmap32.png"));
            tiles[51].collision = false;

            tiles[52] = new Tile();
            tiles[52].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/brickmap3.png"));
            tiles[52].collision = true; // va chạm = true
            tiles[52].breakable = true;

            tiles[53] = new Tile();
            tiles[53].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/brickmap32.png"));
            tiles[53].collision = true; // va chạm = true
            tiles[53].breakable = true;

            tiles[54] = new Tile();
            tiles[54].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wallmap3.png"));
            tiles[54].collision = true; // va chạm = true
            tiles[54].stiff = true;
            
            tiles[55] = new Tile();
            tiles[55].image = null;
            if (gp.level == 3) {
                tiles[55].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/datmap32.png"));
            }
            tiles[55].collision = true;

            tiles[56] = new Tile();
            tiles[56].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png"));
            tiles[56].collision = true;
            tiles[56].stiff = true;
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // đọc map
    public void loadMap(String filePath) {
        try {
            // đọc file txt
            InputStream in = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int col = 0;
            int row = 0;

            while (col < GamePanel.maxCols && row < GamePanel.maxRows) {
                // đọc dòng đầu tiên của txt thành 1 xâu
                String line = br.readLine();
                // biến đổi xâu vừa đọc thành các phần tử string của mảng numbers
                String numbers[] = line.split(" ");
                while (col < GamePanel.maxCols) {
                    // ép kiểu các phần tử của numbers thành int
                    int num = Integer.parseInt(numbers[col]);
                    // đọc thông số vào mảng 2 chiều
                    mapTileNum[col][row] = num;
                    col++;
                }
                col = 0;
                row++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        // vẽ map bằng bảng txt\
        while (col < GamePanel.maxCols && row < GamePanel.maxRows) {
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tiles[tileNum].image, x, y, gp.TILESIZE, gp.TILESIZE, null);
            col++;
            x += gp.TILESIZE;
            if (col == GamePanel.maxCols) {
                col = 0;
                x = 0;
                row++;
                y += gp.TILESIZE;
            }
        }
    }

}
