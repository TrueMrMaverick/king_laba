package com.company.Models;

import com.company.Math.AffineTransform;
import com.company.Math.Matrix;
import com.company.Math.Vector;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.PI;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

/**
 * Created by Nikita on 24.12.2017.
 */
public class Model2D {
    public Matrix vertices;
    public Matrix edges;
    private AffineTransform affineTransform = new AffineTransform();
    private String path;


    public Model2D(){
        vertices = new Matrix();
        edges = new Matrix();
    }

    public Model2D(Matrix vertices, Matrix edges) {
        this.vertices = vertices.clone();
        this.edges = edges.clone();
    }

    public Model2D(String path, String fNameVertices, String fNameEdges){
        this.path = path;
        vertices = new Matrix(fileMatrixInit(fNameVertices));
        edges = new Matrix(fileMatrixInit(fNameEdges));
    }

    private Matrix fileMatrixInit(String fileName){
        File file = new File(path + fileName + ".txt");
        ArrayList<String> stringList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader out = new BufferedReader(fileReader);

            try {
                String stringBuffer;
                while ((stringBuffer = out.readLine()) != null){
                    stringList.add(stringBuffer);
                }
            } finally {
                out.close();
            }

        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Integer> intBuffer = new ArrayList<>();
        Matrix matrix = new Matrix();
        for (int i = 0; i < stringList.size(); i++){
            String subString = stringList.get(i);
            String temp = new String();
            for(int j = 0; j < subString.length(); j++){
                if(subString.charAt(j) != ' '){
                    temp += subString.charAt(j);
                } else {
                    intBuffer.add(Integer.parseInt(temp));
                    temp = "";
                }
            }
            if(temp != ""){
                intBuffer.add(Integer.parseInt(temp));
            }

            if (i == 0){
                int row = intBuffer.get(0);
                int col = intBuffer.get(1);

                matrix = new Matrix(row, col);
            } else {
                List<Double> doubleBuffer = new ArrayList<>();
                for (int idx = 0; idx < intBuffer.size(); idx++){
                    doubleBuffer.add((double) intBuffer.get(idx));
                }
                Vector line = new Vector(doubleBuffer);
                matrix.setRow(i - 1, line);
            }
            intBuffer.clear();
        }

        return matrix;
    }

    public void animation(ActionListener actionListener){
        Timer timer = new Timer(0, actionListener);
        timer.start();
    }

    public void animation(int delay, ActionListener actionListener){
        Timer timer = new Timer(delay, actionListener);
        timer.start();
    }

    public Matrix move(Matrix matrix, double x, double y){
        return affineTransform.translation(x, y).mult(matrix);
    }

    public Matrix turn(Matrix matrix, double t, int point){
        double pointX = matrix.getElement(0, point - 1);
        double pointY = matrix.getElement(1, point - 1);
        Matrix result = matrix.clone();
        result = move(result, -pointX, -pointY);
        //result.print("До поворота: ");
        result = affineTransform.rotation(t).mult(result);
        //result.print("После поворота: ");
        result = move(result, pointX, pointY);
        return result;
    }
    public Matrix turn(Matrix matrix, double cos, double sin, int point){
        double pointX = matrix.getElement(0, point - 1);
        double pointY = matrix.getElement(1, point - 1);
        Matrix result = matrix.clone();
        result = move(result, -pointX, -pointY);
        //result.print("До поворота: ");
        result = affineTransform.rotation(cos, sin).mult(result);
        //result.print("После поворота: ");
        result = move(result, pointX, pointY);
        return result;
    }

    public Matrix scale(Matrix matrix, double kx, double ky){
        return affineTransform.scaling(kx,ky).mult(matrix);
    }

    public Matrix reflect(Matrix matrix){
        double pointX = matrix.getElement(0, 0);
        double pointY = matrix.getElement(1, 0);
        Matrix result = matrix.clone();
        result = move(result, -pointX, -pointY);
        result = affineTransform.rotation(cos(PI), sin(PI)).mult(result);
        result = move(result, -pointX, -pointY);
        return result;
    }
}
