package com.company;

import javax.swing.*;
import java.awt.*;

import static com.company.Game.mouseThread;
import static com.company.DrawMapThread.tileSize;

public class MouseThread implements Runnable {

    JLabel object;                                    // Obiekt od przechowywania piona którego mamy przykleić do myszy

    MouseThread(JLabel ob)
    {
        object = ob;            // Przypisanie
    }

    @Override
    public void run() {

        while(mouseThread)                                  // Jeżeli zmienna mouseThread == true to znaczy ze należy przykelić obiekt do myszy
        {
            PointerInfo a = MouseInfo.getPointerInfo();     // Pobieramy koordynaty kursora
            Point b = a.getLocation();                      //
            int x = (int) b.getX();                         //
            int y = (int) b.getY();                         //

            object.setBounds(x - (tileSize/2), y - (tileSize/2), tileSize, tileSize);   // Ustawiamy środek grafiki piona na pozycji kursora
        }


    }
}
