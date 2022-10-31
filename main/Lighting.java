package main;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class Lighting {

    GamePanel gp;
    int circleSize;
    BufferedImage darknessFilter;

    Area screenArea;
    Shape circleShape;
    Area lightArea;
    RadialGradientPaint gPaint;

    // create a gradation effect within the light circle
    Color color[] = new Color[12];
    float fraction[] = new float[12];

    public Lighting(GamePanel gp, int circleSize) {
        this.gp = gp;
        this.circleSize = circleSize;

        // create a buffered image
        darknessFilter = new BufferedImage(GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();



        // set color
        color[0] = new Color(0, 0, 0, 0.1f);
        color[1] = new Color(0, 0, 0, 0.42f);
        color[2] = new Color(0, 0, 0, 0.52f);
        color[3] = new Color(0, 0, 0, 0.61f);
        color[4] = new Color(0, 0, 0, 0.69f);
        color[5] = new Color(0, 0, 0, 0.76f);
        color[6] = new Color(0, 0, 0, 0.82f);
        color[7] = new Color(0, 0, 0, 0.87f);
        color[8] = new Color(0, 0, 0, 0.91f);
        color[9] = new Color(0, 0, 0, 0.94f);
        color[10] = new Color(0, 0, 0, 0.96f);
        color[11] = new Color(0, 0, 0, 0.98f);

        // set distance
        fraction[0] = 0f;
        fraction[1] = 0.4f;
        fraction[2] = 0.5f;
        fraction[3] = 0.6f;
        fraction[4] = 0.65f;
        fraction[5] = 0.7f;
        fraction[6] = 0.75f;
        fraction[7] = 0.8f;
        fraction[8] = 0.85f;
        fraction[9] = 0.9f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;

    }

    public void draw(Graphics2D g2) {

        // create a screen-sized rectangle area
        screenArea = new Area(new Rectangle2D.Double(0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT));
        // get center
        int centerX = gp.player.getX() + (gp.TILESIZE) / 2;
        int centerY = gp.player.getY() + (gp.TILESIZE) / 2;

        // topLeft x;y of the light
        double x = centerX - (circleSize / 2);
        double y = centerY - (circleSize / 2);

        // create a circle light
        circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);

        // create a light circle area
        lightArea = new Area(circleShape);

        // substract to get a center hold of the rectangle
        screenArea.subtract(lightArea);

        // create a gradation paint settings for the light circle
        gPaint = new RadialGradientPaint(centerX, centerY, (circleSize / 2), fraction, color);

        // set the gradient data on g2
        g2.setPaint(gPaint);

        // draw the light circle
        g2.fill(lightArea);


        // set a black color to draw rec
        g2.setColor(new Color(0, 0, 0, 0.98f));

        // draw the screen without the light circle area
        g2.fill(screenArea);


        g2.drawImage(darknessFilter, 0, 0, null);
        g2.dispose();

    }
}
