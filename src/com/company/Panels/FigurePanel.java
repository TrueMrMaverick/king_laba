package com.company.Panels;


import com.company.Figures.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Nikita on 22.10.2017.
 */
public class FigurePanel extends JPanel {

    Square square = new Square(50,50,20,20);
     public FigurePanel(){
        setLayout(new BorderLayout());
        FigurePanel self = this;
        setBorder(BorderFactory.createLineBorder(Color.black));
         addMouseListener(new MouseAdapter(){
             @Override
             public void mousePressed(MouseEvent e){
                 if(e.getButton() == 1){
                     square.startDragging(e.getX(), e.getY());
                 } else {
                     square.move(e.getX(),e.getY());
                 }
                 repaint();
             }

             @Override
             public void mouseReleased(MouseEvent e) {
                 if(e.getButton() == 1){
                     square.stopDragging();
                 }
             }
         });

         addMouseMotionListener(new MouseAdapter(){
             @Override
             public void mouseDragged(MouseEvent e){
                 square.dragFigure(e.getX(),e.getY());
                 repaint();
             }

         });
         this.repaint();
    }


    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        square.paintFigure(g);
    }

}
