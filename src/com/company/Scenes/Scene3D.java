package com.company.Scenes;

import com.company.Cameras.Camera3D;
import com.company.Math.Matrix;
import com.company.Models.Model3D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Nikita on 27.12.2017.
 */
public class Scene3D extends Camera3D{

    private ArrayList<Model3D> model3DList = new ArrayList<>();

    public  Scene3D(JPanel jPanel) {
        super(jPanel);
    }

    public Scene3D(JPanel jPanel, double l, double r, double b, double t) {
        super(jPanel, l, r, b, t);
    }

    public void render(Graphics g){
        this.g = g;
        axes();
        for (int index = 0; index < model3DList.size(); index++) {

            if (model3DList.get(index).projectedVerticesIsEmpty()){
                model3DList.get(index).project(this.getWorldToProject());
            }
            Matrix vertices = new Matrix(model3DList.get(index).getProjectedVertices());
            Matrix edges = new Matrix(model3DList.get(index).getEdges());



            // Проверяем, чтобы в низу каждого столбца была 1
            for(int i = 0; i < vertices.getRowSize(); i++){
                if(vertices.getElement(2, i) != 1){
                    for (int j = 0; j < vertices.getColSize(); j++){
                        vertices.setElement(j, i, vertices.getElement(j, i) / vertices.getElement(2, i));
                    }
                }
            }

            vertices.print("Projected vertices: ");
            edges.print("Edges: ");

            for (int i = 0; i < edges.getColSize(); i++){
                for (int j = 0; j < edges.getRowSize(); j++) {
                    moveTo(vertices.getElement(0, i), vertices.getElement(1, i));
                    if (edges.getElement(i, j) != 0){
                        lineTo(vertices.getElement(0, j), vertices.getElement(1, j) );
                    }
                }
            }
        }
    }

    public Model3D getModel(){
       return model3DList.get(0);
    }
    public void addModel(Model3D model3D){
        model3DList.add(model3D);
    }

}
