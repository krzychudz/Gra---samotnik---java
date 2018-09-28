package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static com.company.DrawMapThread.tileSize;

public class MapField extends JLabel implements MouseListener
{

    ImageIcon image = new ImageIcon("field.png");       // Grafika pola mapy

    MapField(int x, int y, JFrame frame)
    {
        setIcon(image);                                         // Ustawienie grafiki
        setBounds(x,y,tileSize,tileSize);                       // Ustawienie połozenia
        setVisible(true);                                       // Ustawienie widoczności

        addMouseListener(this);                             // Dodanie listenera

        frame.add(this);                                       // Dodanie do okna gry
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBorder(BorderFactory.createLineBorder(Color.black,2)); // Jeżeli najedziemy to ustawiamy ramkę
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBorder(null);                                                    // Jeżęli opuścimy to kasujemy ramkę

    }
}
