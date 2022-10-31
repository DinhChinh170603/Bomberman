/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXSwingMain.java to edit this template
 */
package main;

import javax.swing.JFrame;

/**
 *
 * @author Lenovo
 */
public class Game {
    public static void main(String[] args) {
        // basically swing seperates window frame and the content inside the frame
        // (which is panel)
        // khởi tạo màn hình
        JFrame window = new JFrame();
        // set exit
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // không cho thay đổi size game
        window.setResizable(false);
        // set title
        window.setTitle("Bomberman ver IDK what it is");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        // make the window's size to fit the size of the panel
        // làm size window vừa với size panel(khung)
        window.pack();

        // null -> make the window appear in the middle of the screen
        // cửa sổ vào giữa màn hình
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        //luồng bắt đầu game\
        gamePanel.startGameThread();
    }
}
