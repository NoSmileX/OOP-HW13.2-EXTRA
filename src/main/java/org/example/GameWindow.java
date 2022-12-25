package org.example;

import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow(){
        add(new GameField());
        setTitle("NYAN CAT (Snake) Game");
        setVisible(true);
        setSize(960, 480);
        setLocation(175, 150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
