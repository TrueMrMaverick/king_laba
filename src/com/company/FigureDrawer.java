package com.company;

import com.company.Menu.MainMenu;

import javax.swing.*;

/**
 * Created by Nikita on 22.10.2017.
 */
public class FigureDrawer extends JFrame {

    public FigureDrawer() {
        super("FigureDrawer");
        this.setBounds(200, 200, 800, 600);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainMenu mainMenu = new MainMenu(this);
        this.setJMenuBar(mainMenu);
        this.setVisible(true);
    }
}
