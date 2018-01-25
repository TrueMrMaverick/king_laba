package com.company.Panels;

import com.company.Scenes.Scene2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Created by Nikita on 06.11.2017.
 */
public class ScenePanel extends JPanel {

    private Scene2D scene2D = new Scene2D(this, -2*Math.PI, 2*Math.PI, -1.5, 1.5);
    private boolean mousePressed = false;
    private double bicentricA = 0.7;
    private double bicentricC = 0.4;

    public ScenePanel(String functionType) {
        setLayout(new BorderLayout());
        ScenePanel self = this;
        setBorder(BorderFactory.createLineBorder(Color.black));
        self.setVisible(true);
        scene2D.setFunctionType(functionType);


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

        repaint();
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
        if (scene2D.getFunctionType().equals("bicentricEllipse")){
            scene2D.twoCenterBipolarPlot(g, bicentricA - bicentricC, bicentricA + bicentricC, bicentricC, bicentricA, true);
        } else {
            scene2D.plot(g, true);
        }
    }
}
