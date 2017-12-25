package com.company.Figures;

/**
 * Created by Nikita on 22.10.2017.
 */
public abstract class BaseFigure{
    int xPos;
    int yPos;
    int width;
    int height;
    private int prevX;
    private int prevY;
    private boolean isDragging;


    public BaseFigure() {}

    public BaseFigure(int xPos, int yPos, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    abstract boolean innerPoint(int x, int y);

    public void startDragging(int x, int y){
        if(innerPoint(x, y)){
            isDragging = true;
            prevX = x;
            prevY = y;
        }
    }

    public void dragFigure(int x, int y){
        if(isDragging){
            xPos += (x - prevX);
            yPos += (y - prevY);
            startDragging(x, y);
        }
    }

    public void stopDragging(){
        isDragging = false;
    }

    public void move(int x, int y){
        xPos = x;
        yPos = y;
    }

    public void setX(int xPos) {
        this.xPos = xPos;
    }

    public int getX() {
        return xPos;
    }

    public void setY(int yPos) {
        this.yPos = yPos;
    }

    public int getY() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPrevX() {
        return prevX;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }

}
