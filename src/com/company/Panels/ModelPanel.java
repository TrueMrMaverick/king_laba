package com.company.Panels;

import com.company.Math.Matrix;
import com.company.Models.Model2D;
import com.company.Scenes.Scene2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Nikita on 24.12.2017.
 */
public class ModelPanel extends JPanel{
    private Scene2D scene2D = new Scene2D(this, -15, 15, -15, 15);

    private boolean mousePressed = false;


    public ModelPanel() {
        setLayout(new BorderLayout());
        ModelPanel self = this;
        setBorder(BorderFactory.createLineBorder(Color.black));
        self.setVisible(true);

        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                scene2D.startDragging(e.getX(), e.getY());
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                scene2D.stopDragging();
            }
        });

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                scene2D.onScroll(e.getX(), e.getY(), e.getWheelRotation());
            }
        });

        addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                scene2D.drag(e.getX(),e.getY());
            }

        });

        String modelStoragePath = modelPath();
        File modelStorage = new File(modelStoragePath);

        File windMillModelsStorage = new File(modelStoragePath + "WindMillModel");

        File[] modelsList = windMillModelsStorage.listFiles();

        for (int i = 0; i < modelsList.length; i++){
            String modelName = modelsList[i].getName();
            File[] modelProperties = new File(modelsList[i].getAbsolutePath()).listFiles();
            Model2D model2D = new Model2D(modelsList[i].getAbsolutePath(), modelName, modelProperties);
        }

//        Model2D windMill = new Model2D("WindMillVertices", "WindMillEdges");
//        scene2D.addModel(windMill);
//        Model2D blade1 = new Model2D("BladeVertices", "BladeEdges");
//        Model2D blade2 = new Model2D("BladeVertices", "BladeEdges");
//        blade2.vertices = blade2.turn(blade2.vertices, Math.PI / 2, 1);
//        Model2D blade3 = new Model2D("BladeVertices", "BladeEdges");
//        blade3.vertices = blade3.turn(blade3.vertices, Math.PI, 1);
//        Model2D blade4 = new Model2D("BladeVertices", "BladeEdges");
//        blade4.vertices = blade4.turn(blade4.vertices, - Math.PI / 2, 1);
//
//        scene2D.addModel(blade1);
//        scene2D.addModel(blade2);
//        scene2D.addModel(blade3);
//        scene2D.addModel(blade4);
//
//        Model2D earth = new Model2D("EarthVertices", "EarthEdges");
//        scene2D.addModel(earth);
//
//        double t = 0.001;
//
//        blade1.animation(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                blade1.vertices = blade1.turn(blade1.vertices, t, 1);
//            }
//        });
//        blade2.animation(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                blade2.vertices = blade2.turn(blade2.vertices, t, 1);
//            }
//        });
//        blade3.animation(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                blade3.vertices = blade3.turn(blade3.vertices, t, 1);
//            }
//        });
//        blade4.animation(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                blade4.vertices = blade4.turn(blade4.vertices, t, 1);
//            }
//        });
       


        repaint();
    }

    public static String modelPath(){
        String path = ModelPanel.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.split("out")[0];
        path += "\\src\\com\\company\\Resources\\Models\\";
        return path;
    }

    private void switchMousePressed(){
        if(mousePressed){
            mousePressed = false;
        } else {
            mousePressed = true;
        }
    }



    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        scene2D.setResolution(this);
        scene2D.render(g);
    }
}
