package com.company.Panels;

import com.company.Math.AffineTransform3D;
import com.company.Math.Matrix;
import com.company.Models.Model2D;
import com.company.Models.Model3D;
import com.company.Scenes.Scene3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Nikita on 27.12.2017.
 */
public class ModelPanel3D extends ModelPanel2D {

    private Scene3D scene3D = new Scene3D(this, -15, 15, -15, 15);
    private AffineTransform3D affineTransform3D = new AffineTransform3D();

    private boolean mousePressed = false;

    public ModelPanel3D(String model) {
        setLayout(new BorderLayout());
        ModelPanel3D self = this;
        setBorder(BorderFactory.createLineBorder(Color.black));
        self.setVisible(true);
        initMouseListners();
        initKeyListners();

        File modelStorage = new File(modelPath() + model);
        File vertices = modelStorage, verges = modelStorage;

        for (File file:
             modelStorage.listFiles()) {
            if(file.getName().equals("Vertices.txt")){
                vertices = file;
            } else if(file.getName().equals("Verges.txt")){
                verges = file;
            }
        }

        scene3D.addModel(new Model3D(modelStorage.getAbsolutePath() + "\\", vertices.getName(), verges.getName()));


        repaint();
    }

    private void initMouseListners(){
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                requestFocusInWindow();
                scene3D.startDragging(e.getX(), e.getY());
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                scene3D.stopDragging();
            }
        });

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                scene3D.onScroll(e.getX(), e.getY(), e.getWheelRotation());
            }
        });

        addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                scene3D.drag(e.getX(),e.getY());
            }

        });


    }

    private void initKeyListners(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                Model3D model3D = scene3D.getModel();
                Matrix vertices = model3D.getVertices();
                Double pointX = vertices.getElement(0, 4);
                Double pointY = vertices.getElement(1, 4);
                Double pointZ = vertices.getElement(2, 4);
                switch (e.getKeyCode()){
                    case KeyEvent.VK_D:
                        model3D.applyAffineTransform(affineTransform3D.translation(1, 0, 0), scene3D.getWorldToProject());
                        break;
                    case KeyEvent.VK_A:
                        model3D.applyAffineTransform(affineTransform3D.translation(-1, 0, 0), scene3D.getWorldToProject());
                        break;
                    case KeyEvent.VK_W:
                        model3D.applyAffineTransform(affineTransform3D.translation(0, 1, 0), scene3D.getWorldToProject());
                        break;
                    case KeyEvent.VK_S:
                        model3D.applyAffineTransform(affineTransform3D.translation(0, -1, 0), scene3D.getWorldToProject());
                        break;
                    case KeyEvent.VK_Q:
                        model3D.applyAffineTransform(affineTransform3D.translation(-pointX, 0, 0), scene3D.getWorldToProject());
                        //model3D.applyAffineTransform(affineTransform3D.rotationY(0.05), scene3D.getWorldToProject());
                        break;
                    case KeyEvent.VK_E:
                        model3D.applyAffineTransform(affineTransform3D.rotationY(-0.05), scene3D.getWorldToProject());
                        break;



                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void switchMousePressed() {
        if (mousePressed) {
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
        scene3D.setResolution(this);
        scene3D.render(g);
    }
}
