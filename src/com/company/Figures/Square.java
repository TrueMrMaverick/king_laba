package com.company.Figures;

import java.awt.*;

/**
 * Created by Nikita on 05.11.2017.
 */
public class Square extends BaseFigure {

    public Square() {}

    public Square(int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height);
    }

    @Override
    boolean innerPoint(int x, int y) {
        if(x - xPos <= width && x - xPos >= 0 && y - yPos <= height && y - yPos >= 0) {
            return true;
        } else return false;
    }

    public void paintFigure(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(xPos, yPos, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(xPos, yPos, width, height);
    }

}
