package com.company.Scenes;

import com.company.Cameras.Camera2D;
import com.company.Math.Matrix;
import com.company.Models.Model2D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Nikita on 06.11.2017.
 */
public class Scene2D extends Camera2D {

    public String functionType;

    private ArrayList<Model2D> model2DList = new ArrayList<>();




    public Scene2D(JPanel jPanel) {
        super(jPanel);
    }

    public Scene2D(JPanel jPanel, double l, double r, double b, double t) {
        super(jPanel, l, r, b, t);
    }


    public void plot(Graphics g, boolean isAxes){
        this.g = g;

        if(isAxes) {
            axes();
        }

        double step = (R - L) / W;
        double currLocationX = L;
        moveTo(currLocationX, function(functionType, currLocationX));
        while (currLocationX < R){
            lineTo(currLocationX, function(functionType, currLocationX));
            currLocationX += step;
        }
    }

    public void twoCenterBipolarPlot(Graphics g, double tMin, double tMax, double c, double a, boolean isAxes){

        this.g = g;

        if(isAxes) {
            axes();
        }

        double step = (R - L) / W;
        double t = tMin + Math.pow(10, -8);

        moveTo(rToX(r1(t), r2(t, a), c), rToY(r1(t), r2(t, a), c));

        for (; t < tMax; t += step)
        {
            lineTo(rToX(r1(t), r2(t, a), c), rToY(r1(t), r2(t, a), c));
        }

        t = tMax - 0.0001;
        lineTo(rToX(r1(t), r2(t, a), c), rToY(r1(t), r2(t, a), c));

        for (; t > tMin; t -= step)
        {
            lineTo(rToX(r1(t), r2(t, a), c), -rToY(r1(t), r2(t, a), c));
        }

        t = tMin + Math.pow(10, -8);
        lineTo(rToX(r1(t), r2(t, a), c), -rToY(r1(t), r2(t, a), c));

    }

    private double r1(double t) {
        return t;
    }

    private double r2(double t, double a){
        return 2 * a - t;
    }

    private double rToX(double r1, double r2, double c){
        return (r1 * r1 - r2 *r2) / (4 * c);
    }

    private double rToY(double r1, double r2, double c)
    {
        return (Math.sqrt(16 * c*c*r1*r1 - (r1*r1 - r2*r2 + 4 * c*c) * (r1*r1 - r2*r2 + 4 * c*c))) / (4 * c);
    }

    public void render(Graphics g){
        this.g = g;
        //axes();
        for (int index = 0; index < model2DList.size(); index++) {

            Matrix vertices = new Matrix(model2DList.get(index).vertices);
            Matrix edges = new Matrix(model2DList.get(index).edges);


            // Проверяем, чтобы в низу каждого столбца была 1
            for(int i = 0; i < vertices.getRowSize(); i++){
                if(vertices.getElement(2, i) != 1){
                    for (int j = 0; j < vertices.getColSize(); j++){
                        vertices.setElement(j, i, vertices.getElement(j, i) / vertices.getElement(2, i));
                    }
                }
            }



            for (int i = 0; i < edges.getColSize(); i++){
                double startX, startY;
                double endX, endY;

                startX = getX(i, 0, vertices, edges);
                startY = getY(i, 0, vertices, edges);
                endX = getX(i, 1, vertices, edges);
                endY = getY(i, 1, vertices, edges);

                moveTo(startX, startY);
                lineTo(endX, endY);
            }
        }
    }



    private double function(String type, double x){
        if(type.equals("sin")){
            return Math.sin(x);
        } else if(type.equals("parabola")){
            return x*x - 2;
        }
        return 0;
    }

    private double getX(int index, int type,  Matrix vertices, Matrix edges){
        double pointNumber = edges.getElement(index, type);
        return vertices.getElement(0, (int) pointNumber - 1);
    }

    private double getY(int index, int type, Matrix vertices, Matrix edges){
        double pointNumber = edges.getElement(index, type);
        return vertices.getElement(1, (int) pointNumber - 1);
    }

    public void addModel(Model2D model2D){
        model2DList.add(model2D);
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }
}
