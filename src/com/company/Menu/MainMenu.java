package com.company.Menu;

import com.company.Panels.FigurePanel;
import com.company.Panels.ModelPanel;
import com.company.Panels.ScenePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Nikita on 06.11.2017.
 */
public class MainMenu extends JMenuBar {

    public MainMenu(JFrame jFrame) {
        createMainMenu(jFrame);
    }
    private ArrayList<ScenePanel> scenePanelsStack = new ArrayList<>();
    private ArrayList<FigurePanel> figurePanelsStack = new ArrayList<>();
    private ArrayList<ModelPanel> modelPanelsStack = new ArrayList<>();



    public void createMainMenu(JFrame jFrame){
        MainMenu self = this;
        JMenu mainMenu = new JMenu("Меню");
        JMenu labas = new JMenu("Лабараторные");
        JMenuItem laba0 = new JMenuItem("Лабараторная №0");
        labas.add(laba0);

        JMenu laba1 = createLaba1Menu(jFrame);
        labas.add(laba1);

        JMenuItem laba2 = createLaba2Menu(jFrame);
        labas.add(laba2);

        mainMenu.add(labas);

        laba0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.clearPanels();
                FigurePanel figurePanel = new FigurePanel();
                figurePanelsStack.add(figurePanel);
                jFrame.add(figurePanel);
                figurePanel.setVisible(true);
                jFrame.setTitle("Лабараторная №0");
                jFrame.revalidate();
            }
        });


        this.add(mainMenu);
        this.setVisible(true);
    }

    private JMenuItem createLaba2Menu(JFrame jFrame) {
        MainMenu self = this;
        JMenuItem jMenu = new JMenuItem("Лабараторная №2");
        JMenu options = new JMenu("Опции");

        jMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.clearPanels();
                ModelPanel modelPanel = new ModelPanel();
                modelPanelsStack.add(modelPanel);
                self.add(options);
                jFrame.add(modelPanel);
                modelPanel.setVisible(true);
                jFrame.setTitle("Лабараторная №2");
                jFrame.revalidate();
            }
        });


        return jMenu;
    }

    private JMenu createLaba1Menu(JFrame jFrame) {
        MainMenu self = this;
        JMenu jMenu = new JMenu("Лабараторная №1");
        JMenuItem label = new JMenuItem("Функции");
        jMenu.add(label);
        jMenu.addSeparator();

        JMenuItem sin = new JMenuItem("sin(x)");
        JMenuItem parabola = new JMenuItem("parabola");
        JMenuItem bicentricEllipse = new JMenuItem("bicentricEllipse");


        sin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.clearPanels();
                ScenePanel scenePanel = new ScenePanel("sin");
                scenePanelsStack.add(scenePanel);
                jFrame.add(scenePanel);
                scenePanel.setVisible(true);
                jFrame.setTitle("Лабараторная №1");
                jFrame.revalidate();
            }
        });
        parabola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.clearPanels();
                ScenePanel scenePanel = new ScenePanel("parabola");
                scenePanelsStack.add(scenePanel);
                jFrame.add(scenePanel);
                scenePanel.setVisible(true);
                jFrame.setTitle("Лабараторная №1");
                jFrame.revalidate();
            }
        });

        jMenu.add(sin);
        jMenu.add(parabola);
        jMenu.add(bicentricEllipse);


        return jMenu;
    }

    private void clearPanels(){
        for (ScenePanel scenePanel:
                scenePanelsStack) {
            scenePanel.setVisible(false);
        }
        scenePanelsStack.clear();

        for (FigurePanel figurePanel:
                figurePanelsStack) {
            figurePanel.setVisible(false);
        }
        figurePanelsStack.clear();

        for (ModelPanel modelPanel:
                modelPanelsStack) {
            modelPanel.setVisible(false);
        }
        modelPanelsStack.clear();
    }

}
