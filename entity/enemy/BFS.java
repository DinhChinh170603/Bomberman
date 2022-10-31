package entity.enemy;

import java.util.ArrayDeque;
import java.util.Deque;

import main.GamePanel;

class Cell {
    public int x, y;

    public Cell(int i, int j) {
        x = i;
        y = j;
    }
}

public class BFS {
    private static int[][] distance = new int[GamePanel.maxCols][GamePanel.maxRows];
    private static boolean[][] visited = new boolean[GamePanel.maxCols][GamePanel.maxRows];
    private static int[] dir = { 0, -1, 0, 1, 0 };

    public static int find(int col, int row, int playerCol, int playerRow) {
        if (GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[col][row]].collision == true) {
            if (GamePanel.tileManager.mapTileNum[col][row] == 55) {
                return (int) 1e9 - 1;
            }
            return (int) 1e8;
        }
        for (int i = 0; i < GamePanel.maxCols; i++) {
            for (int j = 0; j < GamePanel.maxRows; j++) {
                distance[i][j] = 0;
                visited[i][j] = false;
            }
        }
        Deque<Cell> q = new ArrayDeque<>();
        q.offer(new Cell(col, row));
        visited[col][row] = true;
        while (!q.isEmpty()) {
            int x = q.peek().x;
            int y = q.peek().y;
            q.poll();

            if (x == playerCol && y == playerRow)
                break;

            for (int i = 0; i < 4; i++) {
                int u = x + dir[i];
                int v = y + dir[i + 1];

                if (u >= GamePanel.maxCols || u < 0
                        || v >= GamePanel.maxRows || v < 0
                        || GamePanel.tileManager.tiles[GamePanel.tileManager.mapTileNum[u][v]].collision == true)
                    continue;

                if (visited[u][v] == false) {
                    distance[u][v] = distance[x][y] + 1;
                    visited[u][v] = true;
                    q.offer(new Cell(u, v));
                }
            }
        }
        return distance[playerCol][playerRow];
    }
}