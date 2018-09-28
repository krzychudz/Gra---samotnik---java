package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static com.company.Game.*;
import static  com.company.DrawMapThread.tileSize;


public class Pawn extends JLabel implements MouseListener
{

    ImageIcon image = new ImageIcon(SettingsPanel.graphicsPath); // Obiekt przechowujący grafikę

    static int id = 1;                                           // ID piona

    private int Obj_ID;                                          // Zmienna rpzchowująca lokalne ID

    Pawn(int x, int y, JFrame frame)
    {
        setIcon(image);
        setBounds(x,y,tileSize,tileSize);        // Ustawiaenie wymiarów i połozenia grafiki
        setVisible(true);                        // Ustawienie grafiki widocznej
        addMouseListener(this);               // Dodanie Listener'a
        addMouseListener((MouseListener) frame); // Dodanie listenera
        frame.add(this);                         // Dodanie do okna

        Obj_ID = id;                            // Przypisanie globalnego id do lokalnego
        id++;                                   // Zwiększenie id dla następnego piona
    }

    public int getID()
    {
        return Obj_ID;   // Zwracanie ID
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        setBorder(null); // Usunięcie ramki gdy nacisniemy przycisk myszy
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(!mouseThread)
            setBorder(BorderFactory.createLineBorder(Color.black,2)); // Ustawiene ramki gdy najzedzamy, ale tylko gdy aktualnie nie przenosimy zadnego piona
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBorder(null);                                                        //Usunięcie ramki gdy opuszczamy obszar piona
    }


}
