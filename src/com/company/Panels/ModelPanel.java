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


    public ModelPanel(String model) {
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

        File windMillModelsStorage = new File(modelStoragePath + model);

        File[] listFiles = windMillModelsStorage.listFiles();

        ArrayList<Model2D> modelList = new ArrayList<>();

        for (int i = 0; i < listFiles.length; i++){
            String modelName = listFiles[i].getName();
            File[] modelProperties = new File(listFiles[i].getAbsolutePath()).listFiles();
            Model2D model2D = new Model2D(listFiles[i].getAbsolutePath() + "\\", modelName, modelProperties);
            modelList.add(model2D);
        }

        for (Model2D model2D:
             modelList) {
            scene2D.addModel(model2D);
        }
       


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
